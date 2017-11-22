package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.business.User;
import com.luxoft.sdemenkov.movieland.model.technical.Token;
import com.luxoft.sdemenkov.movieland.security.client.Client;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import com.luxoft.sdemenkov.movieland.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Map<UUID, Token> tokenMap = new ConcurrentHashMap<>();

    @Value("${cron.service.users.autologout.time}")
    private Long milliSecondsToLogout;

    @Value("${cron.service.users.session.before.logout.hours}")
    private Integer hoursBeforeLogout;

    @Autowired
    private UserService userService;

    public Token login(String email, String password) {
        User user = userService.getUser(email, password);
        if (user == null) {
            logger.error("Login or password are invalid. Used login = {}, used password = {}", email, password);
            throw new RuntimeException("Login or password are invalid");
        }
        UUID uuid = UUID.randomUUID();
        Token token = new Token(user, uuid, LocalDateTime.now().plusHours(hoursBeforeLogout));
        logger.debug("User {} with password {} is logged in. UUID = {}", email, password, uuid);
        tokenMap.put(uuid, token);
        return token;
    }

    @Override
    public void logout(UUID uuid) {
        tokenMap.remove(uuid);
    }

    @Override
    public boolean isAlive(UUID uuid) {
        if (tokenMap.containsKey(uuid)) {
            boolean isAlive = tokenMap.get(uuid).getDieTime().isAfter(LocalDateTime.now());
            if (!isAlive) {
                tokenMap.remove(uuid);
            }
            return isAlive;
        }
        return false;
    }

    @Override
    @Scheduled(fixedRateString = "${cron.service.users.autologout.time}")
    public void removeInactiveUsers() {
        for (Map.Entry<UUID, Token> entryMap : tokenMap.entrySet()) {
            isAlive(entryMap.getKey());
        }
        logger.debug("Dead sessions are removed");
    }

    @Override
    public Client getClientByUuid(UUID uuid) {
        if (isAlive(uuid)) {
            Client client = new Client(tokenMap.get(uuid));
            logger.debug("getTokenByUuid is executed. client {} is used for uuid {}", client, uuid);
            return client;
        } else {
            logger.debug("getTokenByUuid is executed. token  for uuid {} is not found", uuid);
            return getGuest();
        }
    }

    @Override
    public Client getGuest() {
        Client client = new Client();
        logger.debug("getTokenForGuest is executed. Guest client {} is created", client, true);
        return client;
    }

}