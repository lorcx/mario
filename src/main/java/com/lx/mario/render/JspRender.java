package com.lx.mario.render;

import com.lx.mario.Const;
import com.lx.mario.Mario;
import com.lx.mario.MarioContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * jsp 渲染实现
 * Created by lx on 2017/5/21.
 */
public class JspRender implements Render{
    @Override
    public void render(String view, Writer writer) {
        String viewPath = this.getViewPath(view);
        HttpServletRequest request = MarioContext.me().getRequest().getRaw();
        HttpServletResponse response = MarioContext.me().getResponse().getRaw();
        try {
            request.getRequestDispatcher(viewPath).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getViewPath(String view) {
        Mario mario = Mario.me();
        String viewPrefix = mario.getConf(Const.VIEW_PREFIX_FIELD);
        String viewSuffix = mario.getConf(Const.VIEW_SUFFIX_FIELD);

        if (null == viewSuffix || "".equals(viewSuffix)) {
            viewSuffix = Const.VIEW_SUFFIX;
        }
        if (null == viewPrefix || "".equals(viewPrefix)) {
            viewPrefix = Const.VIEW_PREFIX;
        }
        String viewPath = viewPrefix + "/" + view;
        if (view.endsWith(viewSuffix)) {
            viewPath += viewSuffix;
        }
        return viewPath.replaceAll("[/]+", "/");
    }
}
