package top.lothar.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements RowMapper<User> {


    private int userId;

    private String account;

    private String password;

    private String roleName;

    private String employeeId;

    private Date updateTime;

    private Integer state;

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setAccount(resultSet.getString("account"));
        user.setPassword(resultSet.getString("password"));
        user.setRoleName(resultSet.getString("role_name"));
        user.setEmployeeId(resultSet.getString("employee_id"));
        user.setUpdateTime(resultSet.getDate("update_time"));
        user.setState(resultSet.getInt("state"));
        return user;
    }
}
