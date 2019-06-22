package cn.wsyjlly.service.implementation;

import cn.wsyjlly.entity.Role;
import cn.wsyjlly.entity.User;
import cn.wsyjlly.entity.UserRole;
import cn.wsyjlly.mapper.RoleMapper;
import cn.wsyjlly.mapper.UserMapper;
import cn.wsyjlly.mapper.UserRoleMapper;
import cn.wsyjlly.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wsyjlly
 * @create 2019.06.16 - 17:02
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new User().setUsername(username));
        if (user == null){
            throw new UsernameNotFoundException("账户不存在!");
        }
        List<UserRole> userRoles= userRoleMapper.selectList(new EntityWrapper<UserRole>().eq("uid", user.getId()));
        List<Role> roles = new ArrayList<>();
        for (UserRole item:userRoles){
            Role role = roleMapper.selectById(item.getRid());
            roles.add(role);
            logger.info(username + " 拥有权限"+role.getName());
        }
        user.setRoles(roles);
        return user;
    }

}
