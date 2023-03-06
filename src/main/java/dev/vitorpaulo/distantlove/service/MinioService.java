package dev.vitorpaulo.distantlove.service;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import dev.vitorpaulo.distantlove.model.BucketFolder;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Service
@AllArgsConstructor
public class MinioService {
    
    private static final String SEPARATOR = "/";

    private final MinioClient minioClient;
    private final String bucket;

    public String uploadPhoto(BucketFolder folder, InputStream stream, String extension) {
        final var name = RandomStringUtils.randomAlphanumeric(15) + "." + extension;
        uploadPhoto(folder, name, stream);

        return name;
    }

    public String uploadPhotoAndSign(BucketFolder folder, String name, InputStream stream) {
        uploadPhoto(folder, name, stream);
        return getSignedPhoto(folder, name);
    }

    @SneakyThrows
    public void uploadPhoto(BucketFolder folder, String name, InputStream stream) {
        final var split = name.split("\\.");

        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .object(folder.name().toLowerCase() + SEPARATOR + name)
                .stream(stream, -1, 10485760)
                .contentType("image" + SEPARATOR + split[split.length - 1])
                .build()
        );
    }

    public String getSignedPhoto(BucketFolder folder, String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        return getSignedPhoto(folder.name().toLowerCase() + SEPARATOR + name);
    }

    @SneakyThrows
    public String getSignedPhoto(String object) {
        return minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .bucket(bucket)
                .method(Method.GET)
                .object(object)
                .expiry(1, TimeUnit.DAYS)
                .build()
        );
    }
}
