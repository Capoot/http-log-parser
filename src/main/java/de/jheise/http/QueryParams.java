package de.jheise.http;

import java.util.*;

import static de.jheise.util.CollectionUtil.deepCopy;

public class QueryParams {

    private final Map<String, List<String>> params;

    public QueryParams(HashMap<String, List<String>> queryParams) {

        if(queryParams == null || queryParams.isEmpty()) {
            params = Collections.unmodifiableMap(Collections.EMPTY_MAP);
            return;
        }

        params = deepCopy(queryParams);
    }

    public int size() {
        return params.size();
    }

    public List<String> getValues(String key) {
        return params.get(key);
    }

    public boolean isMultiValued(String key) {
        return params.containsKey(key) && params.get(key).size() > 1;
    }
}
