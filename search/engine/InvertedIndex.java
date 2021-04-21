package search.engine;

import java.util.*;

class InvertedIndex extends HashMap<String, Set<Integer>> {

    Map<String, Set<Integer>> mapIndexes = new HashMap<>();

    Set<Integer> get(String key) {
        return mapIndexes.get(key);
    }

    public Set<Integer> put(String key, Set<Integer> value) {
        return mapIndexes.put(key, value);
    }

    boolean containsKey(String key) {
        return mapIndexes.containsKey(key);
    }

    Set<Integer> getAllIndexesSet() {
        Set<Integer> allIndexes = new LinkedHashSet<>();

        for (String key : mapIndexes.keySet()) {
            allIndexes.addAll(mapIndexes.get(key));
        }

        return allIndexes;
    }

    Set<Integer> union(Set<String> keys) {
        Set<Integer> unionSet = null;

        if (keys.size() > 0) {
            for (String key : keys) {
                if(mapIndexes.containsKey(key)) {
                    if (unionSet == null) {
                        unionSet = new HashSet<>();
                    }
                    unionSet.addAll(get(key));
                }
            }
        }

        return unionSet;
    }

    Set<Integer> intersection(Set<String> keys) {
        Set<Integer> intersectionSet = null;

        if (keys.size() > 0) {
            for (String key : keys) {
                if (mapIndexes.containsKey(key)) {
                    if (intersectionSet == null) {
                        intersectionSet = new HashSet<>(get(key));
                    } else {
                        intersectionSet.retainAll(mapIndexes.get(key));
                    }
                }
            }
        }
        return intersectionSet;
    }

    Set<Integer> difference(Set<String> keys) {
        Set<Integer> differenceSet = getAllIndexesSet();

        Set<Integer> unionSet = union(keys);
        if (unionSet != null) {
            differenceSet.removeAll(unionSet);
        }

        return differenceSet;
    }
}
