package com.lx.mario.util;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/5/21 0021.
 */
public class ExceptionUtil {
    public static void makeRunTimeWhen(boolean flag, String message, Object ... args) {
        if (flag) {
            message = String.format(message, args);
            RuntimeException e = new RuntimeException(message);
            throw correctStackTrace(e);
        }
    }

    public static void makeRuntime(Throwable cause) {
        RuntimeException e = new RuntimeException(cause);
        throw correctStackTrace(e);
    }

    /**
     * 移除lang层堆栈信息
     * @param e
     * @return
     */
    private static RuntimeException correctStackTrace(RuntimeException e) {
        StackTraceElement[] s = e.getStackTrace();
        if (null != s && s.length > 0) {
            e.setStackTrace(Arrays.copyOfRange(s, 1, s.length));
        }
        return e;
    }
}
