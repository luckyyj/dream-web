package cn.com.dream.user.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String userName;

    private String password;

    private String personName;

    private Integer isDelete;

}
