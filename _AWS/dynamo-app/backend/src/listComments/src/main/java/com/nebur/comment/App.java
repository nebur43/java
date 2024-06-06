package com.nebur.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
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

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
    	LambdaLogger logger = context.getLogger();
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    
    	
    	String id = input.getPathParameters().get("id");
    	Long idForo = Long.valueOf(id);
    	logger.log("recuperando Stage comentarios de foro: " + id);
    	
    	List<Comment> comments = getComments(idForo);
    	logger.log("comentarios recuperados: " + comments.size());
    	String body = null;
    	try {
			body = mapper.writeValueAsString(comments);
		} catch (JsonProcessingException e) {
			logger.log("error creando json: " + e.getMessage());
			return response.
					withBody(e.getMessage()).
					withStatusCode(500);
		}
    	
    	Map<String, String> headers = new HashMap<>();
    	headers.put("Content-Type", "application/json");
    	headers.put("X-Custom-Header", "application/json");
    	headers.put("Access-Control-Allow-Headers", "Content-Type");
    	headers.put("Access-Control-Allow-Origin", "*");
    	headers.put("Access-Control-Allow-Methods", "GET");
    	
    	response.withBody(body)
    			.withHeaders(headers);

    	return response.withStatusCode(200);
    }
    
    private List<Comment> getComments(long foroId) {
    	ArrayList<Comment> comments = new ArrayList<>();
    	
    	DynamoDbTable<Comment> mappedTable = enhancedClient
                .table("Thread", TableSchema.fromBean(Comment.class));
    	
    	Key key = Key.builder().partitionValue(foroId).build();
    	QueryConditional queryConditional = QueryConditional
    	        .keyEqualTo(key);
    	
    	mappedTable.query(queryConditional).forEach( x -> comments.addAll(x.items()));
    	return comments;
    }
}
