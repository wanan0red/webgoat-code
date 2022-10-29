package com.wanan.webgoat.webwolf;




import com.wanan.webgoat.webwolf.user.WebGoatUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.http.MediaType.ALL_VALUE;

@Controller
//代表控制类 控制层响应层
@Slf4j
public class FileServer {

    @Value("${webwolf.fileserver.location}")
    private String fileLocation;
    @Value("${server.address}")
    private String server;
    @Value("${server.port}")
    private int port;

    @RequestMapping(path= "/file-server-location",consumes = ALL_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
//   consumes 指定处理请求的提交内容类型(Content-Type) 例如application/json text/html
//    produces 指定返回的内容类型,仅当request请求头中Accept类型中包含该指定类型才返回
    @ResponseBody
    public String getFileLocation(){
        return fileLocation;
    }
    @PostMapping(value = "/fileupload")
    public ModelAndView importFile(@RequestParam("file")MultipartFile myFile) throws IOException{
//        MultipartFile 文件上传工具类
        var user = (WebGoatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        用于获取当前对象
        var destinationDir = new File(fileLocation,user.getUsername());
//        新建文件名和文件位置
        destinationDir.mkdirs();
//        mkdirs可以创建多级目录
        myFile.transferTo(new File(destinationDir,myFile.getOriginalFilename()));
        log.debug("File save to {}",new File(destinationDir,myFile.getOriginalFilename()));

        return new ModelAndView(
                new RedirectView("files",true),
                new ModelMap().addAttribute("uploadSuccess","File uploaded successful")

        );
    }
    @AllArgsConstructor
//    生成有参构造
    @Getter
//    生成get方法
    private class UploadedFile{
        private final String name;
        private final String size;
        private final String link;
    }
    @GetMapping(value = "/files")
    public ModelAndView getFiles(HttpServletRequest request){
        WebGoatUser user = (WebGoatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        获取当前用户
        String username = user.getUsername();
        File destinationDir = new File(fileLocation,username);

        ModelAndView modelAndView = new ModelAndView();
//        新建视图层
        modelAndView.setViewName("files");
//        添加视图名称
        File changeIndicatorFile = new File(destinationDir,user.getUsername()+"_changed");
        if (changeIndicatorFile.exists()){
//            如果文件已经存在的话 就添加请求上传成功
            modelAndView.addObject("uploadSuccess",request.getParameter("uploadSuccess"));

        }
        changeIndicatorFile.delete();
//        然后把文件删除
        var uploadedFiles = new ArrayList<>();
        File[] files = destinationDir.listFiles(File::isFile);
        if (files != null){
            for (File file : files){
                String size = FileUtils.byteCountToDisplaySize(file.length());
                String link = String.format("files/%s/%s",username,file.getName());
                uploadedFiles.add(new UploadedFile(file.getName(),size,link));
            }
//            列出文件名并添加进去
        }
        modelAndView.addObject("files",uploadedFiles);
        modelAndView.addObject("webwolf_url","http://"+server+":"+port);
        return modelAndView;
    }


}
