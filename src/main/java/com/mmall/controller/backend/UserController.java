package com.mmall.controller.backend;

import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wang on 2018/3/18.
 */
@Controller
@RequestMapping(value = "/manage/user")
public class UserController {

    @Autowired
    private IUserService iUserService;


}
