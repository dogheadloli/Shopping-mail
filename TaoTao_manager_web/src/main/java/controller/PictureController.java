package controller;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.PictureService;

import java.io.IOException;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/15 0015 9:28
 * 4
 */
@Controller
@RequestMapping("/pic")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/upload")
    @ResponseBody
    public String pictureUpload(MultipartFile uploadFile) throws IOException {
        Map result = pictureService.uploadPicture(uploadFile);
        String json = JSONUtils.toJSONString(result);
        return json;
    }
}
