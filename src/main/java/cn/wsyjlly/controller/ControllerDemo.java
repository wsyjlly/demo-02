package cn.wsyjlly.controller;

import cn.wsyjlly.service.ServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wsyjlly
 * @create 2019.06.10 - 19:00
 **/
@RestController
public class ControllerDemo {
    @Autowired
    private ServiceDemo serviceDemo;

    @RequestMapping("/admin/demo")
    public String admin(){
        System.out.println("demo");
        return "admin - demo";
    }
    @RequestMapping("/user/demo")
    public String user(){
        System.out.println("demo");
        return "user - demo";
    }
    @RequestMapping("/db/demo")
    public String db(){
        System.out.println("demo");
        return "db - demo";
    }
    @RequestMapping("/demo")
    public String any(){
        System.out.println("demo");
        System.out.println(new BCryptPasswordEncoder(10).encode("234"));
        System.out.println(serviceDemo.admin());
        System.out.println(serviceDemo.dba());
        System.out.println(serviceDemo.user());
        return "demo";
    }
    
}
