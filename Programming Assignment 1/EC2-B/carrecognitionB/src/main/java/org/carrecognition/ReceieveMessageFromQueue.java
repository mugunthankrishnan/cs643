package org.carrecognition;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SqsException;
import java.util.List;
import java.util.ArrayList;

/*  UCID:   mk2246
    Desc:   The ReceieveMessageFromQueue class is to retrieve the messages from the SQS queue and process it
            to AWS Rekognition for text recognition. A message is deleted from the queue immediately after its
            read and the queue is purged after the execution to remove unwanted messages from the SQS queue.
*/

public class ReceieveMessageFromQueue{
    public void retrieveImage() throws InterruptedException{
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/017181574328/PrgmAssignment1.fifo";       
        boolean msgFlag = false;
        String bucketName = "njit-cs-643";
        List<String> fileData = new ArrayList<>();
        while(msgFlag == false){
            try{
                ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(1)
                    .build();
            
                List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

                if(messages.size() > 0){
                    String imageName = messages.get(0).body();
                    if(messages.get(0).body().equals("-1.jpg") == false){
                        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(messages.get(0).receiptHandle())
                        .build();
                        sqsClient.deleteMessage(deleteMessageRequest);
                        TextRecognition textObject = new TextRecognition();
                        textObject.findTextInImage(imageName, bucketName, fileData);
                    }
                    else{
                        PurgeQueueRequest purgeQueueRequest = PurgeQueueRequest.builder()
                            .queueUrl(queueUrl)
                            .build();
                        sqsClient.purgeQueue(purgeQueueRequest);
                        TextRecognition textObject = new TextRecognition();
                        textObject.findTextInImage(imageName, bucketName, fileData);
                        msgFlag = true;
                    }
                }
            } catch (SqsException e) {
                System.err.println(e.awsErrorDetails().errorMessage());
            }
        }
        System.exit(1);
    }
}