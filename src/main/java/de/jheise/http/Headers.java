package de.jheise.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.jheise.util.CollectionUtil.deepCopy;

public class Headers {

    private Map<String, List<String>> headers;

    public Headers(Map<String, List<String>> headers) {

        if(headers == null || headers.isEmpty()) {
            headers = Collections.unmodifiableMap(Collections.EMPTY_MAP);
            return;
        }

        this.headers = deepCopy(headers);
    }

    public int size() {
        return headers.size();
    }

    public List<String> getValues(String key) {
        return headers.get(key);
    }

    public boolean isMultiValued(String key) {
        return headers.containsKey(key) && headers.get(key).size() > 1;
    }
}
