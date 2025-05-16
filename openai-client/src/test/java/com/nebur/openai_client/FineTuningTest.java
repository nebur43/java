package com.nebur.openai_client;

import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.files.FileCreateParams;
import com.openai.models.files.FileDeleteParams;
import com.openai.models.files.FileDeleted;
import com.openai.models.files.FileObject;
import com.openai.models.files.FilePurpose;
import com.openai.models.finetuning.jobs.FineTuningJob;
import com.openai.models.finetuning.jobs.JobCreateParams;
import com.openai.models.finetuning.jobs.JobCreateParams.Model;
import com.openai.models.finetuning.jobs.JobRetrieveParams;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

public class FineTuningTest {

	private static final Logger LOGGER = LogManager.getLogger(FineTuningTest.class);
	
	@Test
	public void fileUpload() {
		
		LOGGER.info(() -> "Uploading file");
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();
    	
    	FileCreateParams params = FileCreateParams.builder()
    		    .purpose(FilePurpose.FINE_TUNE)
    		    .file(Paths.get("src/test/resources/einstein.jsonl"))
    		    .build();
		FileObject fileObject = client.files().create(params);
		
		LOGGER.info(() -> "FileObject: " + fileObject);		
		
	}
	
	@Test
	public void crearTrabajoFineTuning() {
		LOGGER.info(() -> "Creating job");
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		
		JobCreateParams jobParams = JobCreateParams.builder()
				.trainingFile("file-54pktB4xabfUZkz95eghzn")
				.model(Model.GPT_3_5_TURBO)
				.build();

		FineTuningJob job = client.fineTuning().jobs().create(
	            jobParams
	        );

		LOGGER.info(() -> "Job: " + job.id());
		
	}
	
	@Test
	public void listStates() {
		
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		
		for (FineTuningJob job : client.fineTuning().jobs().list().autoPager()) {
		    LOGGER.info(() -> "job: "+ job);
		}
		
	}
	
	@Test
	public void listOneJob() {
		
		LOGGER.info(() -> "Listing one job");
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		
		FineTuningJob job= client.fineTuning().jobs().retrieve(JobRetrieveParams.builder()
				.fineTuningJobId("ftjob-oklSVjGv69qZOdDDBqAU7q9v")
				.build());
		
		LOGGER.info(() -> "Job: " + job.status());
		
	}
	
	@Test
	public void fileDeleteFile() {
		
		LOGGER.info(() -> "Deleting file");
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();
    	
    	FileDeleteParams params = FileDeleteParams.builder()
    			.fileId("file-NfdXWY2paCiJHaognkRvsJ")
    		    .build();
		FileDeleted fileDeleted = client.files().delete(params);
		
		LOGGER.info(() -> "FileObject: " + fileDeleted);		
		
	}
	
	
	@Test
    public void useMyModelTest() {
    	LOGGER.info(() -> "using my model");
    	// Configures using the enviroment `OPENAI_API_KEY`, `OPENAI_ORG_ID`, `OPENAI_PROJECT_ID` and `OPENAI_BASE_URL` environment variables
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ResponseCreateParams params = ResponseCreateParams.builder()
    			.model("ft:gpt-3.5-turbo-0125:neburapps::BSQ8YY8E")
    			.instructions("Responde solo a preguntas sobre einstein. Si el usuario pregunta algo que no sea sobre einstein, responde que no sabes nada de eso")
    	        .input("Cuéntame todo lo que sepas sobre einstein")
    	        .build();
    	Response response = client.responses().create(params);
    	   	
    	LOGGER.info(() -> "Response: " + response );
    	LOGGER.info(() -> "Response.content: " + response.output().get(0).asMessage().content().get(0).outputText().get().text() );
    	
    }
	
    @Test
    public void useMyModelChatCompletionsTest() {
    	LOGGER.info(() -> "using my model chat completions");
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
    			.model("ft:gpt-3.5-turbo-0125:neburapps::BSQ8YY8E")
    			.addSystemMessage("Responde solo a preguntas sobre einstein. No hables de nada mas")
    			.addUserMessage("Cuantos años tiene el sol")
    			.build();
    	
    	ChatCompletion cc = client.chat().completions().create(params);
    	   	
    	LOGGER.info(() -> "ChatCompletion: " + cc );
    	LOGGER.info(() -> "ChatCompletion.content: " + cc.choices().get(0).message().content().get() );
    }

	
}
