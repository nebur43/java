package es.ocr.pruebas.cif;

public class TextPointer {
	
	private final String text;
	
	private int indice;

	public TextPointer(String text) {
		this.text = text;
		indice = -1;
	}

	public Character getNextCharacter() {
		indice++;
		return text.charAt(indice);
	}
	
	public String getNextCharacters(int length) {
		indice++;
		return text.substring(indice, length);
	}
	
	public String getCurrentCharacters(int length) {
		return text.substring(indice, length);
	}
	
	public boolean hasNext() {
		return indice<text.length()-1; 
	}
	
	public String getText() {
		return text;
	}
	
}
