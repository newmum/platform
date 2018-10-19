package net.evecom.etl.processor.transform.handle;

import net.evecom.etl.processor.transform.IProcessor;
import net.evecom.etl.processor.transform.entity.HandlerRequest;
import net.evecom.etl.processor.transform.entity.HandlerResult;
import org.springframework.stereotype.Service;

@Service
public class TextHandle implements IProcessor {

    @Override
    public HandlerResult process(IProcessor.Chain chain) {
        HandlerRequest handlerRequest = chain.request();
        if (handlerRequest.getSyncQty() > 2) {
            return chain.proceed(handlerRequest.toBuilder().remark("HandleA：量大于2继续===>").build());
        }
        return HandlerResult.builder().success(true).msg("HandleA：量小于2处理结束").build();
    }
}
