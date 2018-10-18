package test.proxy;

import java.lang.reflect.InvocationTargetException;

public class LogInterceptor implements Interceptor {
    @Override
    public Object preHandle(Invocation invocation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        System.out.println("日志记录开始");
        return invocation.process();
    }

    @Override
    public void afterCompletion() {
        System.out.println("日志记录结束");
    }
}
