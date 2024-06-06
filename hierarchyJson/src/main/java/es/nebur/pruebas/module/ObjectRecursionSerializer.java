package es.nebur.pruebas.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;

public class ObjectRecursionSerializer implements Serializers {

	@Override
	public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
		return new ObjectRecursionJsonSerializer();
	}



	@Override
	public JsonSerializer<?> findArraySerializer(SerializationConfig config, ArrayType type, BeanDescription beanDesc,
			TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
		return null;
	}

	@Override
	public JsonSerializer<?> findCollectionSerializer(SerializationConfig config, CollectionType type,
			BeanDescription beanDesc, TypeSerializer elementTypeSerializer,
			JsonSerializer<Object> elementValueSerializer) {
		return null;
	}

	@Override
	public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig config, CollectionLikeType type,
			BeanDescription beanDesc, TypeSerializer elementTypeSerializer,
			JsonSerializer<Object> elementValueSerializer) {
		return null;
	}

	@Override
	public JsonSerializer<?> findMapSerializer(SerializationConfig config, MapType type, BeanDescription beanDesc,
			JsonSerializer<Object> keySerializer, TypeSerializer elementTypeSerializer,
			JsonSerializer<Object> elementValueSerializer) {
		return null;
	}

	@Override
	public JsonSerializer<?> findMapLikeSerializer(SerializationConfig config, MapLikeType type,
			BeanDescription beanDesc, JsonSerializer<Object> keySerializer, TypeSerializer elementTypeSerializer,
			JsonSerializer<Object> elementValueSerializer) {
		return null;
	}

}
