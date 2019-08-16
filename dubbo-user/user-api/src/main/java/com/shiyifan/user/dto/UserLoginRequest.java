package com.shiyifan.user.dto;


import com.shiyifan.user.abs.AbstractRequest;

import java.io.Serializable;


public class UserLoginRequest extends AbstractRequest implements Serializable{
    private static final long serialVersionUID = -5836710058540708731L;

    private String username;

    private String password;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                "} " + super.toString();
    }
}
