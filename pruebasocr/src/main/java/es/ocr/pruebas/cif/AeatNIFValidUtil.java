package es.ocr.pruebas.cif;

import org.springframework.util.StringUtils;

public class AeatNIFValidUtil {

	public static final int TAM_NIF = 9;
    public static final int NIF_ERROR = -1;
    public static final int NIF_ERROR_TAMANO = -2;
    public static final int NIF_ERROR_BLANCOS = -3;
    public static final int NIF_ERROR_CARACTERES = -4;
    public static final int NIF_ERROR_3LETRAS = -5;
    public static final int NIF_ERROR_DATOSENTRADA = -6;
    public static final int CIF_ERROR_DC = -10;
    public static final int NIF_ERROR_DC = -11;
    public static final int NIF_ERROR_NUM = -12;
    public static final int NIF_ERROR_DOSNUM = -13;
    public static final int DNI_ERROR_MAX = -20;
    public static final int DNI_ERROR_VALOR = -21;
    public static final int DNI_OK = 0;
    public static final int NIF_OK = 1;
    public static final int NIF_NORESIDENTES = 2;
    public static final int NIF_MENORES14ANOS = 3;
    public static final int NIF_EXTRANJEROS = 4;
    public static final int CIF_OK = 20;
    public static final int CIF_EXTRANJERO_OK = 21;
    public static final int CIF_ORGANIZACION_OK = 22;
    public static final int CIF_NORESIDENTES_OK = 23;
    private static final char[] NUMEROS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] LETRAS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] LETRASNIF = new char[]{'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
    private static final char[] LETRAS2CIF = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    private static final char[] LETRASCIF = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'U', 'V'};
    private static final char[] LETRASCIFORG_Y_EXTR = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'P', 'Q', 'S', 'N', 'W', 'R'};
    private static final char[] LETRASREGATRIBRENTAS = new char[]{'E', 'G', 'H', 'J', 'U', 'V'};
    private static final char[] LETRASCIFEXT = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'N', 'W'};
    private static final char[] LETRASNIFEXT = new char[]{'X', 'Y', 'Z'};
	
    private AeatNIFValidUtil() {
    	
    }
    
	/**
	 * Método que nos valida si es un CIF válido o no. 
	 * @param value Valor a validar.
	 * @return True si es correcto.
	 */
	public static boolean nifValido(final String value) {
		boolean hayError = false;
		if (value instanceof String) {
			if (!StringUtils.isEmpty(value)) {
		    	int result = validNif(value);
		    	switch (result) {
					case NIF_OK:
						hayError = false;
						break;
					case CIF_OK:
					case CIF_ORGANIZACION_OK:
					case CIF_EXTRANJERO_OK:
					case CIF_NORESIDENTES_OK:
						hayError = true;
						break;
					case NIF_EXTRANJEROS:
						hayError = true;
						break;
					default:
						hayError = true;	
				}
			}
		} else {
			hayError = true;
		}
		return !hayError;
	}
	
	/**
	 * Método que nos valida si es un CIF válido o no. 
	 * @param value Valor a validar.
	 * @return True si es correcto.
	 */
	public static boolean nieValido(final String value) {
		boolean hayError = false;
		if (value instanceof String) {
			if (!StringUtils.isEmpty(value)) {
		    	int result = validNif(value);
		    	switch (result) {
					case NIF_OK:
						hayError = true;
						break;
					case CIF_OK:
					case CIF_ORGANIZACION_OK:
					case CIF_EXTRANJERO_OK:
					case CIF_NORESIDENTES_OK:
						hayError = true;
						break;
					case NIF_EXTRANJEROS:
						hayError = false;
						break;
					default:
						hayError = true;	
				}
			}
		} else {
			hayError = true;
		}
		return !hayError;
	}
	
	/**
	 * Método que nos valida si es un CIF válido o no. 
	 * @param value Valor a validar.
	 * @return True si es correcto.
	 */
	public static boolean cifValido(final String value) {
		boolean hayError = false;
		if (value instanceof String) {
			if (!StringUtils.isEmpty(value)) {
		    	int result = validNif(value);
		    	switch (result) {
					case NIF_OK:
						hayError = true;
						break;
					case CIF_OK:
					case CIF_ORGANIZACION_OK:
					case CIF_EXTRANJERO_OK:
					case CIF_NORESIDENTES_OK:
						hayError = false;
						break;
					case NIF_EXTRANJEROS:
						hayError = true;
						break;
					default:
						hayError = true;	
				}
			}
		} else {
			hayError = true;
		}
		return !hayError;
	}
	
    /**
     * Método realizado por la AEAT.
     * @param string NIF a validar.
     * @return Código con el resultado de la validación.
     */
    private static int validNif(String string) {
        int n;
        String string2 = null;
        int n2 = 0;
        int n3 = 0;
        long l = 0;
        long l2 = 0;
        if ((string = string.trim()).length() != TAM_NIF) {
            return NIF_ERROR_TAMANO;
        }
        char c = '\u0000';
        char c2 = '\u0000';
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        char[] arrc = string.toCharArray();
        for (n = 0; n < TAM_NIF; ++n) {
            char c3 = arrc[n];
            int n7 = Character.getType(c3);
            if (n7 == 1 && n4 == 0) {
                ++n4;
                c = c3;
                n5 = n;
                continue;
            }
            if (n7 == 1 && n4 == 1) {
                ++n4;
                c2 = c3;
                n6 = n;
                continue;
            }
            if (n7 == 1 && n4 == 2) {
                return NIF_ERROR_3LETRAS;
            }
            if (n7 == TAM_NIF) continue;
            return NIF_ERROR_CARACTERES;
        }
        if (n4 == 0) {
            if (arrc[0] != '0') {
                return DNI_ERROR_MAX;
            }
            String string3 = string.substring(1);
            if (string3.equals("11111111") || string3.equals("22222222") || string3.equals("33333333") || string3.equals("44444444") || string3.equals("55555555") || string3.equals("66666666") || string3.equals("77777777") || string3.equals("88888888") || string3.equals("99999999") || string3.equals("00000000")) {
                return DNI_ERROR_VALOR;
            }
            return DNI_OK;
        }
        if (n4 == 1 && caracEnCad(LETRASCIF, arrc[n5]) && n5 == 0 && Character.isDigit(arrc[8])) {
            for (n = 1; n < 8; ++n) {
                if (n == 2 || n == 4 || n == 6) {
                    n3+=arrc[n] - 48;
                    continue;
                }
                n2 = (arrc[n] - 48) * 2;
                if (n2 > TAM_NIF) {
                    n2-=TAM_NIF;
                }
                n3+=n2;
            }
            if ((n3 = 10 - n3 % 10) == 10) {
                n3 = 0;
            }
            if (n3 == arrc[n] - 48) {
                if (caracEnCad(LETRASREGATRIBRENTAS, arrc[n5])) {
                    return CIF_NORESIDENTES_OK;
                }
                return CIF_OK;
            }
            return CIF_ERROR_DC;
        }
        if (n4 == 2 && caracEnCad(LETRASCIFORG_Y_EXTR, arrc[n5]) && n5 == 0 && n6 == 8 && caracEnCad(LETRAS2CIF, arrc[n6])) {
            for (n = 1; n < 8; ++n) {
                if (n == 2 || n == 4 || n == 6) {
                    n3+=arrc[n] - 48;
                    continue;
                }
                n2 = (arrc[n] - 48) * 2;
                if (n2 > TAM_NIF) {
                    n2-=TAM_NIF;
                }
                n3+=n2;
            }
            if (LETRAS2CIF[(n3 = 10 - n3 % 10) - 1] == arrc[n6]) {
                if (caracEnCad(LETRASCIFEXT, arrc[n5])) {
                    return CIF_EXTRANJERO_OK;
                }
                return CIF_ORGANIZACION_OK;
            }
            return CIF_ERROR_DC;
        }
        if (n4 == 1 && caracEnCad(LETRAS, arrc[8]) && caracEnCad(LETRASNIF, arrc[n5]) && n5 == 8) {
            StringBuilder stringBuffer = new StringBuilder(string.substring(0, n5));
            l = Long.parseLong(stringBuffer.toString());
            l2 = l % CIF_NORESIDENTES_OK;
            if (l2 + 1 > CIF_NORESIDENTES_OK) {
                return NIF_ERROR_NUM;
            }
            if (c == LETRASNIF[(int)l2]) {
                if (string.equals("00000001R") || string.equals("00000000T") || string.equals("99999999R")) {
                    return NIF_ERROR;
                }
                return NIF_OK;
            }
            return NIF_ERROR_DC;
        }
        if (n4 == 2 && (arrc[0] == 'K' || arrc[0] == 'L' || arrc[0] == 'M') && caracEnCad(LETRASNIF, arrc[n6]) && n6 == 8) {
            string2 = string.substring(1, 3);
            if (!(caracEnCad(NUMEROS, string2.charAt(0)) && caracEnCad(NUMEROS, string2.charAt(1)))) {
                return NIF_ERROR_DOSNUM;
            }
            n = Integer.parseInt(string2);
            if (n < 1 || n > 56) {
                return NIF_ERROR;
            }
            string2 = string.substring(1, n6);
            l = Long.parseLong(string2);
            l2 = l % CIF_NORESIDENTES_OK;
            if (++l2 > CIF_NORESIDENTES_OK) {
                return NIF_ERROR_NUM;
            }
            if (c2 == LETRASNIF[(int)(l2 - 1)]) {
                return NIF_NORESIDENTES;
            }
            return NIF_ERROR_DC;
        }
        if (string.equals("X0000000T")) {
            return NIF_ERROR;
        }
        if (n4 == 2 && caracEnCad(LETRASNIFEXT, arrc[0]) && caracEnCad(LETRASNIF, arrc[n6]) && n6 == 8) {
            string2 = string.substring(1, n6);
            l = Long.parseLong(string2);
            if (arrc[0] == 'Y') {
                l+=10000000;
            } else if (arrc[0] == 'Z') {
                l+=20000000;
            }
            l2 = l % CIF_NORESIDENTES_OK;
            if (++l2 > CIF_NORESIDENTES_OK) {
                return NIF_ERROR_NUM;
            }
            if (c2 == LETRASNIF[(int)(l2 - 1)]) {
                return NIF_EXTRANJEROS;
            }
            return NIF_ERROR_DC;
        }
        return NIF_ERROR;
    }

    private static boolean caracEnCad(char[] arrc, char c) {
        boolean bl = false;
        int n = arrc.length;
        for (int i = 0; i < n; ++i) {
            if (arrc[i] != c) continue;
            bl = true;
            break;
        }
        return bl;
    }
}
