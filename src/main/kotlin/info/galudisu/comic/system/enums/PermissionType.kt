package info.galudisu.comic.system.enums

/**
 * 权限类型
 */
enum class PermissionType private constructor(// 类型
        val type: Int, // 描述
        val desc: String) {
    SYSTEM(1, "系统管理权限"),
    ADMIN(2, "管理权限"),
    USER(3, "普通用户权限");


    companion object {

        fun getPermissionType(type: Int): PermissionType {
            val ts = PermissionType.values()
            for (t in ts) {
                if (t.type == type) {
                    return t
                }
            }
            return USER
        }
    }
}