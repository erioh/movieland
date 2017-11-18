package com.luxoft.sdemenkov.movieland.security.role;


public enum Role {
    USER("USER");

    final String roleDescr;

    Role(String role) {
        this.roleDescr = role;
    }

    public static Role getRolebyText(String text) {
        for (Role role : values()) {
            if(role.roleDescr.equals(text)) {
                return role;
            }
        }
        throw new RuntimeException("Role "+ text +" does not exist");
    }
}
