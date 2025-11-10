package org.com.session06.service.impl;

import com.google.cloud.storage.*;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.com.session06.exception.BadRequestException;
import org.com.session06.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {
    private final Storage storage;
    private final ServletContext servletContext;
    private final String BUCK_NAME = "fir-60e00.appspot.com";

    @Override
    public String uploadFileToLocal(MultipartFile multipartFile) throws BadRequestException {
        try {
            //1. Tạo thư mục tạm uploads trong server tomcat
            String pathUpload = servletContext.getRealPath("/");
            File uploadFolder = new File(pathUpload + "/uploads");
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            //2. copy ảnh từ multipart sang thư mục uploads
            String fileName = multipartFile.getOriginalFilename();
            File fileUpload = new File(uploadFolder + File.separator + fileName);
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), fileUpload);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //3. Gọi phương thức upload lên firebase và truyền đối số là đường dẫn ảnh trên tomcat
            return uploadFileToFireBase(uploadFolder + File.separator + fileName);
        }catch (Exception e){
            throw new BadRequestException("Lỗi khi tải file hình ảnh.");
        }
    }

    @Override
    public String uploadFileToFireBase(String localFilePath) {
        Path localPath = Paths.get(localFilePath);
        String fileName = localPath.getFileName().toString();

        BlobId blobId = BlobId.of(BUCK_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        // Thiết lập quyền truy cập công cộng
        List<Acl> acls = new ArrayList<>();
        acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        blobInfo = blobInfo.toBuilder().setAcl(acls).build();
        try {
            Blob blob = storage.create(blobInfo, Files.readAllBytes(localPath));
            return blob.getMediaLink();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}