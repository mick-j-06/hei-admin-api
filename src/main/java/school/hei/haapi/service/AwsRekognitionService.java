package school.hei.haapi.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.file.NoSuchFileException;
import java.util.List;

@Service
public class AwsRekognitionService {
    @Autowired
    private AmazonRekognition rekognitionClient;
    @Autowired
    private AwsS3Service awsS3Service;

    public List<CompareFacesMatch> compareFacesMatches(String keyName, byte[] sourceImage) throws NoSuchFileException {
        ByteBuffer sourceImageBuffered = ByteBuffer.wrap(sourceImage);
        ByteBuffer targetImageBuffered = ByteBuffer.wrap(awsS3Service.getImage(keyName));

        Image imageSource = new Image().withBytes(sourceImageBuffered);
        Image imageTarget = new Image().withBytes(targetImageBuffered);
        Float similarityThreshold = 80F;

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(imageSource)
                .withTargetImage(imageTarget)
                .withSimilarityThreshold(similarityThreshold);

        CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);

        List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
        for (CompareFacesMatch match : faceDetails) {
            ComparedFace face = match.getFace();
            BoundingBox position = face.getBoundingBox();
            System.out.println("Face at " + position.getLeft().toString()
                    + " " + position.getTop()
                    + " matches with " + match.getSimilarity().toString()
                    + "% confidence.");

        }
        List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

        System.out.println("There was " + uncompared.size()
                + " face(s) that did not match");
        return faceDetails;
    }
}
