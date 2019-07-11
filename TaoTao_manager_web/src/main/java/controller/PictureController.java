package controller;

import com.alibaba.druid.support.json.JSONUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import utils.FtpUtil;
import utils.IDUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/15 0015 9:28
 * 4    图片上传
 */
@Controller
@RequestMapping("/pic")
public class PictureController {

    @Value("${FTP.ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP.PORT}")
    private Integer FTP_PORT;
    @Value("${FTP.USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP.PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP.BASEPATH}")
    private String FTP_BASEPATH;
    @Value("${IMAGE.BASE.URL}")
    private String IMAGE_BASE_URL;


    @RequestMapping("/upload")
    @ResponseBody
    public String pictureUpload(MultipartFile uploadFile) throws IOException {
        Map resultMap = new HashMap();
        try {
            // 生成新文件名
            // 取原始文件名
            String oldName = uploadFile.getOriginalFilename();
            // 生成新名字
            String newName = IDUtils.genImageName() + oldName.substring(oldName.lastIndexOf("."));
            // 图片上传
            String imagePath = new DateTime().toString("/yyyy/MM/dd");
            boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASEPATH,
                    imagePath, newName, uploadFile.getInputStream());
            if (!result) {
                resultMap.put("error", 1);
                resultMap.put("message", "文件上传失败");
                return JSONUtils.toJSONString(resultMap);
            }
            resultMap.put("error", 0);
            resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
            return JSONUtils.toJSONString(resultMap);
        } catch (IOException e) {
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传失败");
            return JSONUtils.toJSONString(resultMap);
        }
    }
}
