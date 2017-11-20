package com.luxoft.sdemenkov.movieland.security.role;


public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    final String roleDescription;

    Role(String role) {
        this.roleDescription = role;
    }

    public static Role getRoleByText(String text) {
        for (Role role : values()) {
            if (role.roleDescription.equals(text)) {
                return role;
            }
        }
        throw new RuntimeException("Role " + text + " does not exist");
    }
}
