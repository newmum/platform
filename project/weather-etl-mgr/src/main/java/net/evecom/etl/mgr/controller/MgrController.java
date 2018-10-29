package net.evecom.etl.mgr.controller;

import net.evecom.etl.mgr.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mgr")
public class MgrController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/preview")
    public String preview(Long id) {
        String rs = userService.findOne(id);
        return rs+" and service-dcap-183 success ";
    }
}
