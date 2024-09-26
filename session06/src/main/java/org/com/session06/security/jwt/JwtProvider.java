package org.com.session06.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Component;
import org.com.session06.security.UserPrinciple;

import java.util.Date;
@Component
public class JwtProvider {

    @Value("${expired}")
    private Long EXPIRED;

    @Value("${secret_key}")
    private String SECRET_KEY;

    private Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    public String generateToken(UserPrinciple userPrinciple){
        // tạo thời gian sống của token
        Date dateExpiration = new Date(new Date().getTime() + EXPIRED);

        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setExpiration(dateExpiration)
                .compact();
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token);
            return true;
        }catch (ExpressionException | SignatureException | MalformedJwtException exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }

    public String getUserNameFromToken(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }
}
