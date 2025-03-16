package com.demo.dboperation;

import java.util.HashMap;
import java.util.List;

public class CollectionUtils {

    /**
     * Generic method to retrieve a list of objects from a HashMap
     *
     * @param resultMap The HashMap containing class-type mappings
     * @param clazz     The class type to retrieve
     * @return The list of objects, or an empty list if not found
     */
    public static <T> List<T> getList(HashMap<Class<?>, List<?>> resultMap, Class<T> clazz) {
        if (resultMap != null && resultMap.containsKey(clazz)) {
            return (List<T>) resultMap.get(clazz);
        }
        return List.of(); // Return an empty immutable list to avoid NullPointerException
    }

    /**
     * Generic method to retrieve a single object from a HashMap
     *
     * @param resultMap The HashMap containing class-type mappings
     * @param clazz     The class type to retrieve
     * @return The first object in the list, or null if the list is empty or not found
     */
    public static <T> T getSingle(HashMap<Class<?>, List<?>> resultMap, Class<T> clazz) {
        List<T> list = getList(resultMap, clazz);
        return (!list.isEmpty()) ? list.get(0) : null;
    }
}
