package com.nebur.pinecone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openapitools.control.client.model.DeletionProtection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.TranslateException;
import ai.djl.translate.TranslatorContext;
import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;
import io.pinecone.unsigned_indices_model.VectorWithUnsignedIndices;


public class SearchTest {

	private static final Logger log = LoggerFactory.getLogger(SearchTest.class);
	
	@Test
	public void kk() {
		log.debug("empezamos");
		Pinecone pc = new Pinecone.Builder("e2d4c810-0d8c-4c87-8f61-eea81322d81e").build();
		
		String indexName = "text-similarity";
        pc.createServerlessIndex(indexName, "cosine", 128, "aws", "eu-south-2", DeletionProtection.DISABLED);
        //Add to the main function:
        Index index = pc.getIndexConnection(indexName);
        
     // Modelo preentrenado para convertir textos en vectores
        HuggingFaceTokenizer tokenizer = HuggingFaceTokenizer.newInstance("sentence-transformers/all-MiniLM-L6-v2");
        
     // Funcin para generar vectores a partir de textos
        NDManager manager = NDManager.newBaseManager();

        String[] texts = {
                "Este es un ejemplo de texto.",
                "Otro texto similar en contenido.",
                "Un texto completamente diferente."
        };

        // Indexar los vectores en Pinecone
        for (int i = 0; i < texts.length; i++) {
            List<Float> vector = encodeText(tokenizer, manager, texts[i]);
            VectorWithUnsignedIndices vectorWithIndices = new VectorWithUnsignedIndices("vec_" + i, vector);
            index.upsert(List.of(vectorWithIndices), "namespace");  // Asegúrate de reemplazar "namespace" con el valor adecuado
        }

        // Buscar textos similares
        String queryText = "Buscando algo relacionado con el contenido.";
        List<Float> queryVector = encodeText(tokenizer, manager, queryText);
        List<Map<String, Object>> result = index.query(queryVector, 2);  // Top 2 resultados
        System.out.println(result);
		
	}
	
    // Funcin para convertir texto en vectores
    private List<Float> encodeText(HuggingFaceTokenizer tokenizer, NDManager manager, String text) throws ModelException, TranslateException, IOException {
        long[] tokens = tokenizer.encode(text).getIds();

        try (Model model = Model.newInstance("sentence-transformers/all-MiniLM-L6-v2")) {
            TranslatorContext ctx = model.newPredictor().newTranslator().createContext();
            NDArray tokenArray = manager.create(tokens);
            NDArray output = ctx.getNDArray(tokenArray);
            float[] r = output.mean(new int[]{1}).toFloatArray();  // Vector resultante
            List<Float> floatList = new ArrayList<>();

            // Convertir float[] a List<Float>
            for (float f : r) {
                floatList.add(f);
            }
            return floatList;
        }
    }
	
}
