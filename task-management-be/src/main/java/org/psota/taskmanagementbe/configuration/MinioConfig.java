package org.psota.taskmanagementbe.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.psota.taskmanagementbe.exception.TaskManagementException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Log4j2
@ConditionalOnProperty(name = "minio.enabled", havingValue = "true")
public class MinioConfig {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${MINIO_ROOT_USER}")
    private String rootUser;

    @Value("${MINIO_ROOT_PASSWORD}")
    private String rootPass;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(rootUser, rootPass)
                .build();
    }

    @PostConstruct
    public void createBucketIfNotExists() throws TaskManagementException {
        try {
            var minioClient = minioClient();
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Minio bucket created: " + bucketName);
            } else {
                log.info("Minio bucket already exists: " + bucketName);
            }
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new TaskManagementException("minio.bucket.create-error");
        }
    }
}
