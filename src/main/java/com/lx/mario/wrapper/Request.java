package com.lx.mario.wrapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletReuqest 增强
 * Created by lx on 2017/5/21.
 */
public class Request {
    private HttpServletRequest raw;

    public Request(HttpServletRequest request) {
        this.raw = request;
    }

    public HttpServletRequest getRaw() {
        return raw;
    }

    public void attr(String name, Object value) {
        raw.setAttribute(name, value);
    }

    public <T> T attr(String name) {
        Object value = raw.getAttribute(name);
        if (null != value) {
            return (T) value;
        }
        return null;
    }

    public Integer queryAsInt(String name) {
        String val = query(name);
        if (null != val && !"".equals(val)) {
            return Integer.valueOf(val);
        }
        return null;
    }

    public String query(String name) {
        return raw.getParameter(name);
    }

    public String cookie(String name) {
        Cookie[] cookies = raw.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
