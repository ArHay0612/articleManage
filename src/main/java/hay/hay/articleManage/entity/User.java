package hay.hay.articleManage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import hay.hay.articleManage.Dto.UserInDto;
import lombok.Getter;
import lombok.Setter;

@Entity // 注明此Java类是实体类
@Setter// 自动生成getter和setter
@Getter
@Table(name = "ts_user") // 数据库中表的名称
public class User extends BaseEntity<String> {

	private static final long serialVersionUID = 4275743746213746550L;

	@Column(name = "name")
	private String name;

	@Column(name = "sex")
	private String sex;

	@Column(name = "email")
	private String email;

	@Column(name = "psw")
	private String psw;

	@Column(name = "type")
	private int type;

	public User(){}
	public User(UserInDto userInDto){
		this.id = userInDto.getId();
		this.name = userInDto.getName();
		this.email = userInDto.getEmail();
		this.sex = userInDto.getSex();
		this.type = userInDto.getType();
	}
}