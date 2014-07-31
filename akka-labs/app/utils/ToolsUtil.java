package utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by w6c on 28/07/2014.
 */
public class ToolsUtil {

    final static Config config = ConfigFactory.load();

    final static String accessKey = config.getString("accessKey");
    final static String secretKey = config.getString("secretKey");

    public static AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

}
