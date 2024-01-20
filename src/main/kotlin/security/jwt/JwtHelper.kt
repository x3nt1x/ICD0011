package security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import security.TokenInfo
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey

class JwtHelper(key: String) {
    private val key: SecretKey

    init {
        this.key = Keys.hmacShaKeyFor(key.toByteArray())
    }

    fun encode(tokenInfo: TokenInfo): String {
        return encode(tokenInfo, LocalDateTime.now().plusMinutes(15))
    }

    fun encode(tokenInfo: TokenInfo, expiration: LocalDateTime): String {
        return Jwts.builder()
                   .signWith(key, Jwts.SIG.HS512)
                   .subject(tokenInfo.username)
                   .expiration(asDate(expiration))
                   .claim("roles", tokenInfo.getRolesAsString())
                   .compact()
    }

    fun decode(token: String): TokenInfo {
        val modifiedToken = token.replace("Bearer ", "")

        val claims = Jwts.parser()
                         .verifyWith(key)
                         .build()
                         .parseSignedClaims(modifiedToken)
                         .payload

        return TokenInfo(claims.subject, claims.get("roles", String::class.java))
    }

    private fun asDate(dateTime: LocalDateTime): Date {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}