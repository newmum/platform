package net.evecom.etl.processor.transform.chain;

import net.evecom.etl.processor.transform.IProcessor;
import net.evecom.etl.processor.transform.entity.HandlerRequest;
import net.evecom.etl.processor.transform.entity.HandlerResult;

import java.util.List;

/**
 * @ClassName: BaseChain
 * @Description: 链路流程控制基类
 * @author： zhengc
 * @date： 2018年10月12日
 */
public class BaseChain implements IProcessor.Chain {

    public HandlerRequest handlerRequest;
    public List<IProcessor> processChain;
    /**
     * 处理级别
     */
    public int level;

    public BaseChain(List<IProcessor> processChain, HandlerRequest handlerRequest, int level){
        this.processChain = processChain;
        this.handlerRequest = handlerRequest;
        this.level = level;
    }

    @Override
    public HandlerRequest request() {
        return handlerRequest;
    }

    @Override
    public HandlerResult proceed(HandlerRequest request) {
        HandlerResult handlerResult = null;
        if (processChain.size() > level) {
            BaseChain baseChain = new BaseChain(processChain, request, level + 1);
            IProcessor processHandler = processChain.get(level);
            handlerResult = processHandler.process(baseChain);
        }
        return handlerResult;
    }
}
