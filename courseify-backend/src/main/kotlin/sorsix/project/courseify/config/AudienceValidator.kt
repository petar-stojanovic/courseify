//package sorsix.project.courseify.config
//
//import org.springframework.security.oauth2.core.OAuth2Error
//import org.springframework.security.oauth2.core.OAuth2TokenValidator
//import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
//import org.springframework.security.oauth2.jwt.Jwt
//
//class AudienceValidator(val audience: String) : OAuth2TokenValidator<Jwt> {
//
//
//    override fun validate(token: Jwt): OAuth2TokenValidatorResult {
//        val error = OAuth2Error(
//            "invalid_token",
//            "The required audience is missing", null
//        );
//
//        if (token.audience.contains(audience)) {
//            return OAuth2TokenValidatorResult.success();
//        }
//
//        return OAuth2TokenValidatorResult.failure(error);
//    }
//}
//
