//package sorsix.project.courseify.config
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.Customizer
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
//import org.springframework.security.oauth2.core.OAuth2TokenValidator
//import org.springframework.security.oauth2.jwt.*
//import org.springframework.security.oauth2.jwt.JwtDecoders.fromOidcIssuerLocation
//import org.springframework.security.web.SecurityFilterChain
//
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig {
//
//
//    @Value("v18j4NbapYFBBVPimcu71PKXbTjwFiGL")
//    val audience: String = ""
//
//    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
//    val issuer: String = ""
//
//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http.cors(Customizer.withDefaults())
////            .authorizeHttpRequests {
////                it
////                    .anyRequest().authenticated()
////            }
//            .sessionManagement {
//                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            }
//            .oauth2ResourceServer {
//                it.jwt {
//                    it.decoder(jwtDecoder())
//                }
//            }
//        return http.build()
//    }
//
//
//    @Bean
//    fun jwtDecoder(): JwtDecoder {
//        val jwtDecoder = fromOidcIssuerLocation<JwtDecoder>(issuer) as NimbusJwtDecoder
//
//
//        val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(audience)
//        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
//        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
//
//
//        jwtDecoder.setJwtValidator(withAudience)
//
//        return jwtDecoder
//    }
//
//}
