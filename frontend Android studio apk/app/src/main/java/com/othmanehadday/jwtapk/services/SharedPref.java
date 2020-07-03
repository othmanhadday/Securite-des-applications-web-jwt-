package com.othmanehadday.jwtapk.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.othmanehadday.jwtapk.model.Role;

import java.util.List;


public class SharedPref {
    private SharedPreferences sharedpreferences;
    private JWT jwt;

    public SharedPref(Context context) {
        if (sharedpreferences == null) {
            sharedpreferences = context.getSharedPreferences("loginPref", 0);
        }
        if (getToken() != "") {
            jwt = new JWT(getToken());
        }
    }


    public SharedPreferences getSharedpreferences() {
        return sharedpreferences;
    }

    public void storeToken(String token) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("jwtToken", token);
        editor.apply();
    }

    public String getToken() {
        String token = sharedpreferences.getString("jwtToken", null);
        if (token == null) {
            token = "";
        }
        if (token.contains("Bearer ")) {
            token = token.replace("Bearer ", "");
        }
        return token;
    }

    public String getSub() {
        String sub = "";
        if (jwt != null) {
            sub = jwt.getSubject();
        }
        return sub;
    }

    public List<Role> getRoles() {
        List<Role> roles = null;
        if (jwt != null) {
            Claim claim = jwt.getClaim("roles");
            roles = claim.asList(Role.class);
        }
        return roles;
    }

    public boolean isAdmin() {
        boolean admin = false;
        List<Role> roles = getRoles();
        if (roles != null) {
            for (Role r : roles) {
                if (r.getAuthority().equals("ADMIN")) {
                    admin = true;
                }
            }
        }
        return admin;
    }

    public boolean isAuthenticated() {
        boolean auth = false;
        if (getToken() == "") {
            auth = false;
        } else {
            if (jwt != null) {
                if (sharedpreferences.contains("jwtToken") && !jwt.isExpired(0)) {
                    auth = true;
                }
            }
        }
        return auth;
    }


}
