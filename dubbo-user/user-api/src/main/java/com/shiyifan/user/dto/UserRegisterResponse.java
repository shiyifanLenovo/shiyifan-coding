package com.pinyg.user.dto;

import com.pinyg.user.abs.AbstractResponse;

import java.io.Serializable;


public class UserRegisterResponse extends AbstractResponse implements Serializable{

    private static final long serialVersionUID = -7690077437344492561L;

    private Integer uid;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
