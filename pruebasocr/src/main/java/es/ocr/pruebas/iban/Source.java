package es.ocr.pruebas.iban;

public class Source {
	
	private final String source;
	
	private int current;

	public Source(String source) {
		this.source = source;
		current = 0;
	}

	public String getSource() {
		return source;
	}
	
	public Character getNextCharacter() {
		return source.charAt(current++);
	}
	
	public boolean hasNext() {
		return current>source.length()-1; 
	}
	
	public int getCurrentCharacter() {
		return current;
	}
	
	public void setCurrentCharacter(int current) {
		this.current = current;
	}
	
	
}
