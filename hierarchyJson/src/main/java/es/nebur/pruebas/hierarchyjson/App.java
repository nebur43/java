package es.nebur.pruebas.hierarchyjson;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * La solucion está en usar: @JsonIdentityInfo
 * 
 * 
 * Para usar hibernate module:
 * 
 * try {
			ObjectMapper mapper = new ObjectMapper();
			Hibernate4Module module = new Hibernate4Module();
			module.configure(Feature.FORCE_LAZY_LOADING, true);
			mapper.registerModule(module);
			
			mapper.writeValue(new File("c:\\tmp\\ecSignProcessKyC.json"),result );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
 *
 */
public class App 
{
	
	public static SerializerProvider serializerProvider ;
	
	public Owner getOwner() {
		Owner o = new Owner();
		o.setName("ruben");
		o.setSurmane("aguado");
		
		Pet pet = new Pet();
		pet.setName("coco");
		pet.setOwner(o);
		
		o.setPet(pet);
		
		return o;
	}
	
	public Owner getOwnerNoRecursion() {
		Owner o = new Owner();
		o.setName("ruben");
		o.setSurmane("aguado");
		
		Pet pet = new Pet();
		pet.setName("coco");
//		pet.setOwner(o);
		
		o.setPet(pet);
		
		return o;
	}
	
	public BoxPet getBox() {
		Pet pet = new Pet();
		pet.setName("coco");
		
		Pet pet2 = new Pet();
		pet2.setName("chip");
		
		Pet pet3 = new Pet();
		pet3.setName("chop");
		
		ArrayList<Pet> pets = new ArrayList<Pet>();
		pets.add(pet);
		pets.add(pet2);
		pets.add(pet3);
		
		BoxPet boxPet = new BoxPet();
		boxPet.setBoxName("caja pets");
		boxPet.setCountPets(3);
		boxPet.setPets(pets);
		
		return boxPet;
		
	}
	
	
	
	
    public static void main( String[] args )
    {
    	
    	App app = new App();
    	Owner owner = app.getOwner();
    	try {

	    	ObjectMapper mapper = new ObjectMapper();
	    	
//	    	serializerProvider =  mapper.getSerializerProviderInstance();
	    	
//	    	System.out.println("owner ser:" + mapper.getSerializerProvider().findValueSerializer(owner.getClass()) ); 
	    	
//	    	mapper.registerModule(new ModuleObjectRecursion());
	    	
//	    	System.out.println("owner ser:" + mapper.getSerializerProvider().findValueSerializer(owner.getClass()) );
    	
//	    	ArrayList<Object> a = new ArrayList<Object>();
//	    	
//	    	a.add(owner);
//	    	a.add(app.getBox());
			mapper.writeValue(new File("c:\\tmp\\owner.json"),owner );
//			System.out.println("owner ser:" + mapper.getSerializerProvider().findValueSerializer(owner.getClass()) );
//			mapper.writeValue(new File("c:\\tmp\\owner.json"),app.getBox() );
			
			
			Owner o2 = mapper.readValue(new File("c:\\\\tmp\\\\owner.json"), Owner.class);
			System.out.println("o2:" + o2);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	
    	System.out.println("fin");
    	
    }
}
