package es.nebur.pruebas.module;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ObjectRecursionJsonSerializer extends JsonSerializer<Object>{
	
	private JsonSerializer<?> originalSerializer;
	
	public ObjectRecursionJsonSerializer() {
	}
	
	public ObjectRecursionJsonSerializer(JsonSerializer<?> originalSerializer) {
		this.originalSerializer = originalSerializer;
	}

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//		JsonSerializer<Object> ser = serializers.findTypedValueSerializer(value.getClass(), true, null);
//		ser.serialize(value, gen, serializers);
		
//		originalSerializer.serialize(value, gen, serializers);
//		
		System.out.println("value:" + value);
		
		gen.writeString("hola");
//		gen.writeFieldName("adios");
//		gen.writeNumber(1);

	}

}
