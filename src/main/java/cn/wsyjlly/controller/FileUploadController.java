package cn.wsyjlly.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wsyjlly
 * @create 2019.06.13 - 17:23
 **/
@RestController
public class FileUploadController {
    private String fomartDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private final String UPLOAD_PATH = "/uploadFiles/";
    private final Integer SUCCESS = 1;
    private final Integer FAILURE = 0;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * 单文件上传
     * */
    @PostMapping("/upload")
    public ModelMap upload(MultipartFile uploadFile, HttpServletRequest request) {
        ModelMap map = new ModelMap();
        if (uploadFile == null){
            map.addAttribute("status",FAILURE);
            map.addAttribute("message","未选择文件");
            return map;
        }
        File folder = getRootPath();
        logger.debug("文件夹路径:"+folder.getAbsolutePath());
        String originalFilename = uploadFile.getOriginalFilename();
        logger.debug("文件原名："+originalFilename);
        String newName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        try {
            uploadFile.transferTo(new File(folder,newName));
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + UPLOAD_PATH + fomartDate+"/" + newName;
            logger.debug("文件访问路径："+filePath);
            map.addAttribute("filePath",filePath);
            map.addAttribute("status",SUCCESS);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.addAttribute("status",FAILURE);
        return map;
    }


    /*
     * 多文件上传
     * */
    @PostMapping("/uploads")
    public ModelMap uploads(MultipartFile[] uploadFiles, HttpServletRequest request) {
        ModelMap map = new ModelMap();
        logger.debug("文件个数："+uploadFiles.length);

        //String realPath = ResourceUtils.getURL("classpath:").getPath()+UPLOAD_PATH;
        File folder = getRootPath();
        HashMap<String, Map> fileListUploadStatus = new HashMap<>();
        for (MultipartFile file:uploadFiles){
            HashMap<String, Object> item = new HashMap<>();
            String originalFilename = file.getOriginalFilename();
            System.out.println("————————————————————————————————");

            logger.debug("————————————————————————————————");
            logger.debug("文件原名："+originalFilename);
            logger.debug("文件大小："+file.getSize());
            logger.debug("文件类型："+file.getContentType());
            String newName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
            try {
                file.transferTo(new File(folder,newName));
                String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + UPLOAD_PATH + fomartDate+"/" + newName;
                logger.debug("访问地址："+filePath);
                logger.debug("地址文件名："+newName);
                item.put("isUpload",true);
                item.put("url",filePath);
                fileListUploadStatus.put(originalFilename,item);
            } catch (IOException e) {
                item.put("isUpload",false);
                item.put("url","");
                fileListUploadStatus.put(originalFilename,item);
                e.printStackTrace();
            }
        }
        map.addAttribute("resultList",fileListUploadStatus);
        return map;
    }
    private File getRootPath(){
        File file = new File(System.getProperty("user.dir")+UPLOAD_PATH);
        if(!file.exists()){//如果文件夹不存在
            file.mkdirs();//创建文件夹
        }
        String rootPath = file.getAbsolutePath();
        File folder = new File(rootPath+'/' + fomartDate);
        if (!folder.isDirectory()){
            folder.mkdirs();
        }
        return folder;
    }
}
