package top.lothar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int userId;
    private String account;
    private String password;
    private String roleName;
    private String employeeId;
    private Date updateTime;
    private Integer state;

}
