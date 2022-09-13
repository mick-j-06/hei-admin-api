package school.hei.haapi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class AwsS3Service {
    private AmazonS3 amazonS3;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            log.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }


    @Bean
    public S3ObjectInputStream findByName(String fileName) {
        log.info("Downloading file with name {}", fileName);
        return amazonS3.getObject(s3BucketName, fileName).getObjectContent();
    }
}
