package com.myorg;

import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketAccessControl;
import software.constructs.Construct;

public class CdkbucketStack extends Stack {
	
    public CdkbucketStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CdkbucketStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // The code that defines your stack goes here
//        createSimpleBucket();
        createPublicBucket();
    }
    
    private void createSimpleBucket() {
    	Bucket.Builder.create(this, "MiPrimerBucketPorCDK")
        .versioned(true)
        .removalPolicy(RemovalPolicy.DESTROY)
        .autoDeleteObjects(true)
        .build();
    }
    
    /**
     * no se puede:
     * AccessControl can only be modified after the bucket has been created.
     */
    private void createPublicBucket() {
    	BlockPublicAccess enablePublicAccess = BlockPublicAccess.Builder.create()
    			.ignorePublicAcls(false)
    			.blockPublicAcls(false)
    			.blockPublicPolicy(false)
    			.restrictPublicBuckets(false)
    			.build();
    	
    	Bucket.Builder.create(this, "webTestCdk")
    	.blockPublicAccess(enablePublicAccess)
        .publicReadAccess(true)
        .accessControl(BucketAccessControl.PUBLIC_READ)
        .removalPolicy(RemovalPolicy.DESTROY)
        .autoDeleteObjects(true)
        .websiteIndexDocument("index.html")
        .websiteErrorDocument("index.html")
        .build();
    }
    
}
