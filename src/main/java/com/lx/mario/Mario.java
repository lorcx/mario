package com.lx.mario;

import com.lx.mario.config.ConfigLoader;
import com.lx.mario.render.Render;
import com.lx.mario.route.RouteManager;
import com.lx.mario.wrapper.Request;
import com.lx.mario.wrapper.Response;
import java.lang.reflect.Method;

/**
 * 单例模式 全局唯一
 * Created by lx on 2017/5/21.
 */
public class Mario {
    // route管理器
    private RouteManager routes;
    // 配置加载器
    private ConfigLoader configLoader;
    // 框架是否初始化
    private boolean init = false;
    // 渲染器
    private Render render;

    private Mario() {
        routes = new RouteManager();
        configLoader = new ConfigLoader();
    }

    private static class MarioHolder {
        private static Mario me = new Mario();
    }

    public static Mario me() {
        return MarioHolder.me;
    }

    public Mario addConf(String conf) {
        configLoader.load(conf);
        return this;
    }

    public String getConf(String name) {
        return configLoader.getConf(name);
    }

    public Mario addRoutes(RouteManager routes) {
        this.routes.addRoute(routes.getRoutes());
        return this;
    }

    public RouteManager getRoutes() {
        return routes;
    }

    /**
     * 添加路由
     * @return
     */
    public Mario addRoute(String path, String methodName, Object controller) {
        try {
            Method method = controller.getClass().getMethod(methodName, Request.class, Response.class);
            this.routes.addRoute(path, method, controller);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean isInit() {
        return init;
    }

    public RouteManager getRouteManager() {
        return null;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public Render getRender(){
        return render;
    }

    public void setRender(Render render) {
        this.render = render;
    }
}
