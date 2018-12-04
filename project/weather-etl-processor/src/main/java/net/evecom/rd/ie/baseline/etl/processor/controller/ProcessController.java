package net.evecom.rd.ie.baseline.etl.processor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.evecom.rd.ie.baseline.etl.processor.transform.ProcessHandle;
import net.evecom.rd.ie.baseline.etl.processor.transform.entity.HandlerRequest;
import net.evecom.rd.ie.baseline.etl.processor.transform.entity.HandlerResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
@Api(tags = "解析处理模块")
public class ProcessController {

    @ApiOperation(value = "测试解析链路", notes = "测试解析链路")
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String findOne(Long id) {
        StringBuilder sb = new StringBuilder("service-uma-183 success ");
        ProcessHandle processHandle = new ProcessHandle();
        for (int i = 1; i <= 10; i++) {
            int qty = (int)(Math.random()*10);
            HandlerResult rs = processHandle.execute(HandlerRequest.builder()
                    .syncQty(qty).build());
            sb.append(qty+":"+rs.getMsg()+"<br>");
        }
        return sb.toString();
    }
}
