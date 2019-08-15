package com.shiyifan.user.dto;


import com.shiyifan.user.abs.AbstractRequest;

import java.io.Serializable;


public class UserQueryRequest extends AbstractRequest implements Serializable{
    private static final long serialVersionUID = 7306023298178530119L;

    private Integer uid;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "UserQueryRequest{" +
                "uid=" + uid +
                "} " + super.toString();
    }
}
