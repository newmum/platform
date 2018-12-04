package net.evecom.rd.ie.baseline.etl.processor.transform.handle;

import net.evecom.rd.ie.baseline.etl.processor.transform.IProcessor;
import net.evecom.rd.ie.baseline.etl.processor.transform.entity.HandlerRequest;
import net.evecom.rd.ie.baseline.etl.processor.transform.entity.HandlerResult;
import org.springframework.stereotype.Service;

@Service
public class AutoStationHandle implements IProcessor {

    @Override
    public HandlerResult process(Chain chain) {
        HandlerRequest handlerRequest = chain.request();
        if (handlerRequest.getSyncQty() > 2) {
            return chain.proceed(handlerRequest.toBuilder().remark("HandleA：量大于2继续===>").build());
        }
        return HandlerResult.builder().success(true).msg("HandleA：量小于2处理结束").build();
    }
}
