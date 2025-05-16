package com.nebur.openai_client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.http.StreamResponse;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionChunk;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStreamEvent;

public class StreamTest {

	private static final Logger LOGGER = LogManager.getLogger(StreamTest.class);
	
    @Test
    public void responsesApiTest() {
    	LOGGER.info(() -> "Running OpenAiTest");
    	// Configures using the enviroment `OPENAI_API_KEY`, `OPENAI_ORG_ID`, `OPENAI_PROJECT_ID` and `OPENAI_BASE_URL` environment variables
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ResponseCreateParams params = ResponseCreateParams.builder()
    			.instructions("Responde siempre en inglés")
    	        .input("Cuenta una historia de terror")
    	        .model(ChatModel.GPT_4O_MINI)
    	        .build();
    	try (StreamResponse<ResponseStreamEvent> streamResponse = client.responses().createStreaming(params) ) {
    		streamResponse.stream().forEach(chunk -> {
    			if (chunk.outputTextDelta().isPresent()) {
					LOGGER.info(() -> "Chunk: " + chunk.outputTextDelta().get().delta());
				} 
		    });
    		LOGGER.info(() -> "No more chunks!");
    	}
    }
	
	
	@Test
	public void chatCompletionsStreamTest() {
		 OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		 ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
	    			.model(ChatModel.GPT_4O_MINI)
	    			.addSystemMessage("Responde siempre en inglés")
	    			.addUserMessage("Cuenta un chiste")
	    			.build();
		
		try (StreamResponse<ChatCompletionChunk> streamResponse = client.chat().completions().createStreaming(params)) {
		    streamResponse.stream().forEach(chunk -> {
		    	LOGGER.info(() -> "Chunk: " + chunk.choices().get(0).delta().content().get());
		    });
		    LOGGER.info(() -> "No more chunks!");
		}
		
	}
}
