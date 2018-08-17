package service.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import service.PictureService;
import utils.FtpUtil;
import utils.IDUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/14 0014 21:40
 * 4
 */
@Service
public class PictureServiceImpl implements PictureService {
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

    @Override
    public Map uploadPicture(MultipartFile uploadFile) {
        Map resultMap = new HashMap();
        try {
            //生成新文件名
            //取原始文件名
            String oldName = uploadFile.getOriginalFilename();
            //生成新名字
            String newName = IDUtils.genImageName() + oldName.substring(oldName.lastIndexOf("."));
            //图片上传
            String imagePath = new DateTime().toString("/yyyy/MM/dd");
            boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASEPATH,
                    imagePath, newName, uploadFile.getInputStream());
            if (!result) {
                resultMap.put("error", 1);
                resultMap.put("message", "文件上传失败");
                return resultMap;
            }
            resultMap.put("error", 0);
            resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
            return resultMap;
        } catch (IOException e) {
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传失败");
            return resultMap;
        }
    }
}
