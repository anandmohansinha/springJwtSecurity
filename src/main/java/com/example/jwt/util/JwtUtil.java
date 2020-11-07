package com.example.jwt.util;

import com.example.jwt.entity.jwt.MinimalJWTUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String secret = "xadmin"; // we have to check this??

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        // here it will check if the token has created before time limit. i.e 10 hours then will will return true else false
        return extractExpiration(token).before(new Date());
    }

    // this method is for generating token. as argument is username. so as user first time send request with usernamr and password
    // so here we will fetch the username , so based on that username we are going to create one token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /*
    public String generateToken(MinimalJWTUser jwtUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", jwtUser.getRoles());
        return createToken(claims, jwtUser);
    }*/


    // in this method createToken subject argument is username
    // here we are setting the time for 10 hours to expire the token.
    // and you can see we are using HS256 algorithmn
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
/*
    private String createToken(Map<String, Object> claims, MinimalJWTUser jwtUser) {

        return Jwts.builder().setClaims(claims).setSubject(jwtUser.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }*/

    // here we are validation the token
    public Boolean validateToken(String token, UserDetails userDetails) {
        // basically token will be generated in encrpted string and from that string . we extract our usename and password using extractUsername method
        final String username = extractUsername(token);
        // here we are validation the username and then check the token is expired or not
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
/*
    public Boolean validateToken(String token, MinimalJWTUser jwtUser) {
        // basically token will be generated in encrpted string and from that string . we extract our usename and password using extractUsername method
        final String username = extractUsername(token);
        // here we are validation the username and then check the token is expired or not
        return ( username.equals(jwtUser.getUsername()) && !isTokenExpired(token));
    }
*/
    public  Boolean validateRole(String token, MinimalJWTUser jwtUser){
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        List<String> roleList = (List<String>) claims.get("roles");
        Boolean isRoleMatched = false;
        for(String role: jwtUser.getRoles()){
            if(roleList.contains(role)){
                isRoleMatched = true;
                break;
            }
        }
        return isRoleMatched;
    }
}
