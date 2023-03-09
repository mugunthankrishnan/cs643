package org.carrecognition;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/*  UCID:   mk2246
    Desc:   The FileCreate class is to create a file in the EBS volume with the car image index and
            the texts present in the car image.
*/

public class FileCreate{
    public static void createFileInEBS(List<String> fileData){
        try {
            String fileName = "/home/ec2-user/ProgrammingAssignment1_CarTexts.txt";
            FileWriter myWriter = new FileWriter(fileName);
            for(String text : fileData){
                myWriter.write(text);
            }
            System.out.println("File successfully created in the path: " + fileName);
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } 
    }
}