package com.nebur.openai_client;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonValue;
import com.openai.models.ChatModel;
import com.openai.models.ResponseFormatJsonSchema;
import com.openai.models.ResponseFormatJsonSchema.JsonSchema;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class StructuredTest {
	
	private static final Logger LOGGER = LogManager.getLogger(StructuredTest.class);
	@Test
	public void getJsonEmpleadosTest() {
	
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        // TODO: Update this once we support extracting JSON schemas from Java classes
        JsonSchema.Schema schema = JsonSchema.Schema.builder()
                .putAdditionalProperty("type", JsonValue.from("object"))
                .putAdditionalProperty(
                        "properties", JsonValue.from(Map.of("empleados", Map.of("items", Map.of("type", "string")))))
                .build();
        
        ChatCompletionCreateParams createParams = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4O_MINI)
                .maxCompletionTokens(2048)
                .responseFormat(ResponseFormatJsonSchema.builder()
                        .jsonSchema(JsonSchema.builder()
                                .name("employee-list")
                                .schema(schema)
                                .build())
                        .build())
                .addUserMessage("Mario, Juan, Pedro y Luis son empleados de la empresa")
                .build();

        client.chat().completions().create(createParams).choices().stream()
                .flatMap(choice -> choice.message().content().stream())
                .forEach( k -> LOGGER.info(() -> k));
	}
	
}
