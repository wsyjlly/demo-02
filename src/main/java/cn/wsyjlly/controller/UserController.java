package cn.wsyjlly.controller;

import cn.wsyjlly.entity.User;
import cn.wsyjlly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wsyjlly
 * @create 2019.06.16 - 17:42
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(10);

    @PostMapping("/register")
    public ModelMap insertUser(String name,String password){
        userService.insertUser(new User()
                .setUsername(name)
                .setPassword(bpe.encode(password))
                .setEnable(true)
                .setLocked(false));
        ModelMap map = new ModelMap();
        map.addAttribute("status","OK");
        return map;
    }
}
