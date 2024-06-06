package com.nebur.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;

public class CopyFileLambda implements RequestHandler<SQSEvent, Void> {

    private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    private static final String SOURCE_BUCKET_NAME = "labstack-ff6ee3ed-93fe-43ee-b2de-50042e-labbucket-zvajik7jool2";
    private static final String INGEST_FOLDER = "ingest/";
    private static final String COPIA_FOLDER = "copia/";

    public Void handleRequest(SQSEvent event, Context context) {
    	LambdaLogger logger = context.getLogger();
        for (SQSEvent.SQSMessage message : event.getRecords()) {
        	logger.log("message: " + message.getBody());
            String fileName = message.getBody();
//            S3Object s3Object = s3.getObject(SOURCE_BUCKET_NAME, INGEST_FOLDER + fileName);
//            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
            CopyObjectRequest copyObjRequest = new CopyObjectRequest(SOURCE_BUCKET_NAME, INGEST_FOLDER + fileName, SOURCE_BUCKET_NAME, COPIA_FOLDER + fileName);
            s3.copyObject(copyObjRequest);
        }
        return null;
    }
}
