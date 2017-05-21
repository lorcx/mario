package com.lx.mario.wrapper;

import com.lx.mario.Mario;
import com.lx.mario.render.Render;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * HttpServletResponse增强
 * Created by lx on 2017/5/21.
 */
public class Response {
    private HttpServletResponse raw;

    private Render render;

    public Response(HttpServletResponse response) {
        this.raw = response;
        this.raw.setHeader("Framework", "Mario");
        this.render = Mario.me().getRender();
    }

    public void text(String text) {
        raw.setContentType("text/plan;charset=UTF-8");
        this.print(text);
    }

    public void html(String html) {
        raw.setContentType("text/html;charset=UTF-8");
        this.print(html);
    }

    private void print(String text) {
        try {
            OutputStream os = raw.getOutputStream();
            os.write(text.getBytes());
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        raw.addCookie(cookie);
    }

    public HttpServletResponse getRaw() {
        return  raw;
    }

    public void render(String view) {
        render.render(view, null);
    }

    public void redirect(String location) {
        try {
            raw.sendRedirect(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
