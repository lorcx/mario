package com.lx.mario.render;

import com.lx.mario.Const;
import com.lx.mario.Mario;

import java.io.Writer;

/**
 * jsp 渲染实现
 * Created by lx on 2017/5/21.
 */
public class JspRender implements Render{
    @Override
    public void render(String view, Writer writer) {
        String viewPath = this.getViewPaht(view);
    }

    private String getViewPaht(String view) {
        Mario mario = Mario.me();
        String viewPrefix = mario.getConf(Const.VIEW_PREFIX_FIELD);
        String viewSuffix = mario.getConf(Const.VIEW_SUFFIX_FIELD);

        if (null == viewSuffix || "".equals(viewSuffix)) {
            viewSuffix = Const.VIEW_SUFFIX;
        }
        if (null == viewPrefix || "".equals(viewPrefix)) {
            viewPrefix = Const.VIEW_SUFFIX;
        }
        String viewPath = viewPrefix + "/" + view;
        if (view.endsWith(viewSuffix)) {
            viewPath += viewSuffix;
        }
        return viewPath.replaceAll("[/]+", "/");
    }
}
