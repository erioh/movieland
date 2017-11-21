package com.luxoft.sdemenkov.movieland.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.sdemenkov.movieland.model.business.User;
import com.luxoft.sdemenkov.movieland.model.technical.Token;
import com.luxoft.sdemenkov.movieland.security.Protected;
import com.luxoft.sdemenkov.movieland.security.SecurityHttpRequestWrapper;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import com.luxoft.sdemenkov.movieland.web.exception.NeededRolesAneAbsentException;
import com.luxoft.sdemenkov.movieland.web.exception.RestException;
import com.luxoft.sdemenkov.movieland.web.exception.UserNotLoggedInException;
import com.luxoft.sdemenkov.movieland.web.response.ExceptionMessageDto;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationService authenticationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuidStr = Optional.ofNullable(request.getHeader("x-auth-token")).orElse("NAN");
        logger.debug("String {} is received as uuid from header x-auth-token", uuidStr);
        Token token;
        if ("NAN".equals(uuidStr)) {
            System.out.println("TAR");
            token = authenticationService.getTokenForGuest();
        } else {
            token = authenticationService.getTokenByUuid(UUID.fromString(uuidStr));
        }
        try {
            validateToken(token, handler);
            TokenPrincipal principal = new TokenPrincipal(token);
            logger.debug("Principal {} is received", principal);
            ((SecurityHttpRequestWrapper) request).setPrincipal(principal);
        } catch (RestException e) {
            response.setStatus(e.getHttpStatus().value());
            response.setContentType("application/json");
            response.setContentType("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ExceptionMessageDto(e.getMessage())));
            return false;
        }

        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("email", token.getUser().getEmail());

        return true;
    }

    public void validateToken(Token token, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.hasMethodAnnotation(Protected.class)) {
            System.out.println(token);
            if("guest".equals(token.getUser().getEmail())) {
                throw new UserNotLoggedInException();
            }
            Protected annotation = handlerMethod.getMethodAnnotation(Protected.class);
            if (!token.getUser().getRoleList().contains(annotation.protectedBy())){
                throw new NeededRolesAneAbsentException();
            }
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
