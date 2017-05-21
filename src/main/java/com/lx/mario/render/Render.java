package com.lx.mario.render;

import java.io.Writer;

/**
 * 视图渲染接口
 * Created by lx on 2017/5/21.
 */
public interface Render {
    /**
     * 渲染到视图
     * @param view 视图名称
     * @param writer 写入对象
     */
    public void render(String view, Writer writer);
}
