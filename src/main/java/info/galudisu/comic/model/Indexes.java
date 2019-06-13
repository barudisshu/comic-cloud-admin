/*
 * This file is generated by jOOQ.
 */
package info.galudisu.comic.model;


import info.galudisu.comic.model.tables.Account;
import info.galudisu.comic.model.tables.AccountRole;
import info.galudisu.comic.model.tables.Clients;
import info.galudisu.comic.model.tables.Permission;
import info.galudisu.comic.model.tables.Role;
import info.galudisu.comic.model.tables.RolePermission;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ACCOUNT_ACCOUNT_SALT_IDX = Indexes0.ACCOUNT_ACCOUNT_SALT_IDX;
    public static final Index ACCOUNT_ACCOUNT_USERNAME_IDX = Indexes0.ACCOUNT_ACCOUNT_USERNAME_IDX;
    public static final Index ACCOUNT_ID = Indexes0.ACCOUNT_ID;
    public static final Index ACCOUNT_PRIMARY = Indexes0.ACCOUNT_PRIMARY;
    public static final Index ACCOUNT_SALT = Indexes0.ACCOUNT_SALT;
    public static final Index ACCOUNT_UID = Indexes0.ACCOUNT_UID;
    public static final Index ACCOUNT_ROLE_PRIMARY = Indexes0.ACCOUNT_ROLE_PRIMARY;
    public static final Index CLIENTS_CLIENT_CLIENT_ID_IDX = Indexes0.CLIENTS_CLIENT_CLIENT_ID_IDX;
    public static final Index CLIENTS_CLIENT_CLIENT_SECRET_IDX = Indexes0.CLIENTS_CLIENT_CLIENT_SECRET_IDX;
    public static final Index CLIENTS_CLIENT_ID = Indexes0.CLIENTS_CLIENT_ID;
    public static final Index CLIENTS_CLIENT_SECRET = Indexes0.CLIENTS_CLIENT_SECRET;
    public static final Index CLIENTS_ID = Indexes0.CLIENTS_ID;
    public static final Index CLIENTS_OWNER_ID = Indexes0.CLIENTS_OWNER_ID;
    public static final Index CLIENTS_PRIMARY = Indexes0.CLIENTS_PRIMARY;
    public static final Index CLIENTS_UID = Indexes0.CLIENTS_UID;
    public static final Index PERMISSION_ID = Indexes0.PERMISSION_ID;
    public static final Index PERMISSION_PERMISSION_CODE_IDX = Indexes0.PERMISSION_PERMISSION_CODE_IDX;
    public static final Index PERMISSION_PERMISSION_INDEX_IDX = Indexes0.PERMISSION_PERMISSION_INDEX_IDX;
    public static final Index PERMISSION_PRIMARY = Indexes0.PERMISSION_PRIMARY;
    public static final Index PERMISSION_UID = Indexes0.PERMISSION_UID;
    public static final Index ROLE_ID = Indexes0.ROLE_ID;
    public static final Index ROLE_PRIMARY = Indexes0.ROLE_PRIMARY;
    public static final Index ROLE_ROLE_CODE_IDX = Indexes0.ROLE_ROLE_CODE_IDX;
    public static final Index ROLE_ROLE_TITLE_IDX = Indexes0.ROLE_ROLE_TITLE_IDX;
    public static final Index ROLE_UID = Indexes0.ROLE_UID;
    public static final Index ROLE_PERMISSION_PRIMARY = Indexes0.ROLE_PERMISSION_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ACCOUNT_ACCOUNT_SALT_IDX = Internal.createIndex("ACCOUNT_SALT_IDX", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.SALT }, true);
        public static Index ACCOUNT_ACCOUNT_USERNAME_IDX = Internal.createIndex("ACCOUNT_USERNAME_IDX", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.USERNAME }, true);
        public static Index ACCOUNT_ID = Internal.createIndex("ID", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.ID }, true);
        public static Index ACCOUNT_PRIMARY = Internal.createIndex("PRIMARY", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.ID }, true);
        public static Index ACCOUNT_SALT = Internal.createIndex("SALT", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.SALT }, true);
        public static Index ACCOUNT_UID = Internal.createIndex("UID", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.UID }, true);
        public static Index ACCOUNT_ROLE_PRIMARY = Internal.createIndex("PRIMARY", AccountRole.ACCOUNT_ROLE, new OrderField[] { AccountRole.ACCOUNT_ROLE.ACCOUNT_UID, AccountRole.ACCOUNT_ROLE.ROLE_UID }, true);
        public static Index CLIENTS_CLIENT_CLIENT_ID_IDX = Internal.createIndex("CLIENT_CLIENT_ID_IDX", Clients.CLIENTS, new OrderField[] { Clients.CLIENTS.CLIENT_ID }, true);
        public static Index CLIENTS_CLIENT_CLIENT_SECRET_IDX = Internal.createIndex("CLIENT_CLIENT_SECRET_IDX", Clients.CLIENTS, new OrderField[] { Clients.CLIENTS.CLIENT_SECRET }, true);
        public static Index CLIENTS_CLIENT_ID = Internal.createIndex("CLIENT_ID", Clients.CLIENTS, new OrderField[] { Clients.CLIENTS.CLIENT_ID }, true);
        public static Index CLIENTS_CLIENT_SECRET = Internal.createIndex("CLIENT_SECRET", Clients.CLIENTS, new OrderField[] { Clients.CLIENTS.CLIENT_SECRET }, true);
        public static Index CLIENTS_ID = Internal.createIndex("ID", Clients.CLIENTS, new OrderField[] { Clients.CLIENTS.ID }, true);
        public static Index CLIENTS_OWNER_ID = Internal.createIndex("OWNER_ID", Clients.CLIENTS, new OrderField[] { Clients.CLIENTS.OWNER_ID }, true);
        public static Index CLIENTS_PRIMARY = Internal.createIndex("PRIMARY", Clients.CLIENTS, new OrderField[] { Clients.CLIENTS.ID }, true);
        public static Index CLIENTS_UID = Internal.createIndex("UID", Clients.CLIENTS, new OrderField[] { Clients.CLIENTS.UID }, true);
        public static Index PERMISSION_ID = Internal.createIndex("ID", Permission.PERMISSION, new OrderField[] { Permission.PERMISSION.ID }, true);
        public static Index PERMISSION_PERMISSION_CODE_IDX = Internal.createIndex("PERMISSION_CODE_IDX", Permission.PERMISSION, new OrderField[] { Permission.PERMISSION.CODE }, true);
        public static Index PERMISSION_PERMISSION_INDEX_IDX = Internal.createIndex("PERMISSION_INDEX_IDX", Permission.PERMISSION, new OrderField[] { Permission.PERMISSION.INDEX }, true);
        public static Index PERMISSION_PRIMARY = Internal.createIndex("PRIMARY", Permission.PERMISSION, new OrderField[] { Permission.PERMISSION.ID }, true);
        public static Index PERMISSION_UID = Internal.createIndex("UID", Permission.PERMISSION, new OrderField[] { Permission.PERMISSION.UID }, true);
        public static Index ROLE_ID = Internal.createIndex("ID", Role.ROLE, new OrderField[] { Role.ROLE.ID }, true);
        public static Index ROLE_PRIMARY = Internal.createIndex("PRIMARY", Role.ROLE, new OrderField[] { Role.ROLE.ID }, true);
        public static Index ROLE_ROLE_CODE_IDX = Internal.createIndex("ROLE_CODE_IDX", Role.ROLE, new OrderField[] { Role.ROLE.CODE }, true);
        public static Index ROLE_ROLE_TITLE_IDX = Internal.createIndex("ROLE_TITLE_IDX", Role.ROLE, new OrderField[] { Role.ROLE.TITLE }, true);
        public static Index ROLE_UID = Internal.createIndex("UID", Role.ROLE, new OrderField[] { Role.ROLE.UID }, true);
        public static Index ROLE_PERMISSION_PRIMARY = Internal.createIndex("PRIMARY", RolePermission.ROLE_PERMISSION, new OrderField[] { RolePermission.ROLE_PERMISSION.ROLE_UID, RolePermission.ROLE_PERMISSION.PERMISSION_UID }, true);
    }
}
