package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"path-traversal-profile.hint1", "path-traversal-profile.hint2", "path-traversal-profile.hint3"})
public class ProfileUpload extends ProfileUploadBase{
    public ProfileUpload(@Value("${webgoat.server.directory}")String webGoatHomeDirectory, WebSession webSession){
//        获取当前路径信息
        super(webGoatHomeDirectory,webSession);
    }
    @PostMapping(value = "/PathTraversal/profile-upload",consumes = ALL_VALUE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult uploadFileHandler(@RequestParam("uploadedFile")MultipartFile file,@RequestParam(value = "fullName",required = false)String fullName){
        return super.execute(file,fullName);
//        首先获取了一个表单文件 接着传入了一个名字
    }
    @GetMapping("/PathTraversal/profile-picture")
    @ResponseBody
    public ResponseEntity<?> getProfilePicture(){
        return super.getProfilePicture();
//        获取图片信息
    }

}
