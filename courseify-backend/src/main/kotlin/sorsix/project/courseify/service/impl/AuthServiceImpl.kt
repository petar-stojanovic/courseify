package sorsix.project.courseify.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sorsix.project.courseify.api.request.AuthenticationRequest
import sorsix.project.courseify.api.request.RegisterRequest
import sorsix.project.courseify.domain.User
import sorsix.project.courseify.domain.response.AuthenticationResponse
import sorsix.project.courseify.repository.UserRepository
import sorsix.project.courseify.security.config.JwtService
import sorsix.project.courseify.security.token.Token
import sorsix.project.courseify.security.token.TokenRepository
import sorsix.project.courseify.security.token.TokenType
import sorsix.project.courseify.service.definitions.AuthService
import javax.naming.AuthenticationException

@Service
class AuthServiceImpl(
    val userRepository: UserRepository,
    val tokenRepository: TokenRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtService: JwtService,
    val authenticationManager: AuthenticationManager
) : AuthService {

    override fun register(request: RegisterRequest): AuthenticationResponse {
        val user = User(
            0,
            request.firstName,
            request.lastName,
            request.email,
            passwordEncoder.encode(request.password),
            request.username,
            request.role
        )
        val savedUser = userRepository.save(user);
        val jwtToken = jwtService.generateToken(user);
        val refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse(
            jwtToken,
            refreshToken
        )
    }

    override fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )
        val user = userRepository.findByUsername(request.username) ?: throw AuthenticationException("User not found")
        val jwtToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        revokeAllUserTokens(user)
        saveUserToken(user, jwtToken)

        return AuthenticationResponse(jwtToken, refreshToken)
    }


    fun saveUserToken(user: User, jwtToken: String) {
        val token = Token(
            token = jwtToken,
            tokenType = TokenType.BEARER,
            revoked = false,
            expired = false,
            user = user
        )
        tokenRepository.save(token)
    }

    fun revokeAllUserTokens(user: User) {
        val validUserTokens = tokenRepository.findAllValidTokenByUser(user.id) ?: return
        val modifiedTokens = validUserTokens.map { token ->
            token.copy(revoked = true, expired = true)
        }
        tokenRepository.saveAll(modifiedTokens)

        /**
         * Github with var instead of val:
         * private void revokeAllUserTokens(User user) {
         *     var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
         *     if (validUserTokens.isEmpty())
         *       return;
         *     validUserTokens.forEach(token -> {
         *       token.setExpired(true);
         *       token.setRevoked(true);
         *     });
         *     tokenRepository.saveAll(validUserTokens);
         *   }
         */


    }


    override fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val refreshToken: String
        val userEmail: String
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }
        refreshToken = authHeader.substring(7)
        userEmail = jwtService.extractUsername(refreshToken)
        if (userEmail != null) {
            val user = userRepository.findByEmail(userEmail) ?: throw AuthenticationException("User not found")

            if (jwtService.isTokenValid(refreshToken, user)) {
                val accessToken = jwtService.generateToken(user)
                revokeAllUserTokens(user)
                saveUserToken(user, accessToken)
                val authResponse = AuthenticationResponse(accessToken, refreshToken)
                ObjectMapper().writeValue(response.outputStream, authResponse)
            }
        }
    }

}
