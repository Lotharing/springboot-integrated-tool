package top.lothar.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ApiModel(description = "user")
@Table(name = "sd_sys_user")
public class User {

    @ApiModelProperty(value = "主键id",hidden = true)
    @GeneratedValue
    @Id
    private int userId;

    @ApiModelProperty(value = "用户名称")
    @Column
    private String account;

    @ApiModelProperty(value = "用户密码")
    @Column
    private String password;

    @ApiModelProperty(value = "用户角色")
    @Column
    private String roleName;

    @ApiModelProperty(value = "员工ID")
    @Column
    private String employeeId;

    @ApiModelProperty(value = "更新时间")
    @Column
    private Date updateTime;

    @ApiModelProperty(value = "用户类型标识")
    @Column
    private Integer state;

}
