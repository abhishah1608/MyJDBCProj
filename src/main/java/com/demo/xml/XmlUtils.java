package com.demo.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class XmlUtils {

    // Serialize Object to XML String
    public static <T> String convertObjToXml(T obj) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        xmlMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return xmlMapper.writeValueAsString(obj);
    }

    // Serialize List to XML String
    public static <T> String convertListToXml(List<T> list) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return xmlMapper.writeValueAsString(list);
    }

    // Deserialize XML String to Object
    public static <T> T convertXmlToObj(String xml, Class<T> type) throws JsonMappingException, JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        xmlMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return xmlMapper.readValue(xml, type);
    }

    // Deserialize XML String to List of Objects
    public static <T> List<T> convertXmlToList(String xml, Class<T> type) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        xmlMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return xmlMapper.readValue(xmlMapper.getFactory().createParser(xml),
                xmlMapper.getTypeFactory().constructCollectionType(ArrayList.class, type));
    }
}

