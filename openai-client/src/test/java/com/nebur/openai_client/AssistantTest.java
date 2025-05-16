package com.nebur.openai_client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.beta.assistants.Assistant;
import com.openai.models.beta.assistants.AssistantCreateParams;
import com.openai.models.beta.assistants.AssistantDeleteParams;
import com.openai.models.beta.assistants.AssistantDeleted;
import com.openai.models.beta.assistants.AssistantRetrieveParams;
import com.openai.models.beta.threads.Thread;
import com.openai.models.beta.threads.ThreadDeleteParams;
import com.openai.models.beta.threads.messages.MessageCreateParams;
import com.openai.models.beta.threads.messages.MessageListPage;
import com.openai.models.beta.threads.messages.MessageListParams;
import com.openai.models.beta.threads.runs.Run;
import com.openai.models.beta.threads.runs.RunCreateParams;
import com.openai.models.beta.threads.runs.RunRetrieveParams;
import com.openai.models.beta.threads.runs.RunStatus;

public class AssistantTest {
	
	private static final Logger LOGGER = LogManager.getLogger(AssistantTest.class);
	
	private static final String ASSISTANT_ID = "asst_DG26fk92XPf8ehq7AbT0Z6oj";
	private static final String THREAD_ID = "thread_XaMNbEATTT6xGK8PWUlwwMh3";
	
	@Test
	public void createAssistantTest() throws Exception {
		// no tiene coste
        // Configures using one of:
        // - The `OPENAI_API_KEY` environment variable
        // - The `OPENAI_BASE_URL` and `AZURE_OPENAI_KEY` environment variables
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        Assistant assistant = client.beta()
                .assistants()
                .create(AssistantCreateParams.builder()
                        .name("Math Tutor")
                        .instructions("You are a personal math tutor. Write and run code to answer math questions.")
                        // TODO: Update this example once we support `addCodeInterpreterTool()` or similar.
//                        .addTool(CodeInterpreterTool.builder().build()) // esto es caro
                        .model(ChatModel.GPT_4O_MINI)
                        .build());
        
        LOGGER.info(() -> "Assistant created: " + assistant);
        LOGGER.info(() -> "Assistant id: " + assistant.id());
        
	}
	
	@Test
	public void createThread() {
		// no tiene coste
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		Thread thread = client.beta().threads().create();
		LOGGER.info(() -> "Thread created: " + thread.id());
	}
	
	@Test
	public void messageAssitant() throws InterruptedException {
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        Assistant assistant = client.beta()
                .assistants().retrieve(AssistantRetrieveParams.builder()
						.assistantId(ASSISTANT_ID)
						.build());
        
        // crear un mensaje nuevo en el hilo
        client.beta()
                .threads()
                .messages()
                .create(MessageCreateParams.builder()
                        .threadId(THREAD_ID)
                        .role(MessageCreateParams.Role.USER)
//                        .content("¿cúal es la capital con más habitantes de Europa?")
                        .content("Y cual es la renta per cápita?")
                        .build());

        // ejecutar el hilo con una instrucción determinada (aquí se cobra el uso de la API)
        Run run = client.beta()
                .threads()
                .runs()
                .create(RunCreateParams.builder()
                        .threadId(THREAD_ID)
                        .assistantId(assistant.id())
                        .instructions("Please address the user as Jane Doe. The user has a premium account.")
                        .build());
        while (run.status().equals(RunStatus.QUEUED) || run.status().equals(RunStatus.IN_PROGRESS)) {
            System.out.println("Polling run...["+run.status()+"]");
            java.lang.Thread.sleep(500);
            run = client.beta()
                    .threads()
                    .runs()
                    .retrieve(RunRetrieveParams.builder()
                            .threadId(THREAD_ID)
                            .runId(run.id())
                            .build());
        }
        System.out.println("Run completed with status: " + run.status() + "\n");

        if (!run.status().equals(RunStatus.COMPLETED)) {
            return;
        }

        // listar los mensajes del hilo y sus respuestas
        MessageListPage page = client.beta()
                .threads()
                .messages()
                .list(MessageListParams.builder()
                        .threadId(THREAD_ID)
                        .order(MessageListParams.Order.ASC)
                        .build());
        
        page.autoPager().stream().forEach(currentMessage -> {
            System.out.println(currentMessage.role().toString().toUpperCase());
            currentMessage.content().stream()
                    .flatMap(content -> content.text().stream())
                    .forEach(textBlock -> System.out.println(textBlock.text().value()));
            System.out.println();
        });
        
	}
	
	@Test
	public void deleteAssistant() {
		
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();

		
		AssistantDeleted assistantDeleted = client.beta()
                .assistants()
                .delete(AssistantDeleteParams.builder()
                        .assistantId(ASSISTANT_ID)
                        .build());
        LOGGER.info(() -> "Assistant deleted: " + assistantDeleted.deleted());
        
	}
	
	@Test
	public void deleteThread() {
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		client.beta().threads().delete(ThreadDeleteParams.builder()
				.threadId(THREAD_ID)
				.build());
		LOGGER.info(() -> "Thread deleted: " + THREAD_ID);
		
	}
	
}
