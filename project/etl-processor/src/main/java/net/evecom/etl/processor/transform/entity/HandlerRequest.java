package net.evecom.etl.processor.transform.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: HandlerRequest
 * @Description: 链路统一请求对象
 * @author： zhengc
 * @date： 2018年10月12日
 * @param <T> data对象类型
 */
@Builder(toBuilder=true)
@Getter
@Setter
@ToString
public class HandlerRequest<T> implements Serializable {

    /**
     * 批次号
     */
    private int batchNum;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 携带的数据
     */
    private T data;

    /**
     * 同步时间
     */
    private String syncDate;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 解析处理任务名
     */
    private int taskName;

    /**
     * 同步数据量
     */
    private int syncQty;

    /**
     * 产品文件名
     */
    private String fileName;

}
