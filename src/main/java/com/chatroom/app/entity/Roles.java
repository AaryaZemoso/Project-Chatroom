package com.chatroom.app.entity;

public enum Roles {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String role;

    private Roles(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }

}
