package net.evecom.drmc.dcap.service.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name= "usms-admin-server",fallback = UserService.class)
public interface IUserService {

    @RequestMapping(value = "/user/findOne")
    public String findOne(Long id);
}
