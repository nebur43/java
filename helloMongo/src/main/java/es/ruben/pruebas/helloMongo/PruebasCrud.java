package es.ruben.pruebas.helloMongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;



/**
 * Hello world!
 *
 * mongodb://docker:mongopw@localhost:49153
 *
 */
public class PruebasCrud 
{
	
	private MongoClient mongoClient;
	
	public PruebasCrud(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	 // collection (=tabla)
	public void crearCollection(MongoDatabase database, String collection) {

      database.createCollection(collection, new CreateCollectionOptions());
	}
	
	public void listarDataBases() {
		List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
        databases.forEach(db -> System.out.println(db.toJson()));
	}
	
	public void listarCollections(MongoDatabase database) {
		Iterator<Document> it = database.listCollections().iterator();
        while (it.hasNext()) {
        	System.out.println("colletion: " + it.next());
        	
        }
	}
	
	public void insertIntoCollection(MongoDatabase database, String collectionName, Document doc) {
		MongoCollection<Document> collection=  database.getCollection(collectionName);
		collection.insertOne(doc);
	}
	
	public void find(MongoDatabase database, String collectionName, Document doc) {
		MongoCollection<Document> collection=  database.getCollection(collectionName);
		
		collection.find(doc).forEach( (Consumer<Document>) c ->   System.out.println("encontrado: " + c));
	}
	
	public void update(MongoDatabase database, String collectionName, Document docQuery, Document docUpdate) {
		// no funciona
		MongoCollection<Document> collection=  database.getCollection(collectionName);
		
		collection.findOneAndUpdate(docQuery, docUpdate);
	}
	
	public void delete(MongoDatabase database, String collectionName, Document docQuery) {
		MongoCollection<Document> collection=  database.getCollection(collectionName);
		
		long borrado = collection.deleteMany(docQuery).getDeletedCount();
		System.out.println("documentos borrados en " + collectionName + " = " + borrado );
	}
	
	
    public static void main( String[] args )
    {
    	
//        String connectionString = System.getProperty("mongodb.uri");
        try (MongoClient mongoClient = MongoClients.create("mongodb://root:example@localhost:27017")) {
        	
        	PruebasCrud pruebas = new PruebasCrud(mongoClient);
        	MongoDatabase database = mongoClient.getDatabase("myMongoDb");

        	pruebas.crearCollection(database, "perros");
        	pruebas.listarDataBases();
        	pruebas.listarCollections(database);
            
            Document doc = new Document();
            doc.put("raza", "coquer");
            doc.put("nombre", "paco");
            doc.put("edad", 7);
            doc.put("hijos", Arrays.asList(
            		new Document().append("nombre", "dana").append("edad", 1),
            		new Document().append("nombre", "scally").append("edad", 3),
            		new Document().append("nombre", "malder").append("edad", 2)
            		) );
            pruebas.insertIntoCollection(database, "perros", doc);
            
        	
//        	Document queryDoc = new Document();
//        	queryDoc.put("nombre", "coco");
////    		pruebas.find(database, "perros", queryDoc);
//        	Document updateDoc = new Document();
//        	updateDoc.put("raza", "coco paris");
//        	pruebas.update(database, "perros", queryDoc, updateDoc);
//        	pruebas.delete(database, "perros", queryDoc);
        	
        	
        }
    	
    }
}
