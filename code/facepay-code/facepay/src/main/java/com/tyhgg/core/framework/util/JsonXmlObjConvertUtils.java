package com.tyhgg.core.framework.util;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public final class JsonXmlObjConvertUtils {
       
    private  JsonXmlObjConvertUtils() {}

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static XmlMapper xmlMapper = new XmlMapper();

    private static Logger logger = LoggerFactory.getLogger(JsonXmlObjConvertUtils.class);

    /**
     * json string convert to javaBean
     * Bean中有Date类型字段时需要用这个方法，JSONObject.toBean(obj, clazz)方法Date字段会赋值成当前时间
	 * Bean中有list时需要用这个方法，JSONObject.toBean(obj, clazz)方法不能准确转化bean
     * json2pojo有多余字段时会报错
     * 
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public static <T> T jsonToObj(String jsonStr, Class<T> clazz){
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            logger.debug("", e);
            return null;
        }
    }

    /**
     * json string convert to map
     * 
     * @throws IOException
     * @throws JsonMappingException
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            logger.debug("", e);
            return null;
        }
    }

    /**
     * json string convert to map with javaBean
     * 
     * @throws IOException
     * @throws JsonMappingException
     */
    public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clazz)
            throws  IOException {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<String, T> result = new HashMap<String, T>();
        for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToPojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     * 
     * @throws IOException
     * @throws JsonMappingException
     */
    public static <T> List<T> jsonToList(String jsonArrayStr, Class<T> clazz)
            throws IOException {
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,
                new TypeReference<List<T>>() {
                });
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(mapToPojo(map, clazz));
        }
        return result;
    }

    /**
     * json string convert to xml string
     * 
     * @throws ControllerException
     * 
     * @throws IOException
     * @throws JsonProcessingException
     */
    public static String jsonToXml(String jsonStr) {
        JsonNode root;
        try {
            root = objectMapper.readTree(jsonStr);
            return xmlMapper.writeValueAsString(root);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            return null;
        }

    }

    /**
     * map convert to xml string
     * 
     * @throws IOException
     */
//    public static String mapToXml(Map<String, ?> map) {
//        String jsonStr;
//        try {
//            jsonStr = objectMapper.writeValueAsString(map);
//            return jsonToXml(jsonStr);
//        } catch (IOException e) {
//            logger.debug(e.getMessage());
//            return null;
//        }
//
//    }

    /**
     * map convert to xml string
     * 
     * @throws ControllerException
     * 
     * @throws JsonProcessingException
     */
    public static String mapToJson(Map<String, ?> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }

    /**
     * map convert to javaBean
     */
    public static <T> T mapToPojo(Map<String, ?> map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * xml string convert to json string
     * 
     * @throws IOException
     */
    public static String xmlToJson(String xml) throws 
            IOException {
        StringWriter w = new StringWriter();
        JsonParser jp = xmlMapper.getFactory().createParser(xml);
        JsonGenerator jg = objectMapper.getFactory().createGenerator(w);
        while (jp.nextToken() != null) {
            jg.copyCurrentEvent(jp);
        }
        jp.close();
        jg.close();
        return w.toString();
    }

    /**
     * javaBean,list,array convert to json string
     * 
     * @throws JsonProcessingException
     */
    public static String objToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * javaBean,list,array convert to Map
     * 
     * @throws ControllerException
     * 
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static Map<String, Object> objToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(obj) != null) {
                    map.put(field.getName(), field.get(obj));
                } else if ("java.util.List".equals(field.getType().getName())) {
                    // 判断是否是空list
                    map.put(field.getName(), new ArrayList<String>());
                } else {
                    map.put(field.getName(), "");
                }
            } catch (IllegalArgumentException e) {
                logger.debug(e.getMessage());
                return null;
            } catch (IllegalAccessException e) {
                logger.debug(e.getMessage());
                return null;
            }
        }
        return map;
    }
    

	/**
	 * Dubbo请求参数是List的参数封装，如：Request<List<CardServiceDTO>>
	 * @param <JSONArray>
	 * @param className
	 * @param httpBody
	 * @return
	 * @throws ControllerException
	 *//*
	private static <JSONArray> Object creatListRequestParams(String className, JSONArray jsonArray) {
		String[] params = className.split("-");
		
		Class<?> clazz = ReflectionUtil.getClass(params[1]);
		@SuppressWarnings("deprecation")
		List<?> list = JSONArray.toList(jsonArray, clazz);
		
		return list;
		
	}*/
}
