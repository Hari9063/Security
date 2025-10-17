package com.DemoSecurity.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
	
	private String secretKey;
	
	public JwtService(){
		KeyGenerator kg;
		try {
			kg = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk=kg.generateKey();
			secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String generateKey(String username){
		
		Map<String,Object> claims =new HashMap<>();
		
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+60*2*1000))
				.and()
				.signWith(getKey())
				.compact()
				;
		
	}

	private SecretKey getKey() {
		
		byte[] key=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(key);
		
	}

	public boolean validateToken
	(String token , UserDetails userDetails) {
		final String userName =extractUsername(token);
		
		return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return etractExpiration(token).before(new Date());
	}

	private Date etractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration);
	}

	public String extractUsername(String token) {
		
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
		final Claims claims= extractAllClaims(token);
		return claimResolver.apply(claims);
		// TODO Auto-generated method stub
		
	}

	private Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
				
	}





}