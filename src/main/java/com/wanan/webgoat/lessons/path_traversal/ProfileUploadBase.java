package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@AllArgsConstructor
@Getter
public class ProfileUploadBase extends AssignmentEndpoint {
    private String webGoatHomeDirectory;
    private WebSession webSession;
    protected AttackResult execute(MultipartFile file,String fullName){
        if (file.isEmpty()){
//            如果文件是空的
            return failed(this).feedback("path-traversal-profile-empty-file").build();
        }
        if (StringUtils.isEmpty(fullName)){
//            如果fullName是空的
            return failed(this).feedback("path-traversal-profile-empty-name").build();
        }
        var uploadDirectory = new File(this.webGoatHomeDirectory,"/PathTraversal/" + webSession.getUserName());
//        新建一个文件目录路径对象
        if (uploadDirectory.exists()){
//            如果上传的文件目录存在 就递归删除
            FileSystemUtils.deleteRecursively(uploadDirectory);

        }
        try {
            uploadDirectory.mkdirs();
//            创建文件夹
            var uploadedFile = new File(uploadDirectory,fullName);
//            传入上传路径与文件名
            uploadedFile.createNewFile();
//            创建一个新文件
            FileCopyUtils.copy(file.getBytes(),uploadedFile);
//          将传入文件信息复制到 新文件中
            if (attemptWasMade(uploadDirectory,uploadedFile)){
//             如果文件的存放路径 与文件的路径不同
                return solvedIt(uploadedFile);
            }
            return informationMessage(this).feedback("path-traversal-profile-updated").feedbackArgs(uploadDirectory.getAbsoluteFile()).build();
        }catch (IOException e){
            return failed(this).output(e.getMessage()).build();
        }
    }

    private AttackResult solvedIt(File uploadedFile) throws IOException {
        if (uploadedFile.getCanonicalFile().getParentFile().getName().endsWith("PathTraversal")){
//            如果上传的文件上级文件名为PathTraversal
            return success(this).build();
        }
        return failed(this).attemptWasMade().feedback("path-traversal-profile-attempt").feedbackArgs(uploadedFile.getCanonicalPath()).build();
    }

    private boolean attemptWasMade(File expectedUploadDirectory, File uploadedFile) throws IOException {
        return !expectedUploadDirectory.getCanonicalPath().equals(uploadedFile.getParentFile().getCanonicalPath());
//       是否存在这个文件
    }
    public ResponseEntity<?> getProfilePicture(){
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                .body(getProfilePictureAsBase64());
    }
    protected byte[] getProfilePictureAsBase64(){
        var profilePictureDirectory = new File(this.webGoatHomeDirectory,"/PathTraversal/" + webSession.getUserName());
//        读取文件
        var profileDirectoryFiles = profilePictureDirectory.listFiles();
//        将目录下的所有文件列出来
        if (profileDirectoryFiles != null && profileDirectoryFiles.length > 0){
//            如果文件不是空
            try(var inputStream = new FileInputStream(profileDirectoryFiles[0])) {
//                就获取第一个文件
                return Base64.getEncoder().encode(FileCopyUtils.copyToByteArray(inputStream));
//                进行base64编码输出
            }catch (IOException e){
                return defaultImage();
            }
        }else {
            return defaultImage();
        }
    }
    @SneakyThrows
//    自动抛异常
    private byte[] defaultImage() {
        var inputStream = getClass().getResourceAsStream("/images/account.png");
        return Base64.getEncoder().encode(FileCopyUtils.copyToByteArray(inputStream));
    }

}
