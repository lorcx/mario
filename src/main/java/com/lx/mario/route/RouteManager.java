package com.lx.mario.route;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 路由管理器，用来存放所有的路由
 * Created by lx on 2017/5/21.
 */
public class RouteManager {
    private static final Logger LOGGER = Logger.getLogger(RouteManager.class.getName());

    private List<Route> routes = new ArrayList<>();

    public void addRoute(List<Route> routes) {
        routes.addAll(routes);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void removeRoute(Route route) {
        routes.remove(route);
    }

    public void addRoute(String path, Method action, Object controller) {
        Route r = new Route();
        r.setAction(action);
        r.setController(controller);
        r.setPath(path);
        routes.add(r);
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
