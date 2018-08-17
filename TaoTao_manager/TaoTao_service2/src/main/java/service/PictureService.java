package service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/14 0014 21:38
 * 4
 */
public interface PictureService {
    public Map uploadPicture(MultipartFile uploadFile) throws IOException;
}
