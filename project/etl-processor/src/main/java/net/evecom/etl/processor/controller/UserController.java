package net.evecom.etl.processor.controller;

import net.evecom.etl.processor.transform.ProcessHandle;
import net.evecom.etl.processor.transform.entity.HandlerRequest;
import net.evecom.etl.processor.transform.entity.HandlerResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${test.name}")
    private String name;

    @RequestMapping(value = "/findOne")
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
