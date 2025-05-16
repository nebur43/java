package com.nebur.test.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class CustomAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
        // Usa un tokenizador estándar
        StandardTokenizer tokenizer = new StandardTokenizer();
        TokenStream tokenStream = new LowerCaseFilter(tokenizer);  // Convierte a minúsculas
        tokenStream = new ASCIIFoldingFilter(tokenStream);  // Remueve tildes y caracteres acentuados
        return new TokenStreamComponents(tokenizer, tokenStream);
	}

}
