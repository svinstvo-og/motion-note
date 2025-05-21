package motion.note.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3ClientConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.credentials.access-key}")
    String accessKey;
    @Value("${aws.credentials.secret-key}")
    String secretKey;

    AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

    @Bean
    public S3Client s3Client() {
        return S3Client
                .builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

}
