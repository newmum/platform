package net.evecom.rd.ie.baseline.etl.processor.transform.handle;

import net.evecom.rd.ie.baseline.etl.processor.transform.IProcessor;
import net.evecom.rd.ie.baseline.etl.processor.transform.entity.HandlerRequest;
import net.evecom.rd.ie.baseline.etl.processor.transform.entity.HandlerResult;
import org.springframework.stereotype.Service;

@Service
public class TyphoonHandle implements IProcessor {

    @Override
    public HandlerResult process(Chain chain) {
        HandlerRequest handlerRequest = chain.request();
        if (handlerRequest.getSyncQty() > 8) {
            return HandlerResult.builder()
                    .success(false)
                    .msg(handlerRequest.getRemark()+"HandleEnd：量大于8不处理").build();
        }
        return HandlerResult.builder()
                .success(true)
                .msg(handlerRequest.getRemark()+"HandleEnd：量小于8处理结束").build();
    }
}
