package es.ocr.pruebas.iban;

public interface IBANTemplate {

	boolean isPrimerCaracter(Character c);

	boolean isSegundoCaracter(Character c);

	int getLongitudCuenta();

	Character getPrimerCaracter();

	Character getSegundoCaracter();

	boolean isCCCSoloDigitos();

}