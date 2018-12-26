package com.evecom.rd.ie.baseline.cloud.gate.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@RefreshScope
public class OauthFilter extends ZuulFilter {

    private static final Log log = LogFactory.getLog(OauthFilter.class);

    @Value("${token.enabled}")
    private boolean isToken;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return RIBBON_ROUTING_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getParameter("token");
        if(isToken){
            if (token!=null && !token.equals("")) {
                context.setSendZuulResponse(true); //对请求进行路由
                context.setResponseStatusCode(200);
                context.set("isSuccess", true);
            }else{
                context.setSendZuulResponse(false); //不对其进行路由
                context.setResponseStatusCode(400);
                String host = request.getRemoteHost();
                context.setResponseBody(context.get(SERVICE_ID_KEY) + ":token is empty");
                context.set("isSuccess", false);
            }
        }else{
            context.setSendZuulResponse(true); //对请求进行路由
            context.setResponseStatusCode(200);
            context.set("isSuccess", true);
        }
        return null;
    }

    protected InputStream getRequestBody(HttpServletRequest request) {
        InputStream requestEntity = null;
        try {
            requestEntity = (InputStream) RequestContext.getCurrentContext()
                    .get(REQUEST_ENTITY_KEY);
            if (requestEntity == null) {
                requestEntity = request.getInputStream();
            }
        }
        catch (IOException ex) {
            log.error("Error during getRequestBody", ex);
        }
        return requestEntity;
    }
}
