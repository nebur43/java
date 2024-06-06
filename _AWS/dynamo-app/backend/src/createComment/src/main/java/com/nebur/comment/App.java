package com.nebur.comment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebur.comment.bean.Comment;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
	
	private ObjectMapper mapper = new ObjectMapper();

	DynamoDbClient ddb = DynamoDbClient.builder()
			.region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
			.credentialsProvider(DefaultCredentialsProvider.builder().build())
//    		.endpointOverride(URI.create("http://192.168.1.100:8000"))
          .build();
	
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(ddb)
            .build();
	
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
    	LambdaLogger logger = context.getLogger();
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    	String body = event.getBody();
    	logger.log("body: " + body );
    	
    	try {
    		Comment comment = mapper.readValue(body, Comment.class);
			logger.log(" name: " + comment.getUsuario()  + " comment:" + comment.getComentario());
			persist(comment);
			logger.log(" commentId: " + comment.getDateComentario());
			
	        Map<String, String> headers = new HashMap<>();
	        headers.put("Content-Type", "application/json");
	        headers.put("X-Custom-Header", "application/json");
	        headers.put("Access-Control-Allow-Headers", "Content-Type");
	    	headers.put("Access-Control-Allow-Origin", "*");
	    	headers.put("Access-Control-Allow-Methods", "PUT");
			
	        return response.withBody("{\"id\": \""+comment.getDateComentario()+"\"}")
	                .withHeaders(headers)
	                .withStatusCode(200);
		} catch (JsonProcessingException e) {
			logger.log("error leyendo input: " + e.getMessage());
			return response.
					withBody(e.getMessage()).
					withStatusCode(500);
		}
    	
    }
    
    private String persist(Comment comment){
    	DynamoDbTable<Comment> mappedTable = enhancedClient
                .table("Thread", TableSchema.fromBean(Comment.class));
    	
    	String commentId = UUID.randomUUID().toString();
        comment.setDateComentario(System.currentTimeMillis());
        comment.setUsuario(removeArroba(comment.getUsuario()));
        mappedTable.putItem(comment);

        return commentId;

    }
    
    private String removeArroba(String s) {
        if (s!=null && s.startsWith("@")) {
        	return removeArroba(s.substring(1, s.length()));
        }
        return s;
    }
    
}
