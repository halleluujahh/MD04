package org.com.session06.service;

import org.com.session06.exception.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    // Lay du lieu file trong DTO -> save vao thu muc upload tomcat
    String uploadFileToLocal(MultipartFile multipartFile) throws BadRequestException;
    // Lay file tu upload local -> storage cua firebase
    String uploadFileToFireBase(String localFilePath);
}
