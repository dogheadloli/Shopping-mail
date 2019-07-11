package intercepter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import po.TbUser;
import service.UserService;
import utils.CookieUtils;
import utils.JsonUtils;
import utils.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/28 0028 20:29
 * 4    登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @Value("${SSO_URL}")
    private String SSO_URL;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 执行handler之先执行此方法
        // 返回true执行，返回false拦截
        // 从cookie取token
        String token = CookieUtils.getCookieValue(httpServletRequest, TOKEN_KEY);
        // 没有，跳转到登录,需要把当前请求的url作为参数传递给sso，sso登录成功后跳转回请求页面
        if (StringUtils.isBlank(token)) {
            String requestURL = httpServletRequest.getRequestURL().toString();
            httpServletResponse.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
            return false;
        }
        // 有，检查token是否过期
        TaotaoResult result = userService.getUserByToken(token);
        if (result.getStatus() != 200) {
            String requestURL = httpServletRequest.getRequestURL().toString();
            httpServletResponse.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
            return false;
        }
        TbUser user = (TbUser) result.getData();
        httpServletRequest.setAttribute("user", user);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // handler执行之后modelAndView返回之前，可以处理modelAndView
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // modelAndView返回之后，可以处理异常
    }
}
