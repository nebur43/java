package es.ruben.pruebas.cassandraHelloWorld;

import java.util.UUID;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;

import io.netty.util.internal.StringUtil;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private CassandraConnector connector;
	private Session session;
	
	public void conectar() {
		System.out.println("Conectando a casandra");
		connector = new CassandraConnector();
        connector.connect("localhost", 9042);
        session = connector.getSession();
	}
	
	public void desconectar() {
		System.out.println("desconectando de casandra ");
		connector.close();
	}
	
	public void createKeyspace(String keyspaceName, String replicationStrategy, int replicationFactor) {
		
		System.out.println("Creando keyspace: " + keyspaceName);
		
			  StringBuilder sb = 
			    new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
			      .append(keyspaceName).append(" WITH replication = {")
			      .append("'class':'").append(replicationStrategy)
			      .append("','replication_factor':").append(replicationFactor)
			      .append("};");
			        
			    String query = sb.toString();
			    session.execute(query);
	}
	
	public void listarKeySpaces() {
		System.out.println("listando keyspaces");
		 ResultSet result = session.execute("SELECT * FROM system_schema.keyspaces;");
		 result.all().stream().forEach( r -> System.out.println(" keyspace: " +r.getString(0) ) );
	}
	
	public void deleteKeySpace(String keySpaceName) {
		System.out.println("borrando keyspace " + keySpaceName);
		session.execute("DROP KEYSPACE "+ keySpaceName);
	}
	
	public void createTableBooks(String keyspace) {
		System.out.println("creando keyspace " + keyspace );
	    String query = "CREATE TABLE IF NOT EXISTS "+keyspace+".books"
	    		+ "("
	    		+ "id uuid PRIMARY KEY, "
	    		+ "title text,"
	    		+ "subject text);";
	    session.execute(query);
	}
	
	public void createTableBooksByTitle(String keyspace) {
		System.out.println("creando tabla " + keyspace + ".booksByTitle");
	    String query = "CREATE TABLE IF NOT EXISTS "+keyspace+".booksByTitle"
	    		+ "("
	    		+ "id uuid, "
	    		+ "title text,"
	    		+ "PRIMARY KEY (title, id));";
	    session.execute(query);
	}
	
	public void listarTabla(String keyspace, String table) {
		System.out.println("listando tabla " +keyspace+"."+ table);
	    String query = "select * from "+keyspace+"."+ table +";";
	    ResultSet result = session.execute(query);
	    System.out.println();
	    result.getColumnDefinitions().asList().forEach( c -> System.out.print(" | " +c.getName() ));
	    System.out.println();
	    result.forEach(r -> System.out.println( r.getUUID(0) + "\t" + r.getString(1 ) +"\t"+ r.getString(2) ));
	    System.out.println();
	}
	
	public void insertData(String kespace, String table, UUID id, String title) {
		System.out.println("insertando id: " + id + "\ttitle: " + title);
	    session.execute("INSERT INTO "+kespace+ "."+ table 
	    		+ "(id, title) "
	    		+ "VALUES (" + id + ", '"+ title + "');");
	}
	
	public void insertBookBatch(String kespace, UUID id, String titulo, String subject) {
		System.out.println("insertando batch id: " + id + "\ttitle: " + titulo);
	    String query = "BEGIN BATCH "
	    		+ "INSERT INTO "+kespace+".books (id, title, subject) "
	    		+ "VALUES ( "+ id + ", '"+titulo+"', '" + subject +"'); "
				+ "INSERT INTO "+kespace+".booksByTitle (id, title) "
				+ "VALUES ( "+ id + ", '"+titulo+"');"
				+ "APPLY BATCH;";
	    session.execute(query);
	}
	
	public void deleteTable(String keyspace, String table) {
	    session.execute("DROP TABLE IF EXISTS "+ keyspace + "." + table);
	}
	
	// cqlsh
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        App app = new App();
        try {
        	app.conectar();
            
//          app.deleteKeySpace("keyspaceTest");
//          app.createKeyspace("test", "SimpleStrategy", 1);
//          app.listarKeySpaces();
          
//          app.createTableBooks("test");
//          app.createTableBooksByTitle("test");
          app.insertBookBatch("test",UUIDs.timeBased(), "el señor de los anillos", "fantasia");
          app.insertBookBatch("test",UUIDs.timeBased(), "peppa pig", "infantil");
//          app.insertData("keyspaceTest","libros", UUIDs.timeBased(), "el señor de los anillos");
//          app.insertData("keyspaceTest","libros", UUIDs.timeBased(), "la historia interminable");
//          app.listarTabla("keyspaceTest","libros");
        } finally {
        	app.desconectar();
		}
        
        
        
        
        
    }
}
