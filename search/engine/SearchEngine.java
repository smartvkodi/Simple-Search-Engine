package search.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class SearchEngine {

    List<String> directIndex = new ArrayList<>();
    InvertedIndex invertedIndex = new InvertedIndex();

    public SearchEngine(String[] args) {
        Map<String, String> configMap = parseArguments(args);
        String importFile = configMap.get("--data") != null ? configMap.get("--data") : "";

        if (!importFile.isBlank()) {
            try (BufferedReader reader = new BufferedReader(new FileReader("./" + importFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    directIndex.add(line);
                    int idx = directIndex.size() - 1;

                    for (String token : line.toLowerCase().split("\\s+")) {
                        if (!invertedIndex.containsKey(token)) {
                            invertedIndex.put(token, new HashSet<>());
                        }
                        invertedIndex.get(token).add(idx);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(importFile + " not found\n");
            } catch (IOException e) {
                System.out.println(importFile + " is not accessible\n");
            }
        } else {
            System.out.println("No import file\n");
        }
    }

    private Map<String, String> parseArguments(String[] args) {
        int argsNumber = args.length;
        Map<String, String> configMap = new HashMap<>(argsNumber);

        for (int i = 0; i < argsNumber; i++) {
            String token = args[i];
            String value = "";
            if (i + 1 < argsNumber) {
                value = args[i + 1];
            }
            if (token.startsWith("--")) {
                configMap.put(token.toLowerCase(), value);
            }
        }

        return configMap;
    }

    Set<Integer> getAllInformation() {
        return invertedIndex.getAllIndexesSet();
    }

    List<String> getDirectIndex() {
        return this.directIndex;
    }

    Set<Integer> searchWithAllTerms(Set<String> terms) {
        return invertedIndex.intersection(terms);
    }

    Set<Integer> searchWithAnyOfTerms(Set<String> terms) {
        return invertedIndex.union(terms);
    }

    Set<Integer> searchWithNoneOfTerms(Set<String> terms) {
        return invertedIndex.difference(terms);
    }
}
