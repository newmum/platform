package net.evecom.rd.ie.baseline.etl.processor.transform.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: HandlerResult
 * @Description: 链路统一结果对象
 * @author： zhengc
 * @date： 2018年10月12日
 * @param <T> data对象类型
 */
@Builder
@ToString
@Getter
public class HandlerResult<T> implements Serializable {
    /**
     * 结果是否成功 true成功 false失败
     */
    private boolean success = true;

    /**
     * 编码 成功为0 失败为具体失败码
     */
    private int code;

    /**
     * 描述信息
     */
    private String msg;

    /**
     * 携带的数据
     */
    private T data;

    /**
     * 请求时间
     */
    private String reqDate;

    /**
     * 创建时间
     */
    private String createDate;

}
