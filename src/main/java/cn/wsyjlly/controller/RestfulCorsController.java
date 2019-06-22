package cn.wsyjlly.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wsyjlly
 * @create 2019.06.10 - 19:00
 * GET（SELECT）：从服务器取出资源（一项或多项）。
 * POST（CREATE）：在服务器新建一个资源。
 * PUT（UPDATE）：在服务器更新资源（客户端提供改变后的完整资源）。
 * PATCH（UPDATE）：在服务器更新资源（客户端提供改变的属性）。
 * DELETE（DELETE）：从服务器删除资源。
 **/
@RestController
@RequestMapping("/cors")
public class RestfulCorsController {
    @RequestMapping("/")
    @CrossOrigin(value = "http://localhost:5000",allowedHeaders = "*",maxAge = 1800)
    public Map<String,String> itemOperator(@RequestBody ModelMap params){
        Map<String,String> map = new HashMap<>();
        map.put("name", (String) params.get("name"));
        System.out.println(params);
        return map;
    }

    @PostMapping("/add")
    @CrossOrigin(value = "http://localhost:5000",allowedHeaders = "*",maxAge = 1800)
    public Map<String,String> addItem(@RequestBody ModelMap params){
        Map<String,String> map = new HashMap<>();
        map.put("name", (String) params.get("name"));
        System.out.println(params);
        return map;
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id){
        System.out.println(id);
        return String.valueOf(id);
    }

    @PutMapping("/{id}")
    public String updateItem(@PathVariable Long id){
        System.out.println(id);
        return String.valueOf(id);
    }

    @PatchMapping("/patch")
    public String updateItem(@RequestBody ModelMap entity){
        System.out.println(entity);
        return "STATUS";
    }

    @GetMapping("/{id}")
    @CrossOrigin(value = "*",allowedHeaders = "*",maxAge = 1800)
    public String getItem(@RequestParam(value = "id", required = false, defaultValue = "1601141019") @PathVariable Long id){
        System.out.println(id);
        return String.valueOf(id);
    }




}
