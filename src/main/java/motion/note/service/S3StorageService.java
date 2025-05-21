package motion.note.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.Duration;

@Service
public class S3StorageService {
    private final String bucketName;
    private final S3Client s3Client;

    public S3StorageService(@Value("${aws.s3.bucket-name}") String bucketName, S3Client s3Client) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }


    public void uploadFile(String key, byte[] content) {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromBytes(content));
    }

    public byte[] downloadFile(String key) {
        return s3Client.getObjectAsBytes(GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build())
                .asByteArray();
    }

//    // Generate pre-signed URL (for secure downloads)
//    public String generatePresignedUrl(String key, Duration expiry) {
//        return s3Client.utilities().getPresignedUrl(builder -> builder
//                .signatureDuration(expiry)
//                .getObjectRequest(GetObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(key)
//                        .build()));
//    }
}
