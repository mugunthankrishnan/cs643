package org.carrecognition;

import java.util.List;
import java.util.ArrayList;
import java.lang.Thread;

import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;

/*  UCID:   mk2246
    Desc:   This GetImageLabels class is to identify all the images that have the label as
            'Car' and the image key is sent to SQS as a message. The image index are sent to SQS 2 seconds after
            the previous one is sent to showcase a manual delay for viewing purpose.
*/

public class GetImageLabels{
    public static void getCarLabels(List<String> imageIndices) throws InterruptedException{
        String bucket = "njit-cs-643";
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
        List<String> cars = new ArrayList<String>();
        SendImageToQueue sender = new SendImageToQueue();
        for(String image : imageIndices){
            DetectLabelsRequest request = new DetectLabelsRequest()
                    .withImage(new Image().withS3Object(new S3Object().withName(image).withBucket(bucket)))
                    .withMaxLabels(10).withMinConfidence(90F);
            try {
                DetectLabelsResult result = rekognitionClient.detectLabels(request);
                List<Label> labels = result.getLabels();
                
                for (Label label : labels) {
                    if(label.getName().equals("Car")){
                        cars.add(image);
                        sender.addImageIndexToSQS(image);
                        Thread.sleep(2000);
                    }
                }
            }
            catch (AmazonRekognitionException e) {
                e.printStackTrace();
            }
        }
        // Sending -1.jpg to indicate to receiver that there are no more images to be sent.
        String stopImage = "-1.jpg";
        sender.addImageIndexToSQS(stopImage);
        System.exit(1);
    }
}