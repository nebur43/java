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
import com.nebur.comment.bean.Foro;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
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
    

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
    	LambdaLogger logger = context.getLogger();
    	
    	APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    	Object respuesta ;
    	if ( input.getPathParameters() == null  || input.getPathParameters().isEmpty() ) {
    		logger.log("Listando Stage foros...");
    		List<Foro> foros = getForos();
    		logger.log("recuperado " + foros.size()+ " foros");
    		respuesta = foros;
    	} else {
    		String id = input.getPathParameters().get("id");
    		logger.log("Listando foro " + id);
    		respuesta = getForo(id);
    	}
    	
    	
    	
    	String body = null;
    	try {
			body = mapper.writeValueAsString(respuesta);
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
    	
    	
    	return response.withBody(body)
    			.withHeaders(headers)
    			.withStatusCode(200);
    }
    
    private Foro getForo(String id) {
    	DynamoDbTable<Foro> mappedTable = enhancedClient
                .table("Foro", TableSchema.fromBean(Foro.class));
    	Long idForo = Long.valueOf(id);
    	Key key = Key.builder().partitionValue(1).sortValue(idForo).build();
    	Foro foro = mappedTable.getItem(key);
    	return foro;
	}

	private List<Foro> getForos() {
    	ArrayList<Foro> foros = new ArrayList<>();
    	
    	DynamoDbTable<Foro> mappedTable = enhancedClient
                .table("Foro", TableSchema.fromBean(Foro.class));
    	mappedTable.scan().forEach( x -> foros.addAll(x.items()));
    	return foros;
    }
}
