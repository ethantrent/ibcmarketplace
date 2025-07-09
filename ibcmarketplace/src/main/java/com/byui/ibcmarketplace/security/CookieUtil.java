package com.byui.ibcmarketplace.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    @Value("${jwt.cookie.name:jwt-token}")
    private String cookieName;
    
    @Value("${jwt.cookie.max-age:86400}") // 1 day in seconds
    private int maxAge;
    
    @Value("${jwt.cookie.secure:false}") // Set to true in production with HTTPS
    private boolean secure;
    
    @Value("${jwt.cookie.http-only:true}")
    private boolean httpOnly;
    
    @Value("${jwt.cookie.same-site:Strict}")
    private String sameSite;

    public void createJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        // Note: SameSite is set via response headers in modern browsers
        response.addCookie(cookie);
        
        // Set SameSite attribute via header for modern browsers
        if ("Strict".equals(sameSite) || "Lax".equals(sameSite)) {
            response.setHeader("Set-Cookie", 
                String.format("%s=%s; Max-Age=%d; Path=/; HttpOnly=%s; Secure=%s; SameSite=%s",
                    cookieName, token, maxAge, httpOnly, secure, sameSite));
        }
    }

    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void clearJwtCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        response.addCookie(cookie);
    }
} 