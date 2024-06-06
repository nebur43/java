package es.pruebas.multipart;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ServerController {

	/**
	 * http://localhost:8080/multipart/version
	 * 
	 * @return
	 */
	@GetMapping (value = "/version")
	public String version() {
		System.out.println("entrando en /version");
		return "1.0";
	}
	
	/**
	 * http://localhost:8080/multipart/upload
	 * 
	 * @param idPetition
	 * @param file
	 * @return
	 */
	@PostMapping (value = "/upload" ,consumes = "multipart/form-data")
	public ResponseEntity<String> uploadFile(@RequestPart RequestBean requestJson, 
			@RequestPart MultipartFile file) {
		
		try {
			System.out.println("llamada a /upload");
//			System.out.println("idPetition="+ idPetition);
			if ( file != null ) {
				System.out.println("multipart file bytes="+ file.getBytes().length);
				System.out.println("File Content:\n" + new String(file.getBytes()));
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("request.getIdOperacion()= " + requestJson.getIdOperacion());
		System.out.println("request.getNumeroOperaciones()= " + requestJson.getNumeroOperaciones());
		
		return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
	}
	
}
