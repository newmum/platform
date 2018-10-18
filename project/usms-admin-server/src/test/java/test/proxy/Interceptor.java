package test.proxy;

import java.lang.reflect.InvocationTargetException;

public interface Interceptor {
    public Object preHandle(Invocation invocation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
    public void afterCompletion();
}
