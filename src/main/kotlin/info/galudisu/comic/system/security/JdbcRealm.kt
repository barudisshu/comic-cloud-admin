package info.galudisu.comic.system.security

import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.subject.Subject


class JdbcRealm : org.apache.shiro.realm.jdbc.JdbcRealm() {

    internal fun getAuthorizationInfo(subject: Subject): AuthorizationInfo {
        return doGetAuthorizationInfo(subject.principals)
    }
}