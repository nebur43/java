package com.nebur.openai_client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.nebur.openai_client.util.JsonUtil;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.RequestOptions;
import com.openai.models.embeddings.CreateEmbeddingResponse;
import com.openai.models.embeddings.EmbeddingCreateParams;
import com.openai.models.embeddings.EmbeddingModel;
import com.openai.models.embeddings.EmbeddingCreateParams.EncodingFormat;
import com.openai.models.vectorstores.VectorStoreSearchPage;
import com.openai.models.vectorstores.VectorStoreSearchParams;

public class EmbeddignsTest {
	
	private static final Logger LOGGER = LogManager.getLogger(EmbeddignsTest.class);
	
	private static String vectorStoreId = "vs_682eec59578c8191ab35e2aa4e1e9590";

    /**
     * obtenemos un embedding de un texto (vector de 1536 dimensiones)
     */
    @Test
    public void embeddingsTest() {
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    	EmbeddingCreateParams params = EmbeddingCreateParams.builder()
    	        .input("¿qué llevar en la playa de barros?")
    	        .model(EmbeddingModel.TEXT_EMBEDDING_3_SMALL)
//    	        .dimensions(500)
    	        .encodingFormat(EncodingFormat.FLOAT)
    	        .build();
    	CreateEmbeddingResponse response = client.embeddings().create(params);
    	   	
    	LOGGER.info("Response: {}", JsonUtil.parse(response) );
//    	LOGGER.info("Response.content: {}", response.);
    }
    
    @Test
    public void vectorStoreSearchTest() {
    	
    	VectorStoreSearchParams params=  VectorStoreSearchParams.builder()
    			.vectorStoreId(vectorStoreId)
    			.query("La salida de madrid para ir a la playa de barros a qué hora es?")
    			.build();
    	
    	OpenAIClient client = OpenAIOkHttpClient.fromEnv();
    	VectorStoreSearchPage searchPage = client.vectorStores().search(
    			params,
    			RequestOptions.none());
    	
    	LOGGER.info("searchPage: {}", JsonUtil.parse(searchPage) );
    	
    	searchPage.data().forEach( item -> {
			LOGGER.info("item: {}", JsonUtil.prettyPrint(item) );
		});
    	
    }
	
}
