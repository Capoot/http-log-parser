package de.jheise.util;

import java.util.*;

public class CollectionUtil {

    public static Map<String, List<String>> deepCopy(Map<String, List<String>> queryParams) {

        Map<String, List<String>> params = new HashMap<>();
        for(String key : queryParams.keySet()) {
            List<String> origin = queryParams.get(key);
            List<String> destination = new ArrayList<>(origin.size());
            destination.addAll(origin);
            params.put(key, Collections.unmodifiableList(destination));
        }
        return Collections.unmodifiableMap(params);
    }
}
