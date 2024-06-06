package com.nebur.comment;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebur.comment.bean.Foro;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
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

    private final DynamoDbEnhancedClient enhancedClient; 
    
    public App() throws URISyntaxException {

    	DynamoDbClient ddb = DynamoDbClient.builder()
    			.region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
    			.credentialsProvider(DefaultCredentialsProvider.builder().build())
//        		.endpointOverride(URI.create("http://192.168.1.100:8000"))
              .build();
    	
    	enhancedClient= DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }
	
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
    	LambdaLogger logger = context.getLogger();
    	logger.log("escribiendo nuevo foro...");
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    	
    	String body = event.getBody();
    	
    	logger.log("body: " + body );
    	
    	try {
    		Foro foro = mapper.readValue(body, Foro.class);
			logger.log(" name: " + foro.getUsuario()  + " asunto:" + foro.getAsunto());
			persist(foro);
			logger.log(" dateForo: " + foro.getDateForo());
			
	        Map<String, String> headers = new HashMap<>();
	        headers.put("Content-Type", "application/json");
	        headers.put("X-Custom-Header", "application/json");
	        headers.put("Access-Control-Allow-Headers", "Content-Type");
	    	headers.put("Access-Control-Allow-Origin", "*");
	    	headers.put("Access-Control-Allow-Methods", "PUT");
			
	        return response.withBody("{\"id\": \""+foro.getDateForo()+"\"}")
	                .withHeaders(headers)
	                .withStatusCode(200);
		} catch (JsonProcessingException e) {
			logger.log("error leyendo input: " + e.getMessage());
			return response.
					withBody(e.getMessage()).
					withStatusCode(500);
		} 

    	
    }
    
    private void persist(Foro foro){
    	DynamoDbTable<Foro> mappedTable = enhancedClient
                .table("Foro", TableSchema.fromBean(Foro.class));
    	foro.setId(1);
        foro.setDateForo(System.currentTimeMillis());
        foro.setUsuario(removeArroba(foro.getUsuario()));
        mappedTable.putItem(foro);

    }

    private String removeArroba(String s) {
        if (s!=null && s.startsWith("@")) {
        	return removeArroba(s.substring(1, s.length()));
        }
        return s;
    }
}
