package search;

import java.io.*;
import java.util.*;

public class PersonAgenda implements Agenda {
    List<String> persons = new ArrayList<>();
    Map<String, List<Integer>> invertedIndex = new LinkedHashMap<>();

    @Override
    public void loadDataFromSource(String[] args) {

        Map<String, String> configMap = parseArguments(args);
        String importFile = configMap.get("--data") != null ? configMap.get("--data") : "";

        if (!importFile.isBlank()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(importFile))) {
                String person;
                while ((person = reader.readLine()) != null) {
                    int idx = persons.size();
                    this.persons.add(person);
                    for (String token : person.toLowerCase().split("\\s+")) {
                        if (!invertedIndex.containsKey(token)) {
                            invertedIndex.put(token, new ArrayList<>());
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

    @Override
    public void getPersons(String searchTerm) {
        List<Integer> indexList = invertedIndex.get(searchTerm);
        if (indexList != null) {
            for (Integer idx : indexList) {
                System.out.println(persons.get(idx));
            }
        } else {
            System.out.println("No matching people found.");
        }
    }

    @Override
    public void getAllPersons() {
        for (String person : persons) {
            System.out.println(person);
        }
        System.out.println();
    }
}
