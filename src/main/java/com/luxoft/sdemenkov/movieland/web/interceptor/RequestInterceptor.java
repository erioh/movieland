package com.luxoft.sdemenkov.movieland.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.sdemenkov.movieland.security.Protected;
import com.luxoft.sdemenkov.movieland.security.SecurityHttpRequestWrapper;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.security.client.Client;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import com.luxoft.sdemenkov.movieland.web.exception.NeededRolesAneAbsentException;
import com.luxoft.sdemenkov.movieland.web.exception.RestException;
import com.luxoft.sdemenkov.movieland.web.exception.UserNotLoggedInException;
import com.luxoft.sdemenkov.movieland.web.dto.response.ExceptionMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

public class RequestInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationService authenticationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<String> requestHeader = Optional.ofNullable(request.getHeader("x-auth-token"));
        logger.debug("String {} is received as uuid from header x-auth-token", requestHeader);
        Client client;
        if (!requestHeader.isPresent()) {
            client = authenticationService.getGuest();
        } else {
            client = authenticationService.getClientByUuid(UUID.fromString(requestHeader.get()));
        }
        try {
            validateToken(client, handler);
            TokenPrincipal principal = new TokenPrincipal(client.getToken());
            logger.debug("Principal {} is received", principal);
            ((SecurityHttpRequestWrapper) request).setPrincipal(principal);
        } catch (RestException e) {
            response.setStatus(e.getHttpStatus().value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ExceptionMessageDto(e.getMessage())));
            return false;
        }

        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("email", client.getClientIdentificator());

        return true;
    }

    public void validateToken(Client client, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.hasMethodAnnotation(Protected.class)) {
            if(client.isGuest()) {
                throw new UserNotLoggedInException();
            }
            Protected annotation = handlerMethod.getMethodAnnotation(Protected.class);
            if (!client.getRoleList().contains(annotation.protectedBy())){
                throw new NeededRolesAneAbsentException();
            }
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
