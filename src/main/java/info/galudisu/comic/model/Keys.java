/*
 * This file is generated by jOOQ.
 */
package info.galudisu.comic.model;


import info.galudisu.comic.model.tables.Accounts;
import info.galudisu.comic.model.tables.Clients;
import info.galudisu.comic.model.tables.records.AccountsRecord;
import info.galudisu.comic.model.tables.records.ClientsRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>comic</code> schema.
 */
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

    public static final Identity<AccountsRecord, Integer> IDENTITY_ACCOUNTS = Identities0.IDENTITY_ACCOUNTS;
    public static final Identity<ClientsRecord, Integer> IDENTITY_CLIENTS = Identities0.IDENTITY_CLIENTS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_PRIMARY = UniqueKeys0.KEY_ACCOUNTS_PRIMARY;
    public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_ID = UniqueKeys0.KEY_ACCOUNTS_ID;
    public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_UID = UniqueKeys0.KEY_ACCOUNTS_UID;
    public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_ACCOUNT_USERNAME_IDX = UniqueKeys0.KEY_ACCOUNTS_ACCOUNT_USERNAME_IDX;
    public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_ACCOUNT_SALT_IDX = UniqueKeys0.KEY_ACCOUNTS_ACCOUNT_SALT_IDX;
    public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_SALT = UniqueKeys0.KEY_ACCOUNTS_SALT;
    public static final UniqueKey<ClientsRecord> KEY_CLIENTS_PRIMARY = UniqueKeys0.KEY_CLIENTS_PRIMARY;
    public static final UniqueKey<ClientsRecord> KEY_CLIENTS_ID = UniqueKeys0.KEY_CLIENTS_ID;
    public static final UniqueKey<ClientsRecord> KEY_CLIENTS_UID = UniqueKeys0.KEY_CLIENTS_UID;
    public static final UniqueKey<ClientsRecord> KEY_CLIENTS_OWNER_ID = UniqueKeys0.KEY_CLIENTS_OWNER_ID;
    public static final UniqueKey<ClientsRecord> KEY_CLIENTS_CLIENT_CLIENT_ID_IDX = UniqueKeys0.KEY_CLIENTS_CLIENT_CLIENT_ID_IDX;
    public static final UniqueKey<ClientsRecord> KEY_CLIENTS_CLIENT_ID = UniqueKeys0.KEY_CLIENTS_CLIENT_ID;
    public static final UniqueKey<ClientsRecord> KEY_CLIENTS_CLIENT_CLIENT_SECRET_IDX = UniqueKeys0.KEY_CLIENTS_CLIENT_CLIENT_SECRET_IDX;
    public static final UniqueKey<ClientsRecord> KEY_CLIENTS_CLIENT_SECRET = UniqueKeys0.KEY_CLIENTS_CLIENT_SECRET;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<AccountsRecord, Integer> IDENTITY_ACCOUNTS = Internal.createIdentity(Accounts.ACCOUNTS, Accounts.ACCOUNTS.ID);
        public static Identity<ClientsRecord, Integer> IDENTITY_CLIENTS = Internal.createIdentity(Clients.CLIENTS, Clients.CLIENTS.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_PRIMARY = Internal.createUniqueKey(Accounts.ACCOUNTS, "KEY_accounts_PRIMARY", Accounts.ACCOUNTS.ID);
        public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_ID = Internal.createUniqueKey(Accounts.ACCOUNTS, "KEY_accounts_ID", Accounts.ACCOUNTS.ID);
        public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_UID = Internal.createUniqueKey(Accounts.ACCOUNTS, "KEY_accounts_UID", Accounts.ACCOUNTS.UID);
        public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_ACCOUNT_USERNAME_IDX = Internal.createUniqueKey(Accounts.ACCOUNTS, "KEY_accounts_ACCOUNT_USERNAME_IDX", Accounts.ACCOUNTS.USERNAME);
        public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_ACCOUNT_SALT_IDX = Internal.createUniqueKey(Accounts.ACCOUNTS, "KEY_accounts_ACCOUNT_SALT_IDX", Accounts.ACCOUNTS.SALT);
        public static final UniqueKey<AccountsRecord> KEY_ACCOUNTS_SALT = Internal.createUniqueKey(Accounts.ACCOUNTS, "KEY_accounts_SALT", Accounts.ACCOUNTS.SALT);
        public static final UniqueKey<ClientsRecord> KEY_CLIENTS_PRIMARY = Internal.createUniqueKey(Clients.CLIENTS, "KEY_clients_PRIMARY", Clients.CLIENTS.ID);
        public static final UniqueKey<ClientsRecord> KEY_CLIENTS_ID = Internal.createUniqueKey(Clients.CLIENTS, "KEY_clients_ID", Clients.CLIENTS.ID);
        public static final UniqueKey<ClientsRecord> KEY_CLIENTS_UID = Internal.createUniqueKey(Clients.CLIENTS, "KEY_clients_UID", Clients.CLIENTS.UID);
        public static final UniqueKey<ClientsRecord> KEY_CLIENTS_OWNER_ID = Internal.createUniqueKey(Clients.CLIENTS, "KEY_clients_OWNER_ID", Clients.CLIENTS.OWNER_ID);
        public static final UniqueKey<ClientsRecord> KEY_CLIENTS_CLIENT_CLIENT_ID_IDX = Internal.createUniqueKey(Clients.CLIENTS, "KEY_clients_CLIENT_CLIENT_ID_IDX", Clients.CLIENTS.CLIENT_ID);
        public static final UniqueKey<ClientsRecord> KEY_CLIENTS_CLIENT_ID = Internal.createUniqueKey(Clients.CLIENTS, "KEY_clients_CLIENT_ID", Clients.CLIENTS.CLIENT_ID);
        public static final UniqueKey<ClientsRecord> KEY_CLIENTS_CLIENT_CLIENT_SECRET_IDX = Internal.createUniqueKey(Clients.CLIENTS, "KEY_clients_CLIENT_CLIENT_SECRET_IDX", Clients.CLIENTS.CLIENT_SECRET);
        public static final UniqueKey<ClientsRecord> KEY_CLIENTS_CLIENT_SECRET = Internal.createUniqueKey(Clients.CLIENTS, "KEY_clients_CLIENT_SECRET", Clients.CLIENTS.CLIENT_SECRET);
    }
}
