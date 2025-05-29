package com.nebur.openai_client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.nebur.openai_client.util.JsonUtil;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.RequestOptions;
import com.openai.models.ChatModel;
import com.openai.models.ComparisonFilter;
import com.openai.models.ComparisonFilter.Type;
import com.openai.models.embeddings.CreateEmbeddingResponse;
import com.openai.models.embeddings.EmbeddingCreateParams;
import com.openai.models.embeddings.EmbeddingCreateParams.EncodingFormat;
import com.openai.models.embeddings.EmbeddingModel;
import com.openai.models.responses.FileSearchTool;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseIncludable;
import com.openai.models.vectorstores.VectorStoreSearchPage;
import com.openai.models.vectorstores.VectorStoreSearchParams;

public class FileSearch {

	private static final Logger LOGGER = LogManager.getLogger(FileSearch.class);
	
	private static String vectorStoreId = "vs_682eec59578c8191ab35e2aa4e1e9590"; 
	
    @Test
    public void responseFileSearchTest() {
    	LOGGER.info("Running responseFileSearchTest");
    	// Configures using the enviroment `OPENAI_API_KEY`, `OPENAI_ORG_ID`, `OPENAI_PROJECT_ID` and `OPENAI_BASE_URL` environment variables
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ResponseCreateParams params = ResponseCreateParams.builder()
    			.instructions("Eres un profesor que está hablando con los padres de un alumno")
    	        .input("¿qué llevar en la playa de barros?")
    	        .model(ChatModel.GPT_4O_MINI)
    	        .addTool(FileSearchTool.builder()
    	        		.addVectorStoreId(vectorStoreId)
    	        		.build())
    	        .build();
    	Response response = client.responses().create(params);
    	   	
    	LOGGER.info("Response: {}", JsonUtil.parse(response) );
    	LOGGER.info("Response.content: {}", response.output().get(1).asMessage().content().get(0).outputText().get().text() );
    	
    }
    
    /**
     * incluimos en la busqueda los chunk de los archivos que se han utilizado para obtener la respuesta
     * como contexto del prompt
     */
    @Test
    public void responseFileSearchIncludeTest() {
    	LOGGER.info("Running responseFileSearchIncludeTest");
    	// Configures using the enviroment `OPENAI_API_KEY`, `OPENAI_ORG_ID`, `OPENAI_PROJECT_ID` and `OPENAI_BASE_URL` environment variables
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ResponseCreateParams params = ResponseCreateParams.builder()
    			.instructions("Eres un profesor que está hablando con los padres de un alumno")
    	        .input("¿qué llevar en la playa de barros?")
    	        .model(ChatModel.GPT_4O_MINI)
    	        .addTool(FileSearchTool.builder()
    	        		.addVectorStoreId(vectorStoreId)
    	        		.build())
    	        .addInclude(ResponseIncludable.FILE_SEARCH_CALL_RESULTS)
    	        .build();
    	Response response = client.responses().create(params);
    	   	
    	LOGGER.info("Response: {}", JsonUtil.parse(response) );
    	LOGGER.info("Response.content: {}", response.output().get(1).asMessage().content().get(0).outputText().get().text() );
    	
    }

    /**
     * filtramos la busqueda para tipos de archivo blog
     */
    @Test
    public void responseFileSearchFilterFilesTest() {
    	LOGGER.info("Running responseFileSearchFilterFilesTest");
    	// Configures using the enviroment `OPENAI_API_KEY`, `OPENAI_ORG_ID`, `OPENAI_PROJECT_ID` and `OPENAI_BASE_URL` environment variables
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	ResponseCreateParams params = ResponseCreateParams.builder()
    			.instructions("Eres un profesor que está hablando con los padres de un alumno. Si la información no está en el archivo, responde que no sabes nada de eso. No alucines información")
    	        .input("¿qué llevar en la playa de barros?")
    	        .model(ChatModel.GPT_4O_MINI)
    	        .addTool(FileSearchTool.builder()
    	        		.addVectorStoreId(vectorStoreId)
    	        		.filters(ComparisonFilter.builder()
    	        				.key("type")
    	        				.type(Type.EQ)
    	        				.value("blog")
    	        				.build())
    	        		.build())
    	        .build();
    	Response response = client.responses().create(params);
    	   	
    	LOGGER.info("Response: {}", JsonUtil.parse(response) );
    	LOGGER.info("Response.content: {}", response.output().get(1).asMessage().content().get(0).outputText().get().text() );
    	
    }
	
    
}
