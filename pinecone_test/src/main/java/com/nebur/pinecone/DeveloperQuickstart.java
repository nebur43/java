package com.nebur.pinecone;

import io.pinecone.clients.Pinecone;
import io.pinecone.clients.Index;
import io.pinecone.proto.DescribeIndexStatsResponse;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import org.openapitools.control.client.model.DeletionProtection;

import java.util.Arrays;
import java.util.List;

public class DeveloperQuickstart {
    public static void main(String[] args) throws InterruptedException {
        Pinecone pc = new Pinecone.Builder("e2d4c810-0d8c-4c87-8f61-eea81322d81e").build();
        
     // Add to the main function:
        String indexName = "quickstart3";
        pc.createServerlessIndex(indexName, "cosine", 2, "aws", "us-east-1", DeletionProtection.DISABLED);
        
      //Add to the main function:
        Index index = pc.getIndexConnection(indexName);

        // Check if index is ready
        while(!pc.describeIndex(indexName).getStatus().getReady()) {
            Thread.sleep(3000);
        }

        List<Float> values1 = Arrays.asList(1.0f, 1.5f);
        List<Float> values2 = Arrays.asList(2.0f, 1.0f);
        List<Float> values3 = Arrays.asList(0.1f, 3.0f);
        List<Float> values4 = Arrays.asList(1.0f, -2.5f);
        List<Float> values5 = Arrays.asList(3.0f, -2.0f);
        List<Float> values6 = Arrays.asList(0.5f, -1.5f);

        index.upsert("vec1", values1, "ns1");
        index.upsert("vec2", values2, "ns1");
        index.upsert("vec3", values3, "ns1");
        index.upsert("vec1", values4, "ns2");
        index.upsert("vec2", values5, "ns2");
        index.upsert("vec3", values6, "ns2");
        
		 // Wait for the upserted vectors to be indexed
		 Thread.sleep(10000);
		
		 DescribeIndexStatsResponse indexStatsResponse = index.describeIndexStats(null);
		 System.out.println(indexStatsResponse);

		 
		 List<Float> queryVector1 = Arrays.asList(1.0f, 1.5f);
		 List<Float> queryVector2 = Arrays.asList(1.0f, -2.5f);

		 QueryResponseWithUnsignedIndices queryResponse1 = index.query(3, queryVector1, null, null, null, "ns1", null, false, true);
		 QueryResponseWithUnsignedIndices queryResponse2 = index.query(3, queryVector2, null, null, null, "ns2", null, false, true);

		 System.out.println(queryResponse1);
		 System.out.println(queryResponse2);
    }
}