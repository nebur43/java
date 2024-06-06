package com.nebur.aws;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class CopyFileLambda2 implements RequestHandler<Object, String> {

    private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    private static final String SOURCE_BUCKET_NAME = "nombre-del-bucket";
    private static final String INGEST_FOLDER = "ingest/";
    private static final String COPIA_FOLDER = "copia/";

    public String handleRequest(Object input, Context context) {
        // Obtiene la lista de objetos en la carpeta "ingest" del bucket.
        List<S3ObjectSummary> objects = s3.listObjects(SOURCE_BUCKET_NAME, INGEST_FOLDER).getObjectSummaries();

        // Copia cada objeto a la carpeta "copia".
        for (S3ObjectSummary object : objects) {
            String key = object.getKey();
            CopyObjectRequest copyObjRequest = new CopyObjectRequest(SOURCE_BUCKET_NAME, key, SOURCE_BUCKET_NAME, COPIA_FOLDER + key);
            s3.copyObject(copyObjRequest);
        }

        return "Archivos copiados exitosamente.";
    }
}
