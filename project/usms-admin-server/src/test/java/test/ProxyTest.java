package test;

import test.proxy.Interceptor;
import test.proxy.LogInterceptor;

import java.util.ArrayList;
import java.util.List;

public class ProxyTest {
    public static void main(String[] args) {
        BuyHouse buyHouse = new BuyHouseImpl();
//        buyHouse.buyHosue();
        List<Interceptor> interceptorList =new ArrayList<>();
        interceptorList.add(new LogInterceptor());
        BuyHouseProxy buyHouseProxy = new BuyHouseProxy(buyHouse);
        buyHouseProxy.buyHosue();
    }
}
