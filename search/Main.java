package search;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Agenda agenda = new PersonAgenda();
        agenda.loadDataFromSource(args);

        try (Scanner scanner = new Scanner(System.in)) {

            boolean continueSearching = true;
            while (continueSearching) {

                System.out.println("=== Menu ===");
                System.out.println("1. Find a person");
                System.out.println("2. Print all people");
                System.out.println("0. Exit");

                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        System.out.println("Enter a name or email to search all suitable people.");
                        agenda.getPersons(scanner.nextLine());
                        break;
                    case 2:
                        System.out.println("=== List of people ===");
                        agenda.getAllPersons();
                        break;
                    case 0:
                        System.out.println("Bye!\n");
                        continueSearching = false;
                        break;
                    default:
                        System.out.println("Incorrect option! Try again.\n");
                        break;
                }
            }
        }
    }

}
