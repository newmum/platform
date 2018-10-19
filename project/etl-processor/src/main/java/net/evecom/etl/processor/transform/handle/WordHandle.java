package net.evecom.etl.processor.transform.handle;

import net.evecom.etl.processor.transform.IProcessor;
import net.evecom.etl.processor.transform.entity.HandlerRequest;
import net.evecom.etl.processor.transform.entity.HandlerResult;
import org.springframework.stereotype.Service;

@Service
public class WordHandle implements IProcessor {

    @Override
    public HandlerResult process(Chain chain) {
        HandlerRequest handlerRequest = chain.request();
        if (handlerRequest.getSyncQty() > 5) {
            return chain.proceed(handlerRequest.toBuilder()
                    .remark(handlerRequest.getRemark()+"HandleB：量大于5继续===>").build());
        }
        return HandlerResult.builder()
                .success(true)
                .msg(handlerRequest.getRemark()+"HandleB：量小于5处理结束").build();
    }
}
