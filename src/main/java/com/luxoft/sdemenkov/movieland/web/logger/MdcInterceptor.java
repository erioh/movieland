package com.luxoft.sdemenkov.movieland.web.logger;

import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.model.User;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class MdcInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuidStr = request.getHeader("x-auth-token");
        Token token = null;
        if (uuidStr != null) {
            token = authenticationService.getTokenByUuid(UUID.fromString(uuidStr));
        }
        if (token == null) {
            User guest = new User();
            guest.setEmail("guest");
            token = new Token(guest, null, null);
        }
        MDC.put("requestId", UUID.randomUUID().toString());
        MDC.put("email", token.getEmail());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
