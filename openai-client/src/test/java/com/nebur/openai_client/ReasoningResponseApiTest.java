package com.nebur.openai_client;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.nebur.openai_client.util.JsonUtil;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.Reasoning;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

public class ReasoningResponseApiTest {
	
	private static final Logger LOGGER = LogManager.getLogger(ReasoningResponseApiTest.class);
	
    @Test
    public void responsesApiTest() {
    	LOGGER.info("Running responsesApiTest");
    	// Configures using the enviroment `OPENAI_API_KEY`, `OPENAI_ORG_ID`, `OPENAI_PROJECT_ID` and `OPENAI_BASE_URL` environment variables
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ResponseCreateParams params = ResponseCreateParams.builder()
    			.instructions("Eres un experto en física")
    	        .input("Qué pasa si cojo una botella con agua liquida y meto una pequeña pelota de plástico dentro. "
    	        		+ "Cierro la botella y la meto en el congelador boca abajo. "
    	        		+ "Después de unas horas, saco la botella del congelador y la pongo boca arriba. "
    	        		+ "¿Dónde está la pelota de plástico?")
    	        .model(ChatModel.O4_MINI)
    	        .reasoning(Reasoning.builder()
    	        		.effort(ReasoningEffort.MEDIUM) // LOW, MEDIUM, HIGH
    	        		.build())
//    	        .maxOutputTokens(500)
//    	        .store(true) // almacena en el contexto la cadena de razonamiento
    	        .build();
    	
    	Date start = new Date();
    	Response response = client.responses().create(params);
    	   	
    	LOGGER.info("Response: {}", JsonUtil.parse(response) );
    	
    	response.output().stream().filter(x -> x.isMessage()).map(x->x.asMessage()).forEach(message -> {
    		message.content().stream().filter(x -> x.isOutputText()).map(x->x.asOutputText()).forEach(outputText -> {
				LOGGER.info("Response.content: {}", outputText.text() );
			});
		});
    	response.usage().ifPresent(usage -> {
			LOGGER.info("Response.usage: {}", JsonUtil.prettyPrint(usage) );
		});
    	LOGGER.info("Response took: {} ms", new Date().getTime() - start.getTime());
    }

}
