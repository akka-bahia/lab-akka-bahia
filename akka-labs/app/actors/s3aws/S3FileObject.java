package actors.s3aws;

import java.io.File;

/**
 * Created by w6c on 27/07/2014.
 */

public class S3FileObject {

    private final String bucketName;
    private final String bucketPath;
    private final String keyFileName;
    private final File file;

    public S3FileObject(String bucketName, String bucketPath, String keyFileName, File file) {
        this.bucketName = bucketName;
        this.bucketPath = bucketPath;
        this.keyFileName = keyFileName;
        this.file = file;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getBucketPath() {
        return bucketPath;
    }

    public String getKeyFileName() {
        return keyFileName;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "S3FileObject{" + "bucketName=" + bucketName + ", bucketPath=" + bucketPath + ", keyFileName=" + keyFileName + ", file=" + file + '}';
    }

}
