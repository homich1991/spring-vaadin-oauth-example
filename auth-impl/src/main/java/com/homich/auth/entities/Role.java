package com.homich.auth.entities;


import java.util.ArrayList;
import java.util.List;

public enum Role {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_GUEST;

    public static List<Role> convertToRoleList(List<String> roles) {
        List<Role> roleList = new ArrayList<>();
        for (String role : roles) {
            roleList.add(Role.valueOf(role));
        }
        return roleList;
    }

    public static List<String> convertToStringList(List<Role> roles) {
        List<String> roleList = new ArrayList<>();
        for (Role role : roles) {
            roleList.add(role.toString());
        }
        return roleList;
    }
}
