package info.galudisu.comic.system.enums

enum class AuthType constructor(
        val type: String,
        val isCheckPasswordInd: Boolean,
        val desc: String) {

    WEB("WEB", true, "表单登录"),
    API("API", true, "接口登录"),
    SSO("SSO", false, "单点登录");

    companion object {
        fun getAuthType(type: String): AuthType {
            val ts = AuthType.values()
            for (t in ts) {
                if (t.type.equals(type, ignoreCase = true)) {
                    return t
                }
            }
            return WEB
        }
    }
}