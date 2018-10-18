package net.evecom.drmc.dcap.controller;

import net.evecom.drmc.dcap.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/view")
public class ViewController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/preview")
    public String preview(Long id) {
        String rs = userService.findOne(id);
        return rs+" and service-dcap-183 success ";
    }
}
