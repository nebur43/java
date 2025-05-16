package com.nebur.openai_client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionAssistantMessageParam;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionMessage;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

/**
 * https://github.com/openai/openai-java
 * 
 * Chat completions API. Use better resposneApi for simple text generation.
 * 
 * Unit test for simple App.
 */
public class BasicChatCompletionTest {

	private static final Logger LOGGER = LogManager.getLogger(BasicChatCompletionTest.class);
	
    /**
     * The previous standard (supported indefinitely) for generating text is the Chat Completions API. 
     * 
     * https://platform.openai.com/docs/api-reference/chat
     */
    @Test
    public void chatCompletionsApiTest() {
    	LOGGER.info(() -> "Running OpenAiTest");
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
    			.model(ChatModel.GPT_4O_MINI)
    			.addSystemMessage("Responde siempre en inglés")
    			.addUserMessage("Cuenta un chiste")
    			.build();
    	
    	ChatCompletion cc = client.chat().completions().create(params);
    	   	
    	LOGGER.info(() -> "ChatCompletion: " + cc );
    	LOGGER.info(() -> "ChatCompletion.content: " + cc.choices().get(0).message().content().get() );
    }
    
    
    /**
     * Don't create more than one client in the same application. Each client has a connection pool and thread pools, 
     * which are more efficient to share between requests.
     */
    @Test
    public void clientConfigurationProgramaticalyTest() {
    	
//    	OpenAIClient client = OpenAIOkHttpClient.builder()
//    		    .apiKey("My API Key")
//    		    .build();
    	
    	// o parcialmente
    	OpenAIClient client = OpenAIOkHttpClient.builder()
    		    // Configures using the `OPENAI_API_KEY`, `OPENAI_ORG_ID`, `OPENAI_PROJECT_ID` and `OPENAI_BASE_URL` environment variables
    		    .fromEnv()
//    		    .apiKey("My API Key")
    		    .project("proj_lU3sgOZe3zGBhNsVorKZkr0W")
    		    .build();
    	
    	ResponseCreateParams params = ResponseCreateParams.builder()
    	        .input("Dime todos los paises de europa")
    	        .model(ChatModel.GPT_4O_MINI)
    	        .build();
    	Response response = client.responses().create(params);
    	   	
    	LOGGER.info(() -> "Response.content: " + response.output().get(0).asMessage().content().get(0).outputText().get().text() );
    	
    }
    
    @Test
    public void chatCompletionsContextApiTest() {
    	LOGGER.info(() -> "Running OpenAiTest");
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
    			.model(ChatModel.GPT_4O_MINI)
    			.addSystemMessage("Eres un profesor de geografía en una clase de 5º de primaria")
    			.build();
    	
    	params = userAskWithHistorial("¿Cuál es la capital de Francia?", client, params);
    	params = userAskWithHistorial("¿Cuántos habitantes tiene?", client, params);
    	params = userAskWithHistorial("¿Cual es la capital de Alemania?", client, params);
    	params = userAskWithHistorial("¿Y cuantos tiene?", client, params);
    	
    	printMessages(params.messages());
    	
    }
    
    private ChatCompletionCreateParams userAskWithHistorial(String pregunta, OpenAIClient client, ChatCompletionCreateParams paramsInput) {
    	
    	LOGGER.info(() -> pregunta);
    	
    	ChatCompletionCreateParams params = paramsInput.toBuilder()
			.addUserMessage(pregunta)
			.build();
    	
    	ChatCompletion cc = client.chat().completions().create(params);

    	ChatCompletionMessage message = cc.choices().get(0).message();
    	
    	// respuesta
    	LOGGER.info(() -> message.content().get() );

    	// se añade respuesta al historial
       	return params.toBuilder()
    			.addMessage(message)
				.build();
    }
    
    
    @Test
    public void crearHistorialMensajesTest() {
    	
    	ChatCompletionAssistantMessageParam assistant = ChatCompletionAssistantMessageParam.builder()
				.content("París es la capital de Francia")
				.build();
    	
    	ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
    			.model(ChatModel.GPT_4O_MINI)
    			.addSystemMessage("Eres un profesor de geografía en una clase de 5º de primaria")
    			.addUserMessage("¿Cuál es la capital de Francia?")
    			.addMessage(assistant)
    			.addSystemMessage("Ahora eres un profesor de matemáticas")
    			.addDeveloperMessage("no hables de geografía") // ¿?
    			.addUserMessage("¿Cuato son 2+2?")
    			.build();
    	
		printMessages(params.messages());
    	
    	
    }
    
    private void printMessages(List<ChatCompletionMessageParam> messages) {
    	LOGGER.info(() -> "----------- PRINT MESSAGES -----------------");
    	messages.stream().forEach( m -> {
			LOGGER.info(() -> {
				if (m.isAssistant()) {
					return "ASSISTANT\t->\t" + m.assistant().get().content().get().asText();
				} else if (m.isUser()) {
					return "USER\t->\t" + m.user().get().content().asText();
				} else if (m.isSystem()) {
					return "SYSTEM\t->\t" + m.system().get().content().asText();
				} else if (m.isFunction()) {
					return "FUNCTION\t->\t" + m.function().get().name();
				} else if (m.isDeveloper()) {
					return "DEVELOPER\t->\t" + m.developer().get().content().asText();
				} else if (m.isTool()) {
					return "TOOL\t->\t" + m.tool().get().content().asText();
				}
				return "otro";
			});
		});
    	LOGGER.info(() -> "------------------------------------");
    }
    
    
}
