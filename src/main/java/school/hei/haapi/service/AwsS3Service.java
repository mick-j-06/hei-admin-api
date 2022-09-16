package school.hei.haapi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwsS3Service {
    @Value("${s3.bucket.name}")
    private String bucketname;
    @Autowired
    private AmazonS3 amazonS3;

    public List<S3ObjectSummary> listObjects() {
        ObjectListing objectListing = this.amazonS3.listObjects(this.bucketname);
        return objectListing.getObjectSummaries();
    }
}
