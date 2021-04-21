package search.engine;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {
    Scanner scanner;
    SearchEngine searchEngine;

    public void run(String[] args) {
        this.searchEngine = new SearchEngine(args);

        try (Scanner scanner = new Scanner(System.in)) {
            this.scanner = scanner;

            boolean continueSearching = true;
            while (continueSearching) {
                System.out.println("\n=== Menu ===");
                System.out.println("1. Find a person");
                System.out.println("2. Print all people");
                System.out.println("0. Exit");

                try {
                    int option = Integer.parseInt(scanner.nextLine());
                    switch (option) {
                        case 1:
                            System.out.println("Select a matching strategy: ALL, ANY, NONE");
                            String searchStrategy = scanner.nextLine().toLowerCase();
                            switch (searchStrategy) {
                                case "all":
                                    System.out.println("Enter a name or email to search all suitable people.");
                                    displayToConsole(searchEngine.searchWithAllTerms(getTermsFromConsole()));
                                    break;
                                case "none":
                                    System.out.println("Enter a name or email to search all different people.");
                                    displayToConsole(searchEngine.searchWithNoneOfTerms(getTermsFromConsole()));
                                    break;
                                case "any":
                                default:
                                    System.out.println("Enter a name or email to search all suitable people.");
                                    displayToConsole(searchEngine.searchWithAnyOfTerms(getTermsFromConsole()));
                                    break;
                            }
                            break;
                        case 0:
                            System.out.println("\nBye!");
                            continueSearching = false;
                            break;
                        case 2:
                        default:
                            System.out.println("\n=== List of people ===");
                            displayToConsole(searchEngine.getAllInformation());
                            break;
                    }
                } catch (NumberFormatException e) {
                    // will show the menu again
                }
            }
        }
    }

    private Set<String> getTermsFromConsole() {
        return Arrays.stream(scanner.nextLine().toLowerCase().split("\\s+"))
                .collect(Collectors.toSet());
    }

    private void displayToConsole(Set<Integer> indexes) {
        if (indexes != null && indexes.size() > 0) {
            System.out.println(indexes.size() + " persons found:");
            indexes.forEach(idx -> System.out.println(searchEngine.getDirectIndex().get(idx)));
        } else {
            System.out.println("No matching people found.");
        }
    }
}
