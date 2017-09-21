package com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities;

public class Role {
    public static final String ROLE_METAL = "role_metal";
    public static final String ROLE_BRONZE = "role_bronze";
    public static final String ROLE_SILVER = "role_silver";
    public static final String ROLE_GOLD = "role_gold";
    public static final String ROLE_SUPER_ADMIN = "role_super_admin";

    private Role() {
        // Static methods and fields only
    }

    public static String[] getAllRoles() {
        return new String[]{ROLE_METAL, ROLE_BRONZE, ROLE_SILVER, ROLE_GOLD, ROLE_SUPER_ADMIN};
    }

}
