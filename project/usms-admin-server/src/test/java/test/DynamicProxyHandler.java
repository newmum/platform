package test;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import test.proxy.Interceptor;
import test.proxy.Invocation;
import test.proxy.LogInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DynamicProxyHandler implements InvocationHandler {

    private Object object;
    private List<Interceptor> interceptorList;
    private Interceptor interceptor;

    public DynamicProxyHandler(Object object,List<Interceptor> interceptorList,Interceptor interceptor) {
        this.object = object;
        this.interceptor= interceptor;
        this.interceptorList = interceptorList;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        Invocation invocation = new Invocation(o, method, objects);
        return interceptor.preHandle(invocation);

//        for(Interceptor interceptor : interceptorList){
//            interceptor.preHandle();
//        }
//        Object result = method.invoke(object, objects);
//        for(Interceptor interceptor : interceptorList){
//            interceptor.afterCompletion();
//        }
//        return result;
    }

    public static Object bind(BuyHouse buyHouse){
        List<Interceptor> interceptorList =new ArrayList<>();
        interceptorList.add(new LogInterceptor());
        buyHouse = (BuyHouse) Proxy.newProxyInstance(BuyHouse.class.getClassLoader(),
                buyHouse.getClass().getInterfaces(),
                new DynamicProxyHandler(buyHouse, interceptorList));
        return buyHouse;
    }
}
