/*
 * This file is generated by jOOQ.
 */
package info.galudisu.comic.model;


import info.galudisu.comic.model.tables.RolesPermissions;
import info.galudisu.comic.model.tables.UserRoles;
import info.galudisu.comic.model.tables.Users;
import info.galudisu.comic.model.tables.records.RolesPermissionsRecord;
import info.galudisu.comic.model.tables.records.UserRolesRecord;
import info.galudisu.comic.model.tables.records.UsersRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<RolesPermissionsRecord, Integer> IDENTITY_ROLES_PERMISSIONS = Identities0.IDENTITY_ROLES_PERMISSIONS;
    public static final Identity<UserRolesRecord, Integer> IDENTITY_USER_ROLES = Identities0.IDENTITY_USER_ROLES;
    public static final Identity<UsersRecord, Integer> IDENTITY_USERS = Identities0.IDENTITY_USERS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_PRIMARY = UniqueKeys0.KEY_ROLES_PERMISSIONS_PRIMARY;
    public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_ID = UniqueKeys0.KEY_ROLES_PERMISSIONS_ID;
    public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_UID = UniqueKeys0.KEY_ROLES_PERMISSIONS_UID;
    public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_ROLES_PERMISSIONS_ROLE_NAME_IDX = UniqueKeys0.KEY_ROLES_PERMISSIONS_ROLES_PERMISSIONS_ROLE_NAME_IDX;
    public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_ROLES_PERMISSIONS_PERMISSION_IDX = UniqueKeys0.KEY_ROLES_PERMISSIONS_ROLES_PERMISSIONS_PERMISSION_IDX;
    public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_PRIMARY = UniqueKeys0.KEY_USER_ROLES_PRIMARY;
    public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_ID = UniqueKeys0.KEY_USER_ROLES_ID;
    public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_UID = UniqueKeys0.KEY_USER_ROLES_UID;
    public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_USER_ROLES_USERNAME_IDX = UniqueKeys0.KEY_USER_ROLES_USER_ROLES_USERNAME_IDX;
    public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_USER_ROLES_ROLE_NAME_IDX = UniqueKeys0.KEY_USER_ROLES_USER_ROLES_ROLE_NAME_IDX;
    public static final UniqueKey<UsersRecord> KEY_USERS_PRIMARY = UniqueKeys0.KEY_USERS_PRIMARY;
    public static final UniqueKey<UsersRecord> KEY_USERS_ID = UniqueKeys0.KEY_USERS_ID;
    public static final UniqueKey<UsersRecord> KEY_USERS_UID = UniqueKeys0.KEY_USERS_UID;
    public static final UniqueKey<UsersRecord> KEY_USERS_USERS_USERNAME_IDX = UniqueKeys0.KEY_USERS_USERS_USERNAME_IDX;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<RolesPermissionsRecord, Integer> IDENTITY_ROLES_PERMISSIONS = Internal.createIdentity(RolesPermissions.ROLES_PERMISSIONS, RolesPermissions.ROLES_PERMISSIONS.ID);
        public static Identity<UserRolesRecord, Integer> IDENTITY_USER_ROLES = Internal.createIdentity(UserRoles.USER_ROLES, UserRoles.USER_ROLES.ID);
        public static Identity<UsersRecord, Integer> IDENTITY_USERS = Internal.createIdentity(Users.USERS, Users.USERS.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_PRIMARY = Internal.createUniqueKey(RolesPermissions.ROLES_PERMISSIONS, "KEY_roles_permissions_PRIMARY", RolesPermissions.ROLES_PERMISSIONS.ID);
        public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_ID = Internal.createUniqueKey(RolesPermissions.ROLES_PERMISSIONS, "KEY_roles_permissions_ID", RolesPermissions.ROLES_PERMISSIONS.ID);
        public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_UID = Internal.createUniqueKey(RolesPermissions.ROLES_PERMISSIONS, "KEY_roles_permissions_UID", RolesPermissions.ROLES_PERMISSIONS.UID);
        public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_ROLES_PERMISSIONS_ROLE_NAME_IDX = Internal.createUniqueKey(RolesPermissions.ROLES_PERMISSIONS, "KEY_roles_permissions_ROLES_PERMISSIONS_ROLE_NAME_IDX", RolesPermissions.ROLES_PERMISSIONS.ROLE_NAME);
        public static final UniqueKey<RolesPermissionsRecord> KEY_ROLES_PERMISSIONS_ROLES_PERMISSIONS_PERMISSION_IDX = Internal.createUniqueKey(RolesPermissions.ROLES_PERMISSIONS, "KEY_roles_permissions_ROLES_PERMISSIONS_PERMISSION_IDX", RolesPermissions.ROLES_PERMISSIONS.PERMISSION);
        public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_PRIMARY = Internal.createUniqueKey(UserRoles.USER_ROLES, "KEY_user_roles_PRIMARY", UserRoles.USER_ROLES.ID);
        public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_ID = Internal.createUniqueKey(UserRoles.USER_ROLES, "KEY_user_roles_ID", UserRoles.USER_ROLES.ID);
        public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_UID = Internal.createUniqueKey(UserRoles.USER_ROLES, "KEY_user_roles_UID", UserRoles.USER_ROLES.UID);
        public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_USER_ROLES_USERNAME_IDX = Internal.createUniqueKey(UserRoles.USER_ROLES, "KEY_user_roles_USER_ROLES_USERNAME_IDX", UserRoles.USER_ROLES.USERNAME);
        public static final UniqueKey<UserRolesRecord> KEY_USER_ROLES_USER_ROLES_ROLE_NAME_IDX = Internal.createUniqueKey(UserRoles.USER_ROLES, "KEY_user_roles_USER_ROLES_ROLE_NAME_IDX", UserRoles.USER_ROLES.ROLE_NAME);
        public static final UniqueKey<UsersRecord> KEY_USERS_PRIMARY = Internal.createUniqueKey(Users.USERS, "KEY_users_PRIMARY", Users.USERS.ID);
        public static final UniqueKey<UsersRecord> KEY_USERS_ID = Internal.createUniqueKey(Users.USERS, "KEY_users_ID", Users.USERS.ID);
        public static final UniqueKey<UsersRecord> KEY_USERS_UID = Internal.createUniqueKey(Users.USERS, "KEY_users_UID", Users.USERS.UID);
        public static final UniqueKey<UsersRecord> KEY_USERS_USERS_USERNAME_IDX = Internal.createUniqueKey(Users.USERS, "KEY_users_USERS_USERNAME_IDX", Users.USERS.USERNAME);
    }
}
