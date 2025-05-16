package com.nebur.openai_client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientAsync;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.client.okhttp.OpenAIOkHttpClientAsync;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class AsyncTest {

	private static final Logger LOGGER = LogManager.getLogger(AsyncTest.class);	
	@Test
	public void asyncCall() throws InterruptedException, ExecutionException {
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();

		ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
		    .addUserMessage("Dime un trabalenguas")
		    .model(ChatModel.GPT_4O_MINI)
		    .build();
		CompletableFuture<ChatCompletion> chatCompletion = client.async().chat().completions().create(params);

		// mostramos en el log el resultado
		chatCompletion.thenAccept( cc ->  LOGGER.info(()-> cc.choices().get(0).message().content().get()) );
		
		LOGGER.info(() -> "fin test");
		
		// esperamos finalizacion de la llamada
		chatCompletion.join();
	}
	
	@Test
	public void asyncClient() {
		OpenAIClientAsync client = OpenAIOkHttpClientAsync.fromEnv();

		ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
		    .addUserMessage("Dime una frase capicua")
		    .model(ChatModel.GPT_4O_MINI)
		    .build();
		CompletableFuture<ChatCompletion> chatCompletion = client.chat().completions().create(params);

		// mostramos en el log el resultado
		chatCompletion.thenAccept( cc ->  LOGGER.info(()-> cc.choices().get(0).message().content().get()) );
		
		LOGGER.info(() -> "fin test");
		
		// esperamos finalizacion de la llamada
		chatCompletion.join();
	}
}
