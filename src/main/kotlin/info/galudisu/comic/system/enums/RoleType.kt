package info.galudisu.comic.system.enums

/**
 * 角色类型
 */
enum class RoleType private constructor(// 类型
        val type: Int, // 当前角色包含的权限类型
        val includePermissionTypes: IntArray, // 描述
        val desc: String) {
    SYSTEM_ADMINISTRATOR(1, intArrayOf(PermissionType.SYSTEM.type, PermissionType.ADMIN.type), "超级管理员"),
    ADMINISTRATOR(2, intArrayOf(PermissionType.ADMIN.type), "管理员"),
    USER(3, intArrayOf(PermissionType.USER.type), "普通用户");


    companion object {

        fun getRoleType(type: Int): RoleType {
            val ts = RoleType.values()
            for (t in ts) {
                if (t.type == type) {
                    return t
                }
            }
            return USER
        }
    }
}