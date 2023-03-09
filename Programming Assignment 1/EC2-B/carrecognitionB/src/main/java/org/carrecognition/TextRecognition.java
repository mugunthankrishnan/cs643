package org.carrecognition;

import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import java.util.List;
import java.util.ArrayList;

/*  UCID:   mk2246
    Desc:   The TextRecognition class is to identify text using AWS Rekognition and create a list of
            the texts present in the car image.
*/

public class TextRecognition{
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();    
    public void findTextInImage(String imgIndex, String bucketName, List<String> fileData){
        if(imgIndex.equals("-1.jpg") == false){
            DetectTextRequest textrequest = new DetectTextRequest()
                .withImage(new Image()
                .withS3Object(new S3Object()
                .withName(imgIndex)
                .withBucket(bucketName)));
            try {
                DetectTextResult textresult = rekognitionClient.detectText(textrequest);
                List<TextDetection> textDetections = textresult.getTextDetections();
                List<String> textData = new ArrayList<>();
                for (TextDetection text: textDetections) {
                    textData.add(text.getDetectedText());
                }
                if(textData.size() > 0){
                    String sentence = imgIndex;
                    for(String text : textData){
                        sentence = sentence + " " + text;
                    }
                    sentence = sentence + "\n";
                    fileData.add(sentence);
                }
            } catch(AmazonRekognitionException e) {
                e.printStackTrace();
            }
        }
        else{
            FileCreate file = new FileCreate();
            file.createFileInEBS(fileData);
        }
    }
}