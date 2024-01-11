package org.psota.taskmanagementbe.service.util;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.psota.taskmanagementbe.exception.ServiceException;
import org.psota.taskmanagementbe.service.interfaces.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class MinioService implements FileStorageService {

    @Value("${minio.bucketName}")
    private String bucketName;

    private final MinioClient minioClient;

    @Override
    public ObjectWriteResponse uploadFile(String objectName, MultipartFile file) throws ServiceException {
        try {
            return minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new ServiceException("minio.file.upload-error");
        }
    }

    public GetObjectResponse downloadFile(String bucketName, String objectName) throws ServiceException {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new ServiceException("minio.file.download-error");
        }
    }

    @Override
    public void deleteFile(String objectName) throws ServiceException {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new ServiceException("minio.file.remove-error");
        }
    }
}
