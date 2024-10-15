package com.manusoft.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

public class CoockeUtils {

    /**
     * Adds a cookie to the HttpServletResponse if the cookie name is not blank.
     * If the client does not accept cookies, the value is added as a header.
     *
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param response the HttpServletResponse object to add the cookie or header to
     */
    public static void addCookie( String name, String value,HttpServletResponse response) {
        if(StringUtils.hasText(name)){

            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(60*60*24);

            // If client not accepting cookies then use headers.
            response.addHeader(name, value);
            response.addCookie(cookie);


        }
    }
    /**
     * Removes a cookie by name from the HttpServletResponse.
     *
     * @param name the name of the cookie to be removed
     * @param response the HttpServletResponse object
     */
    public static void removeCookie( String name,HttpServletResponse response) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
    /**
     * Retrieves the value of a cookie with the given name from the request headers or cookies.
     * If the cookie is not found in the headers, it searches through the cookies.
     *
     * @param name the name of the cookie to retrieve
     * @param request the HttpServletRequest object containing the headers and cookies
     * @return the value of the cookie with the given name, or an empty string if not found
     */
    public static String getCookieValue(String name, HttpServletRequest request) {
        String accessToken = request.getHeader(name);
        if(StringUtils.hasText(accessToken) &&StringUtils.hasText(name)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equalsIgnoreCase(name)) {
                        accessToken = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return accessToken;
    }


}
