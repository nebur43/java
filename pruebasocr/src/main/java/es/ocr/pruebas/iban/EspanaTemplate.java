 package es.ocr.pruebas.iban;

public class EspanaTemplate implements IBANTemplate {
	
	@Override
	public boolean isPrimerCaracter(Character c) {
		return c.equals('E');
	}
	
	@Override
	public boolean isSegundoCaracter(Character c) {
		return c.equals('S') || c.equals('5') || c.equals('3') || c.equals('8');
	}
	
	@Override
	public Character getPrimerCaracter() {
		return 'E';
	}
	
	@Override
	public Character getSegundoCaracter() {
		return 'S';
	}
	
	@Override
	public int getLongitudCuenta() {
		return 20;
	}
	
	@Override
	public boolean isCCCSoloDigitos() {
		return true;
	}

}
