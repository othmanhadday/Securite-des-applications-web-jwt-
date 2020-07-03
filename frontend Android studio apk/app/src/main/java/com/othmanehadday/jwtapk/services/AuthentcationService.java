package com.othmanehadday.jwtapk.services;

import com.othmanehadday.jwtapk.model.User;

public interface AuthentcationService {

    public final static String host="http://192.168.1.103:8082";

    public void login(User user);
    String getRes();
}
