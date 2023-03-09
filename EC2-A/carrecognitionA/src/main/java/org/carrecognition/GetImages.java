package org.carrecognition;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import java.util.List;
import java.util.ArrayList;

/*  UCID:   mk2246
    Desc:   This GetImages class is to retrieve all the images from the S3 bucket and is passed to
            AWS Rekognition as a list.
*/

public class GetImages{
    private final S3Client s3Client;
    public static List<String> imageIndices = new ArrayList<String>();

    public GetImages() {
        s3Client = DependencyFactory.s3Client();
    }

    /*  UCID:   mk2246
        Desc:   The getImageIndex function retrieves the image names/indices from the S3 bucket
                and calls the getCarLabels function to retrieve the image labels.
    */

    public void getImageIndex() throws InterruptedException{
        String bucketName = "njit-cs-643";
        List<String> sample = listImageBucketObjects(s3Client, bucketName);
        s3Client.close();
        GetImageLabels getcars = new GetImageLabels();
        getcars.getCarLabels(sample);
    }

    /*  UCID:   mk2246
        Desc:   The listImageBucketObjects function takes in s3client object and bucket name as
                parameters and list the image objects from the S3 bucket as a list.
    */

    public static List listImageBucketObjects(S3Client s3, String bucketName) {
        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .bucket(bucketName)
                .build();

            ListObjectsResponse res = s3.listObjects(listObjects);
            List<S3Object> objects = res.contents();
            for (S3Object myValue : objects) {
                imageIndices.add(myValue.key());
            }
        }
        catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return imageIndices;
    }
}