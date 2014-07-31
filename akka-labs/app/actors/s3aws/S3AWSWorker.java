package actors.s3aws;

import actors.s3aws.S3FileObject;

import akka.actor.UntypedActor;

import java.io.IOException;
import java.util.Collection;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.AWSCredentials;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import java.util.Collection;

import actors.s3aws.S3FileObject;
//import actors.s3aws.S3FileCollectionMessage;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import utils.ToolsUtil;

import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by w6c on 27/07/2014.
 */

public class S3AWSWorker extends UntypedActor{

    TransferManager tm;

    @Override
    public void onReceive(Object message) throws Exception {

        System.out.println("S3AWSWorker - onReceive");

        if(message instanceof S3FileObject){

            try{
                final Config config = ConfigFactory.load();

                System.out.println("S3AWSWorker - PRE TransferManager");

                tm =  new TransferManager(ToolsUtil.awsCredentials);

                System.out.println("S3AWSWorker - POS TransferManager");

                final S3FileObject s3FileObject = (S3FileObject) message;

                String nameObjectInBucket = s3FileObject.getBucketPath() + s3FileObject.getKeyFileName();

                Upload upload = tm.upload(new PutObjectRequest(s3FileObject.getBucketName(),
                        nameObjectInBucket, s3FileObject.getFile()));

                upload.waitForCompletion();

                if(upload.isDone()){
                    System.out.println("S3AWSWorker - isDone:");
                }

                System.out.println("S3AWSWorker - OK: " + new Date());

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
                System.out.println("S3AWSWorker - finally");

                if(tm != null){
                    tm.shutdownNow();

                    System.out.println("S3AWSWorker - finally - shutdownNow");
                }
            }

            //System.out.println("S3AWSWorker - upload.getProgress().getPercentTransferred: " + upload.getProgress().getPercentTransferred());

        }else{
            System.out.println("**** S3AWSWorker - unhandled ****");
            unhandled(message);
        }
    }
}