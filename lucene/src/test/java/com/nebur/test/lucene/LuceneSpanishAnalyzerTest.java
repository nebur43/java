package com.nebur.test.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuceneSpanishAnalyzerTest {

	private static final Logger logger = LoggerFactory.getLogger(LuceneSpanishAnalyzerTest.class);
	
	private String indexPath="C:/tmp/lucene-es-index";
	
	@Test
	public void crearIndiceCustmoAnalyzer() throws IOException {
		logger.debug("creando indice");
		
		// Paso 1: Crear el analizador y el directorio en el sistema de archivos
        Analyzer analyzer = new SpanishAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        
        try (Directory index = FSDirectory.open(Paths.get(indexPath));
        		IndexWriter writer = new IndexWriter(index, config);) {
            // Paso 2: Indexar documentos
            indexDoc(writer, "1", "Lucene es una biblioteca de búsqueda de texto en Java.");
            indexDoc(writer, "2", "Lucene permite crear motores de búsqueda rápidos y eficientes.");
            indexDoc(writer, "3", "Puedes usar Lucene para indexar grandes cantidades de texto.");

        }
        logger.debug("indice creado");
	}
	
	@Test
	public void anadirTexto() throws IOException {
		logger.debug("actualizando indice");
		
		// Paso 1: Crear el analizador y el directorio en el sistema de archivos
        Analyzer analyzer = new SpanishAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        
        try (Directory index = FSDirectory.open(Paths.get(indexPath));
        		IndexWriter writer = new IndexWriter(index, config);) {
            // Paso 2: Indexar documentos
            indexDoc(writer, "4", "Elastisearch consume muchos más recursos.");
            indexDoc(writer, "5", "El niño está santando en la cama");

        }
        logger.debug("indice actualizado");
	}
	
    private void indexDoc(IndexWriter writer, String id, String content) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("id", id, Field.Store.YES));
        doc.add(new TextField("content", content, Field.Store.YES));
        writer.addDocument(doc);
    }
	
	@Test
	public void buscarEnIndice() throws ParseException, IOException {
		logger.debug("cargando indice");
		Analyzer analyzer = new SpanishAnalyzer();
		
		// Paso 3: Crear una consulta de búsqueda
		String[] busqueda= {"saltar"};
        Query query = getQuery(analyzer, busqueda);

  		 // Paso 4: Buscar en el índice
        try (Directory index = FSDirectory.open(Paths.get(indexPath));
        		DirectoryReader reader = DirectoryReader.open(index);) {
            IndexSearcher searcher = new IndexSearcher(reader);
            logger.debug("buscando en indice");
            TopDocs results = searcher.search(query, 10);

            logger.debug("Resultados de la búsqueda para: {}", String.join(", ", busqueda) );
            for (ScoreDoc scoreDoc : results.scoreDocs) {
//                Document doc = searcher.doc(scoreDoc.doc);
                Document doc = searcher.storedFields().document(scoreDoc.doc);
                logger.debug("Documento ID: {}", doc.get("id"));
                logger.debug("Contenido: {}", doc.get("content"));
                logger.debug("Score: {}", scoreDoc.score);
            }
        }
	}

	private Query getQuery(Analyzer analyzer, String...palabras) throws ParseException {
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		for (String palabra:palabras) {
			Query query = new QueryParser("content", analyzer).parse(palabra);
	        booleanQuery.add(query, BooleanClause.Occur.MUST);// MUST, MUST_NOT, SHOULD
		}
        Query query = booleanQuery.build();
		return query;
	}

}
