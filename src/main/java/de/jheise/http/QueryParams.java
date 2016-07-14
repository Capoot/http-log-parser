package de.jheise.http;

import java.util.*;
import java.util.stream.Collectors;

public class QueryParams {

    private final Map<String, List<String>> params;

    public QueryParams(HashMap<String, List<String>> queryParams) {
        params = deepCopy(queryParams);
    }

    private Map<String, List<String>> deepCopy(HashMap<String, List<String>> queryParams) {

        Map<String, List<String>> params = new HashMap<>();
        for(String key : queryParams.keySet()) {
            List<String> origin = queryParams.get(key);
            List<String> destination = new ArrayList<>(origin.size());
            destination.addAll(origin);
            params.put(key, Collections.unmodifiableList(destination));
        }
        return Collections.unmodifiableMap(params);
    }

    public int size() {
        return params.size();
    }

    public List<String> get(String key) {
        return params.get(key);
    }
}
