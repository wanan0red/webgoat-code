package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.metadata.CallParameterMetaData;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Base64;

@RestController
@AssignmentHints({
        "path-traversal-profile-retrieve.hint1",
        "path-traversal-profile-retrieve.hint2",
        "path-traversal-profile-retrieve.hint3",
        "path-traversal-profile-retrieve.hint4",
        "path-traversal-profile-retrieve.hint5",
        "path-traversal-profile-retrieve.hint6"})
@Slf4j
public class ProfileUploadRetrieval extends AssignmentEndpoint {
    private final File catPicturesDirectory;

    public ProfileUploadRetrieval(@Value("${webgoat.server.directory}")String webGoatHomeDirectory) {
        this.catPicturesDirectory = new File(webGoatHomeDirectory,"/PathTraversal/"+"/cats");
        this.catPicturesDirectory.mkdirs();
    }
    @PostConstruct
    public void initAssignment(){
//        这里是在启动项目时执行的 目的主要是获取图片信息 与初始化flag文件
        for (int i = 1;i <= 10;i++){
            try (InputStream is = new ClassPathResource("lessons/path_traversal/images/cats/" + i + ".jpg").getInputStream()){
//                首先获取所有图片的输入流
                FileCopyUtils.copy(is,new FileOutputStream(new File(catPicturesDirectory,i+".jpg")));
//                复制文件进去
            }catch (Exception e){
                log.error("Unable to copy pictures"+ e.getMessage());
            }
        }
        var secretDirectory = this.catPicturesDirectory.getParentFile().getParentFile();
//        将目录向上移动两级
        try {
            Files.writeString(secretDirectory.toPath().resolve("path-traversal-secret.jpg"),"You found it submit the SHA-512 hash of your username as answer");
//            将字符写入文件

        }catch (IOException e){
            log.error("Unable to write secret in: {}",secretDirectory,e);
        }
    }
    @PostMapping("/PathTraversal/random")
    @ResponseBody
    public AttackResult execute(@RequestParam(value = "secret",required = false)String secret){
//        对比secret是否相同
        if (Sha512DigestUtils.shaHex(getWebSession().getUserName()).equals(secret)){
            return success(this).build();
        }
        return failed(this).build();
    }
    @GetMapping("/PathTraversal/random-picture")
    @ResponseBody
    public ResponseEntity<?> getProfilePicture(HttpServletRequest request){
        var queryParams = request.getQueryString();
//        这句话是重点 其中 getQueryString 是获取url中的查询字符串 并且不会进行url 解码 不会
        if (queryParams != null && (queryParams.contains("..") || queryParams.contains("/"))){
//            这里进行了严格匹配
            return ResponseEntity.badRequest().body("Illegal characters are not allowed in the query params");
        }
        try {
            var id = request.getParameter("id");
//            这里呢获取了这个id参数 其中呢会进行url解码
            var catPicture = new File(catPicturesDirectory,(id == null ? RandomUtils.nextInt(1,11) : id) + ".jpg");
            if (catPicture.getName().toLowerCase().contains("path-traversal-secret.jpg")){
//                如果包含 path-traversal-secret.jpg 就将内容返回回去
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                        .body(FileCopyUtils.copyToByteArray(catPicture));
            }
            if (catPicture.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                        .location(new URI("/PathTraversal/random-picture?id="+catPicture.getName()))
                        .body(Base64.getEncoder().encode(FileCopyUtils.copyToByteArray(catPicture)));

            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .location(new URI("/PathTraversal/random-picture?id="+catPicture.getName()))
                    .body(StringUtils.arrayToCommaDelimitedString(catPicture.getParentFile().listFiles()).getBytes());

        }catch (IOException | URISyntaxException e){
            log.error("Image not found",e);
        }
        return ResponseEntity.badRequest().build();
    }
}
