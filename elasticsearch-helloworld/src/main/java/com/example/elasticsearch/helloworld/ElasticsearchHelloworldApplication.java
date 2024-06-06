package com.example.elasticsearch.helloworld;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.index.Settings;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import com.example.elasticsearch.helloworld.dao.User;
import com.example.elasticsearch.helloworld.service.IUserService;

@SpringBootApplication
public class ElasticsearchHelloworldApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(ElasticsearchHelloworldApplication.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchHelloworldApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("inicio");
		
//		crearIndice("kk");
		
		for ( int i = 0 ; i< 10 ; i++) {
			User user = User.builder()
					.name("elisa-"+i)
					.surname(UUID.randomUUID().toString())
					.birth(new Date())
					.sex(User.Sex.FEMALE.name())
					.child(2)
					.build();
			userService.createUser(user);
		}
			
		
		
		userService.getUserByNameSpecial("3").forEach(u -> LOG.debug("usuario: " + u.getId() + " nombre: "+ u.getName() + " apellido:" + u.getSurname()));
		
		userService.getByNameQuery("6").forEach(u -> LOG.debug("usuario: " + u.getId() + " nombre: "+ u.getName() + " apellido:" + u.getSurname()));
		
		
//		ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").build();
//		
//		try ( RestHighLevelClient client = RestClients.create(clientConfiguration).rest() ) {
//			
//			String jsonObject = "{\"age\":10,\"dateOfBirth\":1471466076564,\"fullName\":\"John Doe\"}";
//		  IndexRequest request = new IndexRequest("people");
//		  request.source(jsonObject, XContentType.JSON);
//		  
//		  IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//		  String index = response.getIndex();
//		  long version = response.getVersion();
//		  
//		  LOG.info("Respuesta: {} - index: {} - version: {}", response.getResult() ,index, version);
//			
//		}
		
		
		LOG.info("fin");
	}

	
	public void crearIndice(String name) {
		
		IndexCoordinates indexCoordinates = IndexCoordinates.of(name);
		IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(indexCoordinates);
		
        String mappingJson = "{\"properties\": {\n"
        		+ "            \"argumento\": {\n"
        		+ "                \"type\": \"text\",\n"
        		+ "                \"analyzer\": \"rebuilt_spanish\"\n"
        		+ "            },\n"
        		+ "            \"votosAFavor\": {\n"
        		+ "                \"type\": \"integer\",\n"
        		+ "                \"index\": false,\n"
        		+ "                \"null_value\": 0\n"
        		+ "            }\n"
        		+ "        }"
        		+ " } ";
		
        Document mapping = Document.parse(mappingJson);
        
		String indexSettings = "{        \"index\": {\n"
				+ "            \"number_of_shards\": 1,\n"
				+ "            \"number_of_replicas\": 1\n"
				+ "        },\n"
				+ "        \"analysis\": {\n"
				+ "            \"filter\": {\n"
				+ "                \"spanish_stop\": {\n"
				+ "                    \"type\": \"stop\",\n"
				+ "                    \"stopwords\": \"_spanish_\"\n"
				+ "                },\n"
				+ "                \"spanish_stemmer\": {\n"
				+ "                    \"type\": \"stemmer\",\n"
				+ "                    \"language\": \"light_spanish\"\n"
				+ "                }\n"
				+ "            },\n"
				+ "            \"analyzer\": {\n"
				+ "                \"rebuilt_spanish\": {\n"
				+ "                    \"tokenizer\": \"standard\",\n"
				+ "                    \"filter\": [\n"
				+ "                        \"lowercase\",\n"
				+ "                        \"spanish_stop\",\n"
				+ "                        \"spanish_stemmer\"\n"
				+ "                    ]\n"
				+ "                }\n"
				+ "            }\n"
				+ "        }"
				+ " } ";

        
//        Map<String, Object> settings = JacksonUtil.fromString(indexSettings, new TypeReference<>() {});
        Settings settings = Settings.parse(indexSettings);

        indexOperations.create(settings, mapping);
        indexOperations.refresh(); //(Optional) refreshes the doc count
		
	}
	
	
//    @Bean
//    public boolean createTestIndex(RestHighLevelClient restHighLevelClient) throws Exception {
//        try {
//            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("hello-world");
//            restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT); // 1
//        } catch (Exception ignored) { }
//
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest("hello-world");
//        createIndexRequest.settings(
//            Settings.builder().put("index.number_of_shards", 1)
//                .put("index.number_of_replicas", 0));
//
//        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT); // 2
//        return true;
//    }
	
}
