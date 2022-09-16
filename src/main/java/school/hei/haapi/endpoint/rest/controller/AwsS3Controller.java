package school.hei.haapi.endpoint.rest.controller;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.service.AwsS3Service;

import java.util.List;

@RestController
public class AwsS3Controller {
    @Autowired
    private AwsS3Service awsS3Service;

    @GetMapping(value = "/bucket/summary")
    public List<S3ObjectSummary> getAllFile() {
        return awsS3Service.listObjects();
    }
}
