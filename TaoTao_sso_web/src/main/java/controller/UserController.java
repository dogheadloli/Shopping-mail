package controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import po.TbUser;
import service.UserService;
import utils.CookieUtils;
import utils.JsonUtils;
import utils.TaotaoResult;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/17 0017 21:24
 * 4    用户登录相关
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;

	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkUserData(@PathVariable String param, @PathVariable Integer type) {
		return userService.checkData(param, type);
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser user) {
		TaotaoResult result = userService.register(user);
		return result;
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username, String password,
	                          HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = userService.longin(username, password);
		if (result.getStatus() == 200) {
			//把token写入cookie
			CookieUtils.setCookie(request, response, TOKEN_KEY, result.getData().toString());
		}
		return result;
	}

	@RequestMapping(value = "/user/token/{token}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	//指定返回响应数据的content-type
	public String getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = userService.getUserByToken(token);
		//是否为jsonp请求
		if (StringUtils.isNotBlank(callback)) {
			return callback + "(" + JsonUtils.objectToJson(result) + ");";
		}
		return JsonUtils.objectToJson(result);
	}

	@RequestMapping("/user/logout/{token}")
	@ResponseBody
	public String logout(@PathVariable String token, String callback,
	                     HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = userService.logout(token);
		//从cookie删除
		if (result.getStatus() == 200) {
			CookieUtils.deleteCookie(request, response, TOKEN_KEY);
		}
		//是否为jsonp请求
		if (StringUtils.isNotBlank(callback)) {
			return callback + "(" + JsonUtils.objectToJson(result) + ");";
		}
		return JsonUtils.objectToJson(result);
	}
}


