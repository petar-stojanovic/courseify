package sorsix.project.courseify.security.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service
import sorsix.project.courseify.security.token.Token
import sorsix.project.courseify.security.token.TokenRepository

@Service
data class LogoutService(
    val tokenRepository: TokenRepository
) : LogoutHandler {

    override fun logout(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val authHeader: String? = request?.getHeader("Authorization")
        val jwt: String
        if (authHeader == null || authHeader.startsWith("Bearer ")) {
            return
        }
        jwt = authHeader.substring(7)
        var storedToken = tokenRepository.findByToken(jwt)

        /**
         * This is from GitHub
         * if (storedToken != null) {
         *             storedToken.expired = true
         *             storedToken.revoked = true
         *             tokenRepository.save(storedToken)
         *             SecurityContextHolder.clearContext()
         *         }
         */

        if (storedToken != null) {
            val newToken = Token(
                storedToken.id,
                storedToken.token,
                storedToken.tokenType,
                true,
                true,
                storedToken.user
            )
            tokenRepository.save(newToken)
            SecurityContextHolder.clearContext()
        }

    }
}
