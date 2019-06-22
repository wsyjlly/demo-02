package cn.wsyjlly.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;



/**
 * @author wsyjlly
 * @create 2019.06.16 - 16:49
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
@TableName(value="role")
public class Role implements Serializable {
    @TableId(value="id" , type= IdType.AUTO)
    private Integer id;
    private String name;
    private String name_des;

}
