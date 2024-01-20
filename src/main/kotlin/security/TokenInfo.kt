package security

data class TokenInfo(val username: String, val roles: List<String>) {
    constructor(userName: String, roles: String) : this(
        username = userName,
        roles = roles.split(", ")
    )

    fun getRolesAsString(): String {
        return roles.joinToString(", ")
    }
}