package net.evecom.etl.processor.transform;

import net.evecom.etl.processor.transform.entity.HandlerRequest;
import net.evecom.etl.processor.transform.entity.HandlerResult;

/**
 * @ClassName: IProcessor
 * @Description: 解析处理器接口
 * @author： zhengc
 * @date： 2018年10月12日
 */
public interface IProcessor {

    /**
     * 解析处理
     * @param chain
     * @return
     */
    public HandlerResult process(Chain chain);

    /**
     * 解析链路接口
     */
    interface Chain {
        /**
         * 获取请求对象
         * @return
         */
        HandlerRequest request();

        /**
         * 请求流转
         * @param request
         * @return
         */
        HandlerResult proceed(HandlerRequest request);
    }
}
