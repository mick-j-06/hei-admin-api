package school.hei.haapi.endpoint.rest.controller;

import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.service.AwsRekognitionService;

import java.nio.file.NoSuchFileException;
import java.util.List;

@RestController
public class AwsRekognitionController {
    @Autowired
    private AwsRekognitionService awsRekognitionService;

    @GetMapping(value = "/rekognition")
    public String rek() {
        return "rekognition";
    }

    @PostMapping(value = "/rekognition")
    public List<CompareFacesMatch> getAll(@RequestBody byte[] image) throws NoSuchFileException {
        return awsRekognitionService.compareFacesMatches(image);
    }
}
