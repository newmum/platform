package net.evecom.rd.ie.baseline.tools.service;

import java.io.Serializable;

/**
 * @ClassName: Params
 * @Description: 统一入参对象
 * @author： zhengc
 * @date： 2009年10月14日
 */
public class Params<T> implements Serializable {

    /**
     * 入参携带数据
     */
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
