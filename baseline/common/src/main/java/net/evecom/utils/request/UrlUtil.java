package net.evecom.utils.request;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: UrlUtil
 * @Description: URL操作组件
 * @author： zhengc
 * @date： 2017年4月9日
 */
public class UrlUtil {
    /** 默认字符集（UTF-8） */
    public static final String DEFAULT_ENCODING			= "UTF-8";
    /** URL scheme 指示符 */
    public static final String URL_SECHEME_SUFFIX		= "://";
    /** URL 目录分隔符 */
    public static final String URL_PATH_SEPARATOR		= "/";
    /** URL 端口分隔符 */
    public static final String URL_PORT_SEPARATOR		= ":";
    /** URL 参数标识符 */
    public static final String URL_PARAM_FLAG			= "?";
    /** URL 参数分隔符 */
    public static final String URL_PARAM_SEPARATOR		= "&";
    /** HTTP URL 标识 */
    public static final String HTTP_SCHEME				= "http";
    /** HTTPS URL 标识 */
    public static final String HTTPS_SCHEME				= "https";

    /** 添加 URL 地址参数 */
    public static final String addUrlParams(String url, Object ... params) {
        int index = url.indexOf('?');
        char sep1 = (index == -1) ? '?' : '&';
        StringBuilder sb = new StringBuilder(url);
        for(int i = 0; i < params.length; i += 2) {
            String key = urlEncode(params[i].toString());
            sb.append(i == 0 ? sep1 : '&').append(key).append('=');
            if(i < params.length - 1) {
                String val = urlEncode(params[i + 1].toString());
                sb.append(val);
            }
        }
        return sb.toString();
    }

    /** 添加 URL 地址参数 */
    public static final String addUrlParams(String url, Map<String, String> map) {
        if(map==null || map.size()== 0) return url;
        int i			= 0;
        Object[] params	= new Object[map.size() * 2];
        for(Map.Entry<String, String> entry : map.entrySet()) {
            String key	= entry.getKey();
            String val	= entry.getValue();
            params[i++]	= key == null ? "" : key;
            params[i++]	= val == null ? "" : val;
        }
        return addUrlParams(url, params);
    }

    /** 删除 URL 地址参数 */
    public static final String deleteUrlParams(String url, String ... names) {
        String baseUrl = truncateUrlParams(url);
        Map<String, String> params = getUrlParamMap(url);
        for(String name : names)
            params.remove(name);
        return addUrlParams(baseUrl, params);
    }

    /** 获取 URL 地址参数 */
    public static final Map<String, String> getUrlParamMap(String url) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        int index = url.indexOf('?');
        if(index != -1) {
            String str = url.substring(index + 1);
            String[] params = str.split("\\&");
            for(String param : params) {
                String[] pair	= param.split("\\=", 2);
                String name		= urlDecode(pair[0]);
                String value	= pair.length == 2 ? urlDecode(pair[1]) : "";
                if(!name.isEmpty())
                    map.put(name, value);
            }
        }
        return map;
    }

    /** 获取 URL 地址的指定参数 */
    public static final String getUrlParam(String url, String name)
    {
        return getUrlParamMap(url).get(name);
    }

    /** 编码 URL 地址参数 */
    public final static String encodeUrlParams(String url) {
        String baseUrl = truncateUrlParams(url);
        if(baseUrl.length() < url.length()) {
            Map<String, String> params = getUrlParamMap(url);
            String[] items = new String[params.size() * 2];
            int i = 0;
            for(Map.Entry<String, String> e : params.entrySet()) {
                items[i++] = urlEncode(e.getKey());
                items[i++] = urlEncode(e.getValue());
            }
            url = addUrlParams(baseUrl, (Object[])items);
        }
        return url;
    }
    /** 获取 URL 地址的非参数段 */
    public static final String truncateUrlParams(String url) {
        int p = url.indexOf(URL_PARAM_FLAG);
        if(p != -1)
            url = url.substring(0, p);
        return url;
    }

    /** URL编码 （使用默认字符集） */
    public final static String urlEncode(String url)
    {
        return urlEncode(url, null);
    }
    /** URL编码 （使用指定字符集） */
    public final static String urlEncode(String url, String charset) {
        try {
            return URLEncoder.encode(url, safeCharset(charset));
        }
        catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /** URL解码 （使用默认字符集） */
    public final static String urlDecode(String url)
    {
        return urlDecode(url, null);
    }

    /** URL解码 （使用指定字符集） */
    public final static String urlDecode(String url, String enc) {
        try {
            return URLDecoder.decode(url, safeCharset(enc));
        }
        catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存默认字符集
     * @param charset
     * @return
     */
    private final static String safeCharset(String charset) {
        if(StringUtils.isEmpty(charset))
            charset = DEFAULT_ENCODING;
        return charset;
    }
}
