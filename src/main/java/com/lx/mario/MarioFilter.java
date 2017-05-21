package com.lx.mario;

import com.lx.mario.route.Route;
import com.lx.mario.route.RouteManager;
import com.lx.mario.route.RouteMatcher;
import com.lx.mario.util.PathUtil;
import com.lx.mario.util.ReflectUtil;
import com.lx.mario.wrapper.Request;
import com.lx.mario.wrapper.Response;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Mario mvc 核心控制器
 * Created by lx on 2017/5/21.
 */
public class MarioFilter implements Filter{

    private static final Logger LOGGER = Logger.getLogger(MarioFilter.class.getName());

    private RouteMatcher routeMatcher = new RouteMatcher(new ArrayList<Route>());

    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Mario mario = new Mario();
        if (!mario.isInit()) {
            String className = filterConfig.getInitParameter("bootstrap");
            Bootstrap bootstrap = this.getBootstrap(className);
            bootstrap.init(mario);
            RouteManager routeManager = mario.getRouteManager();
            if (null != routeManager) {
                routeMatcher.setRoutes(routeManager.getRoutes());
            }
            servletContext = filterConfig.getServletContext();
            mario.setInit(true);
        }
    }

    private Bootstrap getBootstrap(String className) {
        if (null != className) {
            try {
                Class<?> clazz = Class.forName(className);
                Bootstrap bootstrap = (Bootstrap) clazz.newInstance();
                return bootstrap;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("init bootstarp class error!");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 请求的uri
        String uri = PathUtil.getRelativePath(request);
        LOGGER.info("Request URI : " + uri);
        Route route = routeMatcher.findRoute(uri);
        if (null != route) {
            handle(request, response, route);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private void handle(HttpServletRequest request, HttpServletResponse response, Route route) {
        // 初始化上下文
        Request req = new Request(request);
        Response resp = new Response(response);
        MarioContext.initContext(servletContext, request, response);
        Object controller = route.getController();
        // 要执行的路由
        Method actionMethod = route.getAction();
        // 要执行的route方法
        executeMethod(controller, actionMethod, req, resp);
    }

    /**
     * 执行路由方法
     * @param controller
     * @param actionMethod
     * @param req
     * @param resp
     */
    private Object executeMethod(Object controller, Method actionMethod, Request req, Response resp) {
        int len = actionMethod.getParameterTypes().length;
        actionMethod.setAccessible(true);
        if (len > 0) {
            Object[] args = getArgs(req, resp, actionMethod.getParameterTypes());
            return ReflectUtil.invokeMethod(controller, actionMethod, args);
        } else {
            return ReflectUtil.invokeMethod(controller, actionMethod);
        }
    }

    /**
     * 获取方法内的参数
     * @param req
     * @param resp
     * @param parameterTypes
     * @return
     */
    private Object[] getArgs(Request req, Response resp, Class<?>[] params) {
        int len = params.length;
        Object[] args = new Object[len];
        for (int i = 0; i < len; i++) {
            Class<?> paramTypeClazz = params[i];
            if (paramTypeClazz.getName().equals(Request.class.getName())) {
                args[i] = req;
            }
            if (paramTypeClazz.getName().equals(Response.class.getName())) {
                args[i] = resp;
            }
        }
        return args;
    }

    @Override
    public void destroy() {

    }
}
