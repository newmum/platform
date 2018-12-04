package net.evecom.rd.ie.baseline.etl.mgr.service.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name= "etl-processor",fallback = UserService.class)
public interface IUserService {

    @RequestMapping(value = "/user/findOne")
    public String findOne(Long id);
}
