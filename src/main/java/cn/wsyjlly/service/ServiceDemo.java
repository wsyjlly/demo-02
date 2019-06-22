package cn.wsyjlly.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author wsyjlly
 * @create 2019.06.16 - 16:02
 **/
@Service
public class ServiceDemo {
    @Secured("ROLE_ADMIN")
    public String admin(){
        return "service ... admin";
    }
    @PreAuthorize("hasRole('ADMIN') and hasRole('DBA')")
    public String dba(){
        return "service ... dba";
    }
    @PreAuthorize("hasAnyRole('ADMIN','DBA','USER')")
    public String user(){
        return "service ... user";
    }
}
