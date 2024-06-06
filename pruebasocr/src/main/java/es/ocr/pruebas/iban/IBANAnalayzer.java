package es.ocr.pruebas.iban;

import java.util.List;


/**
 * clase no singleton
 * 
 * @author 67760769 - Rubén Aguado
 *
 */
public class IBANAnalayzer {

	private Source source;
	
	// almacenamos los iban reconocidos
	private List<String> tokens;
	
	private StringBuilder currentToken; 
	
	private IBANTemplate template = new EspanaTemplate();
//	private List<IBANTemplate> templates = new ArrayList<>() hay que modificar esto si reconocemos más ibanes que los de españa 
	
	
	public IBANAnalayzer(String sourceTxt) {
		this.source = new Source(sourceTxt);
		this.currentToken = new StringBuilder();
//		this.templates.add(new EspanaTemplate())
	}

	public void analize(){
		

		while ( source.hasNext()) {
			Character c = source.getNextCharacter();
			if (  template.isPrimerCaracter(c) ) {
				int ancla = source.getCurrentCharacter()+1;
				currentToken = new StringBuilder();
				currentToken.append(template.getPrimerCaracter());
				if (analizaSegundoCaracterYMas()) {
					if (currentToken.length() == template.getLongitudCuenta()+4) {
						tokens.add(currentToken.toString());
					}
				} else {
					source.setCurrentCharacter(ancla);
					
				}
				currentToken = null;
			}
		}
		
	}
	
	private boolean analizaSegundoCaracterYMas() {
		if (source.hasNext()) {
			Character c = source.getNextCharacter();
			if ( template.isSegundoCaracter(c) ) {
				currentToken.append(template.getSegundoCaracter());
				if (analizaTerceroCuartoCaracterYMas()) {
						return true;
				} 
			}
		} 
		return false;
	}
	
	private boolean analizaTerceroCuartoCaracterYMas() {
		if (source.hasNext()) {
			Character c3 = source.getNextCharacter();
			currentToken.append(c3);
			if ( Character.isDigit(c3) && source.hasNext()) {
					Character c4 = source.getNextCharacter();
					currentToken.append(c4);
					if ( Character.isDigit(c4) && analizaCCC()) {
						return true;
					}
			}
		}
		return false;
	}
	
	private boolean analizaCCC() {
		while (source.hasNext() ) {
			Character c = source.getNextCharacter();
			if ( !skipLetter(c) ) {
				if (template.isCCCSoloDigitos()) {
					if (Character.isDigit(c) ) {
						currentToken.append(c);
					} else {
						return false;
					}
				} else {
					currentToken.append(c);
				}
			}
			
			if (currentToken.length() >= template.getLongitudCuenta()+4) {
				return true;
			}
		}
		return false;
	}
	
	 
	
	private boolean skipLetter(Character c) {
		return c.equals(' ') || c.equals('\t') || c.equals('\n'); // TODO: meter más
	}
	
	public List<String> getTokens() {
		return tokens;
	}

}
