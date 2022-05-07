package hay.hay.articleManage.Dto;

import hay.hay.articleManage.entity.User;
import lombok.Data;

@Data
public class UserOutDto {

    private String id;
    private String name;
    private String sex;
    private String email;
    private String role;
    private int type;

    public UserOutDto (User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.type = user.getType();
        if (this.type == 1) {
            this.role = "管理员";
        } else if (this.type == 2){
            this.role = "普通用户";
        }
    }

    public UserOutDto () {}
}
