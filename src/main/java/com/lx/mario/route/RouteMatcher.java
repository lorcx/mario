package com.lx.mario.route;

import com.lx.mario.util.PathUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 路由匹配器,用于匹配路由
 * Created by lx on 2017/5/21.
 */
public class RouteMatcher {
    private List<Route> routes;

    public RouteMatcher(List<Route> routes) {
        this.routes = routes;
    }

    /**
     * 根据path查找路由
     * @param path 请求的地址
     * @return 返回查找到的路由
     */
    public Route findRoute(String path) {
        String cleanPath = parsePath(path);
        List<Route> matchRoutes = new ArrayList<>();
        for (Route route : this.routes) {
            if (matchesPath(route.getPath(), cleanPath)) {
                matchRoutes.add(route);
            }
        }
        // 优先匹配原则
        giveMatch(path, matchRoutes);
        return matchRoutes.size() > 0 ? matchRoutes.get(0) : null;
    }

    private void giveMatch(final String uri, List<Route> routes) {
        Collections.sort(routes, new Comparator<Route>() {
            @Override
            public int compare(Route o1, Route o2) {
                if (o2.getPath().equals(uri)) {
                    return o2.getPath().indexOf(uri);
                }
                return -1;
            }
        });
    }

    private boolean matchesPath(String path, String cleanPath) {
        path = path.replaceAll(PathUtil.VAR_REGEXP, PathUtil.VAR_REPLACE);
        // (?i) 表示区分大小写
        return cleanPath.matches("(?i)" + path);
    }

    private String parsePath(String path) {
        path = PathUtil.fixPath(path);
        try {
            URI uri = new URI(path);
            return uri.getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
