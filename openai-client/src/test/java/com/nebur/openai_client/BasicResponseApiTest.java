package com.nebur.openai_client;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseInputItem;

/**
 * https://github.com/openai/openai-java
 * 
 * Unit test for simple App.
 */
public class BasicResponseApiTest {

	private static final Logger LOGGER = LogManager.getLogger(BasicResponseApiTest.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	
	
	/**
	 * The primary API for interacting with OpenAI models is the Responses API.
	 * 
	 * https://platform.openai.com/docs/api-reference/responses
	 */
    @Test
    public void responsesApiTest() {
    	LOGGER.info("Running responsesApiTest");
    	// Configures using the enviroment `OPENAI_API_KEY`, `OPENAI_ORG_ID`, `OPENAI_PROJECT_ID` and `OPENAI_BASE_URL` environment variables
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ResponseCreateParams params = ResponseCreateParams.builder()
    			.instructions("Responde siempre en Ruso")
    	        .input("Cuenta una historia de terror")
    	        .model(ChatModel.GPT_4O_MINI)
    	        .build();
    	Response response = client.responses().create(params);
    	   	
    	LOGGER.info("Response: {}", json(response) );
    	LOGGER.info("Response.content: {}", response.output().get(0).asMessage().content().get(0).outputText().get().text() );
    	
    }
    
    @Test
    public void responsesContextApiTest() {
		LOGGER.info("Running responsesContextApiTest");
		// Configures using the enviroment `OPENAI_API_KEY`, `OPENAI_ORG_ID`, `OPENAI_PROJECT_ID` and `OPENAI_BASE_URL` environment variables
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        List<ResponseInputItem> inputItems = new ArrayList<>();
        
        ResponseCreateParams params = ResponseCreateParams.builder()
        		.instructions("Eres un profesor de geografía en una clase de 5º de primaria")
                .inputOfResponse(inputItems)
                .model(ChatModel.GPT_4O_MINI)
                .build();
        
        userAskWithHistorial("¿Cuál es la capital de España?", client, params, inputItems);
        userAskWithHistorial("Cuántos habitantes tiene?", client, params, inputItems);
        userAskWithHistorial("¿Cual es la capital de Alemania?", client, params, inputItems);
        userAskWithHistorial("¿Y cuantos tiene?", client, params, inputItems);

		
	}
    
    private void userAskWithHistorial(String pregunta, OpenAIClient client, ResponseCreateParams createParams, List<ResponseInputItem> inputItems) {
    	
    	LOGGER.info("{}",pregunta);
    	
    	inputItems.add(ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()
                .role(EasyInputMessage.Role.USER)
                .content(pregunta)
                .build()));
    	
    	// este paso no es necesario, ya que se añade al historial
    	createParams = createParams.toBuilder().inputOfResponse(inputItems).build();
        
        Response response = client.responses().create(createParams);
        
        response.output().forEach(item -> {
//			LOGGER.debug("Response: {}", json(item));
			LOGGER.info("{}", item.asMessage().content().get(0).outputText().get().text());
			inputItems.add(ResponseInputItem.ofResponseOutputMessage(item.asMessage()));
		});
    }
    
    
    private String json(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			LOGGER.error("Error serializing object to JSON", e);
			return null;
		}
    }
    
    
}
