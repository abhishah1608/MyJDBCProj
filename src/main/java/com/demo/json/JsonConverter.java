package com.demo.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
	
	public static <T> String convertObjToJson(T obj) throws JsonProcessingException
	{
		String json = null;
		
        ObjectMapper mapper = new ObjectMapper();
        
        json = mapper.writeValueAsString(obj);
        return json;
	}
	
	public static <T> String convertListToJson(List<T> list) throws JsonProcessingException
	{
		String json = null;
		
        ObjectMapper mapper = new ObjectMapper();
        
        json = mapper.writeValueAsString(list);
        return json;
	}
	
	public static <T> T convertJsonToObj(String json) throws JsonMappingException, JsonProcessingException
	{
		T obj = null;
		ObjectMapper mapper = new ObjectMapper();
		obj = mapper.readValue(json, new TypeReference<T>() {});
		return obj;
	}
	
	public static <T> List<T> convertJsonToList(String jsonArray, Class<T> type) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonArray, mapper.getTypeFactory().constructCollectionType(ArrayList.class, type));
    }
}
