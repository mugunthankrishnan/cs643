**CS643 - Cloud Computing**

**UCID: mk2246**

**Name: Mugunthan Krishnan**

**Programming Assignment 1**

**Setup Instructions:**

Java SE Development Kit 15 - https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html#license-lightbox

Apache Maven 4.0.0 - https://dlcdn.apache.org/maven/maven-4/4.0.0-alpha-4/source/apache-maven-4.0.0-alpha-4-src.tar.gz

**Install JDK using the following command in terminal-**

**sudo yum install <jdk_file.rpm>**

**Install Maven following the below steps:**

Unzip the maven tar.zip file - **tar xzvf <maven_file.tar.gz>**

Add the maven /bin directory to the PATH environment variable - **export PATH=<location_of_bin_directory>:$PATH**

**Copy the AWS Access Key ID, AWS Secret Access Key and AWS Session Token to ~/.aws/credentials and configure region to us-east-1 and output format as json.**

**Use aws configure to configure the above and the resulting files are ~/.aws/credentials and ~/.aws/config**

**The AWS services used are EC2 instances, S3 Buckets, Simple Queue Service and AWS Rekognition.**

**Amazon EC2 Instance Setup:**

Create two separate EC2 instances with the following properties:

**EC2-A Properties:**

![image](https://user-images.githubusercontent.com/123665839/223913030-d47d6949-6923-4d70-89fd-09f288bcfbcc.png)
![image](https://user-images.githubusercontent.com/123665839/223913372-a91d0a02-09d8-41e0-9b43-7a33592f7ec2.png)
![image](https://user-images.githubusercontent.com/123665839/223913461-6c51cde0-9478-4f2d-b082-df40f8558c49.png)

**EC2-B Properties:**

![image](https://user-images.githubusercontent.com/123665839/223913625-2c81c5eb-db63-4213-bf01-3ef6d7bacb52.png)
![image](https://user-images.githubusercontent.com/123665839/223913801-ee6f2b77-b084-4242-b08c-dbb5af905c42.png)
![image](https://user-images.githubusercontent.com/123665839/223913877-71479c13-74a9-4fb3-9c3a-c3f8d6bddf20.png)

Create a Simple Message Queue Service in the AWS Console with the following properties:

**SQS Properties:**
![image](https://user-images.githubusercontent.com/123665839/223914136-50804fca-97c4-4244-bb2d-4c10da3f6680.png)

The maven build of EC2 instance A:

**Instance A Build Successful:**
![image](https://user-images.githubusercontent.com/123665839/223914612-0d2081e6-f98f-4181-ad31-b93611fce882.png)

The maven build of EC2 instance B:

**Instance B Build Successful:**
![image](https://user-images.githubusercontent.com/123665839/223914418-3f5a3a15-237a-4d63-9371-ffe7f37191b4.png)

The resulting file is populated with the image index followed by the text found in the corresponding image.

**Result File Created:**
![image](https://user-images.githubusercontent.com/123665839/223914851-b750fec1-5529-482a-9c87-00e5fc5c711b.png)

**Steps to run the 2 maven projects:**

1. Install the above Java and Maven versions.
2. Navigate to the maven project location and use the command **"mvn clean package"** to build the maven project.
3. After the build is successful, run the command - **mvn exec:java -Dexec.mainClass="org.carrecognition.App"** to run the applications in both the EC2 instances.
4. The resulting text file is created in the path **/home/ec2-user/ProgrammingAssignment1_CarTexts.txt**
