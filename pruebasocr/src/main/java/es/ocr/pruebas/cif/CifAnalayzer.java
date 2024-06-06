package es.ocr.pruebas.cif;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * clase no singleton
 * 
 * @author 67760769 - Rubén Aguado
 *
 */
public class CifAnalayzer {

	private TextPointer tp;
	
	// almacenamos los iban reconocidos
	private List<String> tokens;
	
//	private List<IBANTemplate> templates = new ArrayList<>() hay que modificar esto si reconocemos más ibanes que los de españa 
	
	
	public CifAnalayzer(String text) {
		this.tp = new TextPointer(text);
//		this.templates.add(new EspanaTemplate())
	}

	public void analize(){
		while ( tp.hasNext()) {
			String x = tp.getNextCharacters(9);
			if ( isCif(x) ) {
				tokens.add(x);
			}
		}
	}
	
	
	
	private boolean isPrimerCaracter(Character c) {
		return c.equals('A') //Sociedades anónimas.
				|| c.equals('B') // Sociedades de responsabilidad limitada.
				|| c.equals('C') // Sociedades colectivas.
				|| c.equals('D') //  Sociedades comanditarias.
				|| c.equals('E') // Comunidades de bienes.
				|| c.equals('F') //  Sociedades cooperativas.
				|| c.equals('G') //  Asociaciones y fundaciones.
				|| c.equals('H') // Comunidades de propietarios en régimen de propiedad horizontal.
				|| c.equals('J') // Sociedades civiles.
				|| c.equals('N') // Entidades no residentes.
				|| c.equals('P') // Corporaciones locales.
				|| c.equals('Q') // Organismos autónomos, estatales o no, y asimilados, y congregaciones e instituciones religiosas.
				|| c.equals('R') // Congregaciones e instituciones religiosas (desde 2008, ORDEN EHA/451/20084​)
				|| c.equals('S') // Órganos de la Administración del Estado y comunidades autónomas 
				|| c.equals('U') // Uniones Temporales de Empresas.
				|| c.equals('V') // Sociedad Agraria de Transformación.
				|| c.equals('W') // Establecimientos permanentes de entidades no residentes en España
				|| c.equals('K') || c.equals('L') || c.equals('M') // compatibilidad con formatos antiguos
				|| c.equals('X') //  Extranjeros, que en lugar del D.N.I. tienen el N.I.E
				; 
	}
	
	private boolean isProvinciaValida(Character c1, Character c2) {
		return false;
	}

	public List<String> getCifs(String text) {
		ArrayList<String> cifs = new ArrayList<>();
//		Pattern cifPattern = Pattern.compile("(\\b)([ABCDEFGHJNPQRSUVWKLMX])(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)([ABCDEFGHIJ0123456789])(\\b)");
		Pattern cifPattern = Pattern.compile("\\b[ABCDEFGHJNPQRSUVWKLMX]\\d\\d\\d\\d\\d\\d\\d[ABCDEFGHIJ0123456789]\\b");
	    Matcher m = cifPattern.matcher(text);
	    while (m.find()) {
	    	if (AeatNIFValidUtil.cifValido(m.group())
	    			&& !cifs.contains(m.group())) {
	    		cifs.add(m.group());
	    	}
	    }
	    return cifs;
	}
	
	public List<String> getCifsMio(String text) {
		ArrayList<String> cifs = new ArrayList<>();
		Pattern cifPattern = Pattern.compile("([ABCDEFGHJNPQRSUVWKLMX])(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)([ABCDEFGHIJ0123456789])");
	    Matcher m = cifPattern.matcher(text);
	    while (m.find()) {
	    	int sumaPar = Integer.parseInt(m.group(3)) + Integer.parseInt(m.group(5)) + Integer.parseInt(m.group(7));
            int sumaDigito2 = (Integer.parseInt(m.group(2)) * 2) % 10 + (Integer.parseInt(m.group(2)) * 2) / 10;
            int sumaDigito4 = (Integer.parseInt(m.group(4)) * 2) % 10 + (Integer.parseInt(m.group(4)) * 2) / 10;
            int sumaDigito6 = (Integer.parseInt(m.group(6)) * 2) % 10 + (Integer.parseInt(m.group(6)) * 2) / 10;
            int sumaDigito8 = (Integer.parseInt(m.group(8)) * 2) % 10 + (Integer.parseInt(m.group(8)) * 2) / 10;
            int sumaImpar = sumaDigito2 + sumaDigito4 + sumaDigito6 + sumaDigito8;
            int suma = sumaPar + sumaImpar;
            int control = 10 - (suma % 10);
            if(control == 10)
                control = 0;
            String letras = "JABCDEFGHI";
            if(m.group(1).equals("K") 
            		|| m.group(1).equals("P") 
            		|| m.group(1).equals("Q")
            		|| m.group(1).equals("R") 
            		|| m.group(1).equals("S")
            		|| m.group(1).equals("W")
            		|| m.group(1).equals("N")
            		|| (m.group(2).equals("0") && m.group(3).equals("0")) ) {
            	if (m.group(9).equals(letras.substring(control, control + 1))) {
            		cifs.add(m.group());
            	}
            } else if(m.group(1).equals("A") 
            		|| m.group(1).equals("B") 
            		|| m.group(1).equals("E") 
            		|| m.group(1).equals("H")) {
            	if (m.group(9).equals(String.valueOf(control))) {
            		cifs.add(m.group());
            	}
            } else if (m.group(9).equals(letras.substring(control, control + 1)) || m.group(9).equals(String.valueOf(control)) ) {
            	cifs.add(m.group());
            }
	    }
		
		return cifs;
	}
	
	
    public boolean isCif(String cif) {
        Pattern cifPattern = Pattern.compile("([ABCDEFGHJNPQRSUVWKLMX])(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)([ABCDEFGHIJ0123456789])");
        Matcher m = cifPattern.matcher(cif);
        if(m.matches())
        {
            int sumaPar = Integer.parseInt(m.group(3)) + Integer.parseInt(m.group(5)) + Integer.parseInt(m.group(7));
            int sumaDigito2 = (Integer.parseInt(m.group(2)) * 2) % 10 + (Integer.parseInt(m.group(2)) * 2) / 10;
            int sumaDigito4 = (Integer.parseInt(m.group(4)) * 2) % 10 + (Integer.parseInt(m.group(4)) * 2) / 10;
            int sumaDigito6 = (Integer.parseInt(m.group(6)) * 2) % 10 + (Integer.parseInt(m.group(6)) * 2) / 10;
            int sumaDigito8 = (Integer.parseInt(m.group(8)) * 2) % 10 + (Integer.parseInt(m.group(8)) * 2) / 10;
            int sumaImpar = sumaDigito2 + sumaDigito4 + sumaDigito6 + sumaDigito8;
            int suma = sumaPar + sumaImpar;
            int control = 10 - (suma % 10);
            if(control == 10)
                control = 0;
            String letras = "JABCDEFGHI";
            if(m.group(1).equals("K") || m.group(1).equals("P") || m.group(1).equals("Q") || m.group(1).equals("S"))
                return m.group(9).equals(letras.substring(control, control + 1));
            if(m.group(1).equals("A") || m.group(1).equals("B") || m.group(1).equals("E") || m.group(1).equals("H"))
                return m.group(9).equals((new StringBuilder()).append(control).toString());
            return m.group(9).equals(letras.substring(control, control + 1)) || m.group(9).equals((new StringBuilder()).append(control).toString());
        } else
        {
            return false;
        }
    }
	
//	private boolean skipLetter(Character c) {
//		return c.equals(' ') || c.equals('\t') || c.equals('\n'); // TODO: meter más
//	}
	
	public List<String> getTokens() {
		return tokens;
	}
	
	

}
