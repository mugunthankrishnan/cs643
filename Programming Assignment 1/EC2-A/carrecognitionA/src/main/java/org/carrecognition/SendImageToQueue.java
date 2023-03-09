package org.carrecognition;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SqsException;

/*  UCID:   mk2246
    Desc:   This SendImageToQueue class is to send all the images to SQS as a message.
*/

public class SendImageToQueue{
    public static void addImageIndexToSQS(String imageIndex){
        String queueUrl ="https://sqs.us-east-1.amazonaws.com/017181574328/PrgmAssignment1.fifo";
        String msggrpId = "carimages";
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageGroupId(msggrpId)
                .messageBody(imageIndex)
                .build());
    }
}