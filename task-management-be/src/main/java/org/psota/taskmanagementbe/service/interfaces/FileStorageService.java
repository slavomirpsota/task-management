package org.psota.taskmanagementbe.service.interfaces;

import io.minio.GetObjectResponse;
import io.minio.ObjectWriteResponse;
import org.psota.taskmanagementbe.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    ObjectWriteResponse uploadFile(String objectName, MultipartFile file) throws ServiceException;

    GetObjectResponse downloadFile(String bucketName, String objectName) throws ServiceException;

    void deleteFile(String objectName) throws ServiceException;
}