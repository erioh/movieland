package com.luxoft.sdemenkov.movieland.web.interceptor;

import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.security.SecurityHttpRequestWrapper;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

public class RequestInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuidStr = Optional.ofNullable(request.getHeader("x-auth-token")).orElse("NAN");
        logger.debug("String {} is received as uuid from header x-auth-token", uuidStr);
        Token token;
        if ("NAN".equals(uuidStr)) {
            token = authenticationService.getTokenForGuest();
        } else {
            token = authenticationService.getTokenByUuid(UUID.fromString(uuidStr));
        }
        TokenPrincipal principal = new TokenPrincipal(token);
        logger.debug("Principal {} is received", principal);
        ((SecurityHttpRequestWrapper) request).setPrincipal(principal);
        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("email", token.getUser().getEmail());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
