package net.evecom.etl.processor.transform;

import net.evecom.etl.processor.transform.chain.BaseChain;
import net.evecom.etl.processor.transform.entity.HandlerRequest;
import net.evecom.etl.processor.transform.entity.HandlerResult;

import java.util.ArrayList;

/**
 * @ClassName: ProcessHandle
 * @Description: 链路初始化
 * @author： zhengc
 * @date： 2018年10月12日
 */
public class ProcessHandle {

    public ProcessHandle(){
        handlerList = new ArrayList<IProcessor>();
    }

    private ArrayList<IProcessor> handlerList;

    /**
     * 添加处理类
     * @param processHandler
     */
    public void addHandler(IProcessor processHandler) {
        handlerList.add(processHandler);
    }

    /**
     * 执行流转
     * @param handlerRequest
     * @return
     */
    public HandlerResult execute(HandlerRequest handlerRequest) {
        ArrayList<IProcessor> handlers = new ArrayList<IProcessor>();
        handlers.addAll(handlerList);
        handlers.add(getProcessor("net.evecom.etl.processor.transform.handle.TextHandle"));
        handlers.add(getProcessor("net.evecom.etl.processor.transform.handle.TyphoonHandle"));
        handlers.add(getProcessor("net.evecom.etl.processor.transform.handle.WordHandle"));
        BaseChain baseChain = new BaseChain(handlers, handlerRequest, 0);
        return baseChain.proceed(handlerRequest);
    }

    public IProcessor getProcessor(String classpath){
        if(classpath!=null){
            try {
                return (IProcessor) Class.forName(classpath).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
