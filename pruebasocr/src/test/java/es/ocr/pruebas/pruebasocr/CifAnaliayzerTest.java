package es.ocr.pruebas.pruebasocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ocr.pruebas.cif.CifAnalayzer;

public class CifAnaliayzerTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CifAnaliayzerTest.class);
	
	private String txt = "A »A Servicto Tetemásico de Soltcima de NEF N* Expediente AEAT\r\n"
			+ "\r\n"
			+ "l ACI690850011\r\n"
			+ "Agvera Tubenada - Esten del 4memar de p Ne Ne s\r\n"
			+ "\r\n"
			+ "COMUNICACIÓN ACREDITAT!\r\n"
			+ "IVA DEL NÚME\r\n"
			+ "IDENTIFICACIÓN FISCAL “ k\r\n"
			+ "\r\n"
			+ "! La presente comunicación ha sido remitida por la Agencia Estatal de la Admíninácíún Tributaria,\r\n"
			+ "\r\n"
			+ "i Este documento tiene plena validez para acreditar su Número de Identificación Fiscal (NTF).\r\n"
			+ "\r\n"
			+ "tob: .a autentició E detas dentificación Fisca \" y o Para ello la propía\r\n"
			+ "Agencia Tributaria le remitirá próximamente, por correo ordinario y al domicilio fiscal de la sociedad, una\r\n"
			+ "\r\n"
			+ "verificación correspondiente de la validez de la certificación.\r\n"
			+ "\r\n"
			+ "El NIF que le ha sido asignado tiene carácter provisional. En breve plazo recibirá, en su domicilio fiscal, en papel, el\r\n"
			+ "documento identificador de la tarjeta acreditativa.\r\n"
			+ "\r\n"
			+ "Le recordamos que tiene la obligación de aportar la documentación pendiente necesaria para la asignación del NIF\r\n"
			+ "definitivo. Una vez cumplidos los trámites administrativos pertinentes, el NIF definitivo le será remitido al domicilio\r\n"
			+ "a fiscal de la sociedad.\r\n"
			+ "\r\n"
			+ "Recuerde que debe incluir su NTF en t\r\n"
			+ "como consecuencia del desarrollo de\r\n"
			+ "“comunicaciones o escritos que pfesente an\r\n"
			+ "\r\n"
			+ "odos los documentos de naturaleza o con trascendencia tributaria que expida\r\n"
			+ "su actividad, así como en todas las autoliquidaciones, declaraciones,\r\n"
			+ "\r\n"
			+ "te la Administración Tributaria.\r\n"
			+ "\r\n"
			+ "NIF Provisional\r\n"
			+ "B86908522\r\n"
			+ "\r\n"
			+ "y 09/01/2014\r\n"
			+ "¡de expedición del NIF Provisional MADRID - CENTRO\r\n"
			+ "ión de la AEAT ALCOVECOS, S.L.\r\n"
			+ "\r\n"
			+ "SAN NAZARIO, 7 - 28002 Madrid (Madrid)\r\n"
			+ "\r\n"
			+ "o denominación social\r\n"
			+ "o soci SAN NAZARIO, 7 - 28002 Madrid (Madrid)\r\n"
			+ "";

	@Test
	public void simpleTest() {
		CifAnalayzer cifAnalayzer = new CifAnalayzer("F2712284E");
//		cifAnalayzer.analize();
		for (String cif:cifAnalayzer.getCifs("F27122845")) {
			LOGGER.info("cif: {}", cif );
		}
		
	}
	
	@Test
	public void ficheroOcrTest() throws IOException {
		CifAnalayzer cifAnalayzer = new CifAnalayzer("");
		String txt = new String(EcontratoUtils.readImageFile("C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs_octubre\\url_204_0-ECONT-MPV1-231011-07701132_cif.pdf.txt"));
		for (String cif:cifAnalayzer.getCifs(txt)) {
			LOGGER.info("cif: {}", cif );
		}
		
	}
	
	@Test
	public void allFicherosOcrTest() throws IOException {
		CifAnalayzer cifAnalayzer = new CifAnalayzer("");
		String dir ="C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs_octubre\\";
		File dirF = new File(dir);
		
		File[] files = dirF.listFiles();
		for (File file: files) {
			if (file.isDirectory() || !file.getAbsolutePath().endsWith(".txt")) {
				continue;
			}
			
			String txt = new String(EcontratoUtils.readImageFile(file.getAbsolutePath()));
			ArrayList<String> cifs = new ArrayList<>();
			for (String cif:cifAnalayzer.getCifs(txt)) {
				cifs.add(cif);
			}
			LOGGER.info("{} : cifs={} : {}", file.getName(), cifs.size(), cifs);
		}
		
		
	}
	
	@Test
	public void testCifProduccion() throws FileNotFoundException, IOException {
//		String[] cifs= {"A28914851","A48017776","A48017776","A48176671","A61441523","A66454448","B01332246","B01561422","B01626845","B01882018","B01999275","B02173060","B02173060","B02206928","B02232387","B02232387","B02232387","B02303246","B02669661","B02800795","B02800795","B03780012","B03780012","B04538609","B04953758","B05341730","B05341730","B05341730","B05341730","B05341730","B05341730","B05341730","B05341730","B05341730","B05341730","B06673776","B06874515","B06949184","B06949184","B06972020","B07420771","B09117722","B09719139","B09719139","B09719139","B09719139","B09879313","B09879313","B09889478","B09889478","B09982273","B09995648","B10552305","B12839312","B13688577","B13688577","B13717525","B13717525","B13821327","B13876933","B13994629","B14797740","B14935647","B14935647","B14958136","B14958136","B14958136","B14958136","B14958136","B14958136","B17106378","B17747643","B17747643","B17747643","B17821679","B18505891","B18962290","B20508222","B20562278","B23547292","B23633092","B24716268","B24732893","B25794959","B25794959","B25794959","B26343962","B26343962","B26343962","B27835222","B28351914","B28351914","B28351914","B29116084","B29853009","B29853009","B30705214","B30776181","B31807860","B33814898","B33814898","B33814898","B34272435","B36802692","B36802692","B36802692","B37547866","B37547866","B39699376","B42504969","B42833210","B42833210","B42833210","B42848432","B42958298","B44907301","B45606340","B45606340","B45606340","B45864196","B45871654","B45897246","B47761085","B47763149","B47763149","B48534739","B54589163","B55240907","B55240907","B55352769","B56003684","B56028129","B56081516","B56107741","B56196041","B56485170","B56548852","B57177636","B57966194","B59530436","B61964292","B61964292","B63200042","B63347306","B63566715","B63566715","B64061534","B64694755","B64694755","B64694755","B65446262","B65699126","B66145202","B66420258","B66456898","B66712118","B66746157","B66764390","B67628008","B67628008","B67628008","B67642736","B67677344","B67828517","B67925925","B72392608","B72493570","B72518004","B72518004","B72522725","B72539232","B72539232","B72576358","B72688005","B72740053","B72740053","B72807043","B72807043","B72807043","B72807043","B73641078","B76146620","B76236025","B76702539","B78360799","B80944127","B81075921","B81302283","B81694556","B81694556","B81694556","B81694556","B81694556","B81698037","B83639914","B85408102","B86027000","B86579224","B86774890","B86774890","B86908522","B86908522","B86917531","B87074720","B87109039","B87109039","B87247318","B87247318","B87657573","B87691606","B87691606","B87925749","B88241724","B88241724","B88319397","B88319397","B88570346","B88588967","B88588967","B90016817","B90022468","B90197286","B90460569","B91179531","B91315002","B91818377","B91818377","B91818377","B91818377","B91967869","B91967869","B91967869","B92139971","B93511467","B93592939","B93592939","B93592939","B93592939","B94135654","B94145950","B94186848","B95950275","B95989687","B96365119","B96682927","B98158835","B98896749","B98915838","B98915838","B99425571","E32470395","E44812584","E44812584","E56302383","E67902635","E79825121","E85367589","E97336762","E97625172","F03013828","F03013828","F03013828","F06004030","F06004030","F06004030","F06004030","F06031801","F06031801","F06031801","F06031801","F06031801","F06031801","F10874329","F10874329","F10874329","F12009114","F14011563","F14011563","F14011563","F18608125","F29007291","F29007291","F29007291","F29007291","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41013079","F41021486","F41021486","F41028655","F41028655","F41028655","F41028655","F46024774","F46024774","F46024790","F46024790","F46024790","F46024790","F46025037","F46025037","F46026084","F46026084","F46026464","F46026464","F46026464","F46026696","F46209110","F54341565","F54341565","F54341565","F58226523","F92267525","F92267525","F92267525","F92267525","F92267525","G34031252","G45783594","G55176754","G87582524","G92568054","G92568054","J16817298","J16817298","J22427454","J50880491","J56478159","J67854265","J76280106","J76280106","J76280106","J76280106","J93670149","J93670149","J93670149","J95564399","J95808572","J95808572","J98490501","V47087374","V97603732"};
		List<String> cifs = getCifs();
		CifAnalayzer cifAnalayzer = new CifAnalayzer(null);
		int ok = 0;
		int ko = 0;
		for ( String cif:cifs ) {
//			assertFalse(cifAnalayzer.getCifs(cif).isEmpty());
			if (cifAnalayzer.getCifs(cif).isEmpty()) {
				LOGGER.warn("no reconocido {}", cif);
				ko++;
			} else {
				ok++;
			}
		}
		LOGGER.info("OK: {}       KO: {}", ok, ko);
	}
	
	private List<String> getCifs() throws FileNotFoundException, IOException {
		ArrayList<String> cifs = new ArrayList<>();
		
		
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\_PROYECTOS\\KyC\\evolutivos\\20231110-Tesseract\\cifs_produccion.txt"))){
		    String texto = br.readLine();
		    while(texto != null) {
		    	cifs.add(texto);
		        texto = br.readLine();
		    }
		}
		
		return cifs;
	}
	
}
