package hay.hay.articleManage.controller;

import com.google.common.collect.Lists;
import hay.hay.articleManage.Dto.ResponseBean;
import hay.hay.articleManage.Dto.UserInDto;
import hay.hay.articleManage.Dto.UserOutDto;
import hay.hay.articleManage.entity.User;
import hay.hay.articleManage.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin(value = "*", allowCredentials = "true")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	private  ResponseBean login(String email, String psw) throws UnsupportedEncodingException {
		User user = userService.findByEmail(email);
		if (user == null) {
			return new ResponseBean().code(ResponseBean.FAILED).success(false).data("找不到用户");
		}
		psw = new String(Base64Utils.decodeFromString(psw), "utf-8");
		String encryPsw = DigestUtils.md5Hex(psw);
		if (user.getPsw().equals(encryPsw)) {
			UserOutDto outDto = new UserOutDto(user);
			return new ResponseBean().code(200).success(true).data(outDto);
		} else {
			return new ResponseBean().code(ResponseBean.FAILED).success(false).data("登录失败");
		}
	}

	//修改个人信息接口
	@PostMapping("/CreateOrUpdate")
	private ResponseBean createOrUpdateUser(UserInDto inDto) throws UnsupportedEncodingException {
		ResponseBean responseBean = new ResponseBean();
		if (StringUtils.isEmpty(inDto.getId())) {
			User tempUser = userService.findByEmail(inDto.getEmail());
			if (tempUser != null) {
				return new ResponseBean().code(ResponseBean.FAILED).success(false).data("该邮箱已被注册");
			}
			return createUser(inDto);
		} else {
			return updateUser(inDto);
		}
	}

	ResponseBean createUser(UserInDto userInDto) throws UnsupportedEncodingException {
		User user = new User(userInDto);
		user.setPsw(DigestUtils.md5Hex( new String(Base64Utils.decodeFromString(userInDto.getPsw()), "utf-8")));
		userService.save(user);
		return new ResponseBean().code(200).success(true).data("用户添加成功");
	}

	ResponseBean updateUser(UserInDto userInDto) throws UnsupportedEncodingException {
		User oldUser = userService.findById(userInDto.getId());
		User user = new User(userInDto);
		if (oldUser == null) {
			return new ResponseBean().code(ResponseBean.FAILED).success(false).data("用户修改失败，找不到该用户");
		}
			if (!oldUser.getId().equals(user.getId())) {
				return new ResponseBean().code(ResponseBean.FAILED).success(false).data("用户修改失败，该邮箱已被使用");
			}
		String newPsw = new String(Base64Utils.decodeFromString(userInDto.getPsw()), "utf-8");
		if (!newPsw.equals(userInDto.getEmail())) {
			user.setPsw(DigestUtils.md5Hex(newPsw));
		} else {
			user.setPsw(oldUser.getPsw());
		}
		userService.update(user);
		return new ResponseBean().code(200).success(true).data("用户修改成功");
	}

	/**
	*管理员
	**/

	//管理员查询所有用户
	@GetMapping("/all")
	private ResponseBean findAll() {
		List<User> users = userService.findAll();
		List<UserOutDto> dtos = Lists.newArrayList();
		for (User user : users) {
			dtos.add(new UserOutDto(user));
		}
		return new ResponseBean().code(200).success(true).data(dtos);
	}

	//管理员删除用户
	@PostMapping("/delete")
	private ResponseBean deleteUser(String id) {
		User user = userService.findById(id);
		if (user != null) {
			userService.delete(user);
			return new ResponseBean().code(200).success(true).data("用户删除成功");
		} else {
			return new ResponseBean().code(200).success(true).data("找不到该用户");
		}
	}

}
