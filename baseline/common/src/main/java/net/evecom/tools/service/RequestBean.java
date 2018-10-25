package net.evecom.tools.service;

public class RequestBean {
    private static final ThreadLocal<RequestBean> REQUEST_BEAN = new ThreadLocal<>();
    /** 平台 **/
    private String platform;
    /** 版本 **/
    private String version;
    /** 产品 **/
    private String data;
    /** token **/
    private String token;
    /** 开始时间 **/
    private Long stime;
    /** 当前请求用户sessionId **/
    private String sid;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getStime() {
        return stime;
    }

    public void setStime(Long stime) {
        this.stime = stime;
    }

    public String getSid() {
        return sid;
    }

    public void setSessionId(String sid) {
        this.sid = sid;
    }

    public static RequestBean getRequestBean() {
        return REQUEST_BEAN.get();
    }

    public static void setRequestBean(RequestBean request) {
        REQUEST_BEAN.set(request);
    }

    public static void removeRequestBean() {
        REQUEST_BEAN.remove();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
