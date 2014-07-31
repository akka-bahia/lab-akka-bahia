package actors.s3aws;

import java.util.Collection;

/**
 * Created by w6c on 27/07/2014.
 */
public class S3FileCollectionMessage {

    public final Collection<S3FileObject> s3FileObjectCollection;

    public S3FileCollectionMessage(Collection<S3FileObject> s3FileObjectCollection) {
        this.s3FileObjectCollection = s3FileObjectCollection;
    }
}
