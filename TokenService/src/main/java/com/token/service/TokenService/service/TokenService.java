package com.token.service.TokenService.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;
import java.io.UnsupportedEncodingException;
import java.util.Date;


@Service
public class TokenService {
    public static final String token_secret = "quei083nr8fjfh345q98m4aioweuf1";

    public String createToken(ObjectId userId){
        try {
            // Random generating String using with Token Secret.
            // We are using HMAC256 Algorithms to generate the token
            Algorithm algorithm = Algorithm.HMAC256(token_secret);

            // We are using claims of userId and create Date using Date() object
            String token =  JWT.
                                create().
                                withClaim("userId", userId.toString()).
                                withClaim("createdAt", new Date()).
                                sign(algorithm);
            return token;

        } catch (UnsupportedEncodingException | JWTCreationException e){
            e.printStackTrace();
        }
        return null;
    }

    // Decoding the created token
    public String getUserIdFromToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(token_secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            return decodedJWT.getClaim("userId").asString();

        } catch (UnsupportedEncodingException | JWTCreationException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean isTokenValid(String token){
        String userId = this.getUserIdFromToken(token);
        return  userId != null;
    }
}
