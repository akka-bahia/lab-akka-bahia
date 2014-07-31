package actors.s3aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import utils.ToolsUtil;

/**
 * Created by w6c on 30/07/2014.
 */


public class S3Helper {


    public static void putS3(S3FileObject message){

        System.out.println("S3Helper - putS3");

        if(message instanceof S3FileObject){
            //Config config = ConfigFactory.load();

            //###########################################################

            try{
                //final Config config = ConfigFactory.load();

                //String accessKey = config.getString("accessKey");
                //String secretKey = config.getString("secretKey");

                //System.out.println("accessKey: " + accessKey);
                //System.out.println("secretKey: " + secretKey);

                //AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

                System.out.println("S3Helper - PRE TransferManager");

                //TransferManager tm =  new TransferManager(awsCredentials);
                //###########################################################

                TransferManager tm =  new TransferManager(ToolsUtil.awsCredentials);

                System.out.println("S3Helper - POS TransferManager");

                final S3FileObject s3FileObject = (S3FileObject) message;

                String nameObjectInBucket = s3FileObject.getBucketPath() + s3FileObject.getKeyFileName();

                System.out.println("nameObjectInBucket: " + nameObjectInBucket);
                System.out.println("s3FileObject.getFile().canRead: " + s3FileObject.getFile().canRead());


                Upload upload = tm.upload(new PutObjectRequest(s3FileObject.getBucketName(),
                        nameObjectInBucket, s3FileObject.getFile()));

                System.out.println("upload.getDescription: " + upload.getDescription());

                if(upload.isDone()){
                    System.out.println("S3Helper - upload.isDone");

                    tm.shutdownNow();
                }

            } catch (AmazonServiceException ase) {

                //retorno = false;

                System.out.println("Caught an AmazonServiceException, which "
                        + "means your request made it "
                        + "to Amazon S3, but was rejected with an error response"
                        + " for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());
            } catch (AmazonClientException ace) {

                //retorno = false;

                System.out.println("Caught an AmazonClientException, which "
                        + "means the client encountered "
                        + "an internal error while trying to "
                        + "communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            }catch(Exception ex){

                ex.printStackTrace();
            }finally {
                System.out.println("S3Helper - SUCCESSFULL");
            }


            //System.out.println("S3AWSWorker - upload.getProgress().getPercentTransferred: " + upload.getProgress().getPercentTransferred());
        }

        System.out.println("S3Helper - putS3 - AFTER");
    }

}
