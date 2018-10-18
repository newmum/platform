package test;

import org.springframework.cglib.proxy.Proxy;
import test.proxy.Interceptor;
import test.proxy.LogInterceptor;

import java.util.ArrayList;
import java.util.List;

public class DynamicProxyTest {
    public static void main(String[] args) {
        BuyHouse buyHouse = new BuyHouseImpl();
        BuyHouse proxyBuyHouse = (BuyHouse) DynamicProxyHandler.bind(buyHouse);
        proxyBuyHouse.buyHosue();
    }
}
