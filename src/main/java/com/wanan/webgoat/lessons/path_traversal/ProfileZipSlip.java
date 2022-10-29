package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"path-traversal-zip-slip.hint1", "path-traversal-zip-slip.hint2", "path-traversal-zip-slip.hint3", "path-traversal-zip-slip.hint4"})
public class ProfileZipSlip extends ProfileUploadBase {

    public ProfileZipSlip(@Value("${webgoat.server.directory}") String webGoatHomeDirectory, WebSession webSession) {
        super(webGoatHomeDirectory, webSession);
    }

    @PostMapping(value = "/PathTraversal/zip-slip", consumes = ALL_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult uploadFileHandler(@RequestParam("uploadedFileZipSlip") MultipartFile file) {
        if (!file.getOriginalFilename().toLowerCase().endsWith(".zip")) {
//            如果文件名不是以.zip结尾
            return failed(this).feedback("path-traversal-zip-slip.no-zip").build();
        } else {
            return processZipUpload(file);
        }
    }
    @SneakyThrows
    private AttackResult processZipUpload(MultipartFile file) {
        var tmpZipDirectory = Files.createTempDirectory(getWebSession().getUserName());
//        获取文件名并创建一个临时目录
        var uploadDirectory = new File(getWebGoatHomeDirectory(), "/PathTraversal/" + getWebSession().getUserName());
//        对目录进行拼接
        var currentImage = getProfilePictureAsBase64();
//        获取下当前图片
        Files.createDirectories(uploadDirectory.toPath());
//        创建目录
        try {
            var uploadedZipFile = tmpZipDirectory.resolve(file.getOriginalFilename());
//            将临时路径添加进去
            FileCopyUtils.copy(file.getBytes(), uploadedZipFile.toFile());
//            将上传文件 存放进去
            ZipFile zip = new ZipFile(uploadedZipFile.toFile());
//            打开一个zip文件进行读取
            Enumeration<? extends ZipEntry> entries = zip.entries();
//            返回zip文件条目的枚举
            while (entries.hasMoreElements()) {
                ZipEntry e = entries.nextElement();
//                选择下一个
                File f = new File(uploadedZipFile.toFile(), e.getName());
//                新建一个文件实例
                InputStream is = zip.getInputStream(e);
//                将文件读入流
                Files.copy(is, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                将文件复制进去 如果文件存在就清除
            }
            return isSolved(currentImage, getProfilePictureAsBase64());
        }catch (IOException e){
            return failed(this).output(e.getMessage()).build();
        }
    }

    private AttackResult isSolved(byte[] currentImage, byte[] newImage) {
        if (Arrays.equals(currentImage, newImage)) {
//            如果两个图片相等
            return failed(this).output("path-traversal-zip-slip.extracted").build();
        }
        return success(this).output("path-traversal-zip-slip.extracted").build();

    }
    @GetMapping("/PathTraversal/zip-slip")
    @ResponseBody
    public ResponseEntity<?> getProfilePicture(){
        return super.getProfilePicture();
    }
    @GetMapping("/PathTraversal/zip-slip/profile-image/{username}")
    @ResponseBody
    public ResponseEntity<?> getProfilePicture(@PathVariable("username")String username){
        return ResponseEntity.notFound().build();
    }
}
