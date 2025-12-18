package com.sqlclass.bms.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sqlclass.bms.utils.PathUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/update")
public class UpdateController {
    public static void main(String[] args) {
        try {
            System.out.println(new ClassPathResource("").getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 我的上传方法
     * @param req
     * @return
     */
    /**
     * 我的上传方法
     * @param req
     * @return
     */
    private String myUpdate(HttpServletRequest req) {
        String res = null;  // 返回网络路径
        try {
            String staticDir = PathUtils.getClassLoadRootPath() + "/src/main/resources/static/files/";
            // 如果结果目录不存在，则创建目录
            File resDirFile = new File(staticDir);
            if(!resDirFile.exists()) {
                boolean flag = resDirFile.mkdirs();
                if(!flag) throw new RuntimeException("创建结果目录失败");
            }
            
            // 使用Spring的标准multipart处理方式
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
            Iterator<String> fileNames = multipartRequest.getFileNames();
            if (fileNames.hasNext()) {
                String fileName = fileNames.next();
                MultipartFile file = multipartRequest.getFile(fileName);
                if (file != null && !file.isEmpty()) {
                    // 上传的文件
                    System.out.println("表单项的 name 属性值：" + fileName);
                    System.out.println("上传的文件名：" + file.getOriginalFilename());
                    // 加个时间戳防止重名
                    String newFileName = System.currentTimeMillis() + file.getOriginalFilename();
                    // 写文件
                    File destFile = new File(staticDir + "/" + newFileName);
                    file.transferTo(destFile);
                    // 返回值
                    res = "/files/" + newFileName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 上传图片
     * @param req
     * @return
     */
    @RequestMapping("/updateImg")
    @ResponseBody
    public Map<String,Object> updateImg(HttpServletRequest req){
        String resPath = myUpdate(req);

        Map<String,Object> res = new HashMap<>();
        res.put("code",0);
        res.put("data", resPath);

        return res;
    }

}