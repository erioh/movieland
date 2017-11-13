package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.UserDao;
import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.model.User;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Map<UUID, Long> birthTimeOfUuid = new ConcurrentHashMap<>();
    private Map<UUID, User> userToUuidMap = new ConcurrentHashMap<>();

    @Value("${cron.service.users.autologout.time}")
    private Long milliSecondsToLogout;

    @Autowired
    private UserDao userDao;

    public Token login(String email, String password) {
        User user = userDao.getUser(email, password);
        if (user == null) {
            logger.error("Login or password are invalid. Used login = {}, used password = {}", email, password);
            throw new RuntimeException("Login or password are invalid");
        }
        UUID uuid = UUID.randomUUID();
        Token token = new Token(uuid, user.getNickname());
        logger.debug("User {} with password {} is logged in. UUID = {}", email, password, uuid);
        birthTimeOfUuid.put(uuid, System.currentTimeMillis());
        userToUuidMap.put(uuid, user);
        return token;
    }

    @Override
    public void logout(UUID uuid) {
        birthTimeOfUuid.remove(uuid);
        userToUuidMap.remove(uuid);
    }

    @Override
    public boolean isAlive(UUID uuid) {
        if (birthTimeOfUuid.containsKey(uuid)) {
            boolean isAlive = System.currentTimeMillis() - birthTimeOfUuid.get(uuid) < milliSecondsToLogout;
            if (!isAlive) {
                birthTimeOfUuid.remove(uuid);
                userToUuidMap.remove(uuid);
            }
            return isAlive;
        }
        return false;
    }

    @Override
    @Scheduled(fixedRateString = "${cron.service.users.autologout.time}")
    public void removeInactiveUsers() {
        for(Map.Entry<UUID, Long> entryMap: birthTimeOfUuid.entrySet()){
            isAlive(entryMap.getKey());
        };
    }

}