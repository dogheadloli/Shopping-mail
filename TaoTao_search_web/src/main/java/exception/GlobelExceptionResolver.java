package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/8 0008 20:38
 * 4
 */
public class GlobelExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobelExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        logger.info("进入全局异常处理器。。。");
        logger.debug("测试handler类型" + o.getClass());
        //控制台打印异常
        e.printStackTrace();
        //向日志文件中写入日志文件
        logger.error("系统发生异常", e);
        //发邮件,jmail
        //发短信
        //展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "您的电脑有问题");
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
