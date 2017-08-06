package com.utils;

import com.dao.entities.Role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 21.05.2017.
 */
public class FormatUtils {

    private FormatUtils() {
    }

    public static List<String> formatRolesFromDBtoView(Collection<Role> rolesDB) {
        return rolesDB.stream().map(role -> formatRoleFromDBtoView(role.getName())).collect(Collectors.toList());
    }

    public static List<String> formatRolesFromViewToDB(Collection<String> roles) {
        return roles.stream().map(FormatUtils::formatRoleFromViewToDB).collect(Collectors.toList());
    }

    private static String formatRoleFromDBtoView(String role) {
        //from ROLE_ADMIN to Admin
        String roleNew = role.replace("ROLE_", "").toLowerCase();
        return Character.toUpperCase(roleNew.charAt(0)) + roleNew.substring(1);
    }

    private static String formatRoleFromViewToDB(String role) {
        return "ROLE_"+role.trim().toUpperCase();
    }
}
