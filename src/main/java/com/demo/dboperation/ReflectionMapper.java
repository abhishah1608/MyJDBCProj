package com.demo.dboperation;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReflectionMapper {
	
    // Generic method to map a single row from ResultSet to an Object (Case-Insensitive Column Mapping)
    public static <T> T mapRow(ResultSet rs, Class<T> clazz) throws SQLException {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            // Fetch column names (case-insensitive mapping)
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Map<String, String> columnNameMap = new HashMap<>();

            for (int i = 1; i <= columnCount; i++) {
                columnNameMap.put(metaData.getColumnName(i).toLowerCase(), metaData.getColumnName(i));
            }

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true); // Allow private field access
                String fieldName = field.getName().toLowerCase(); // Convert field name to lowercase

                if (columnNameMap.containsKey(fieldName)) { // Match column name ignoring case
                    String actualColumnName = columnNameMap.get(fieldName);
                    Object value = rs.getObject(actualColumnName); // Fetch data using correct column name
                   
                    // Handle java.sql.Timestamp to LocalDateTime conversion
                    if (value instanceof Timestamp && field.getType().equals(LocalDateTime.class)) {
                        value = ((Timestamp) value).toLocalDateTime();
                    }
                    
                    // Handle BigDecimal to float conversion
                    if (value instanceof BigDecimal && field.getType().equals(float.class)) {
                        value = ((BigDecimal) value).floatValue();
                    }

                    // Handle BigDecimal to double conversion
                    if (value instanceof BigDecimal && field.getType().equals(double.class)) {
                        value = ((BigDecimal) value).doubleValue();
                    }
                    
                    // Convert String to UUID (for GUID fields)
                    if (value instanceof String && field.getType().equals(UUID.class)) {
                        value = UUID.fromString((String) value);
                    }

                    field.set(instance, value); // Assign value to field
                }
            }
            return instance;

        } catch (Exception e) {
            throw new RuntimeException("Error mapping ResultSet to " + clazz.getSimpleName(), e);
        }
    }


	// Generic method to map an entire ResultSet to a List<T>
    public static <T> List<T> mapResultSet(ResultSet rs, Class<?> clazz) throws SQLException {
        List<T> list = new ArrayList<>();

        try {
            while (rs.next()) {
                T obj = (T) mapRow(rs, clazz);
                list.add(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error mapping ResultSet to " + clazz.getSimpleName(), e);
        }

        return list;
    }
    
    public static <T> T mapRowToObject(Map<String, Object> rowMap, Class<T> clazz) throws Exception {
        T obj = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;

            // Case-insensitive lookup in map keys
            for (String key : rowMap.keySet()) {
                if (key.equalsIgnoreCase(fieldName)) {
                    value = rowMap.get(key);
                    break;
                }
            }

            if (value != null) {
                Class<?> fieldType = field.getType();

                // Handle common type conversions
                if (value instanceof Timestamp && fieldType.equals(LocalDateTime.class)) {
                    value = ((Timestamp) value).toLocalDateTime();
                } else if (value instanceof BigDecimal) {
                    if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                        value = ((BigDecimal) value).floatValue();
                    } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                        value = ((BigDecimal) value).doubleValue();
                    } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                        value = ((BigDecimal) value).intValue();
                    } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                        value = ((BigDecimal) value).longValue();
                    }
                } else if (value instanceof String && fieldType.equals(UUID.class)) {
                    value = UUID.fromString((String) value);
                }

                field.set(obj, value);
            }
        }
        return obj;
    }

    
    
    public static <T> List<T> getMappedList(Map<Integer, Object> resultMap, int resultIndex, Class<T> clazz) throws Exception {
        Object result = resultMap.get(resultIndex);
        if (result instanceof List<?>) {
            List<Map<String, Object>> rows = (List<Map<String, Object>>) result;
            List<T> mappedList = new ArrayList<>();
            for (Map<String, Object> row : rows) {
                mappedList.add(mapRowToObject(row, clazz));
            }
            return mappedList;
        }
        return List.of();  // Empty list if no result
    }

    public static <T> T getSingleMappedObject(Map<Integer, Object> resultMap, int resultIndex, Class<T> clazz) throws Exception {
        List<T> list = getMappedList(resultMap, resultIndex, clazz);
        return (!list.isEmpty()) ? list.get(0) : null;
    }
    
    public static int getTotalUpdateCount(Map<Integer, Object> resultMap) {
        int totalCount = 0;
        for (Object value : resultMap.values()) {
            if (value instanceof Integer) {
                totalCount += (Integer) value;
            }
        }
        return totalCount;
    }

}
