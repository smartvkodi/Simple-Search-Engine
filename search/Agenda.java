package search;

public interface Agenda {
    void loadDataFromSource(String[] args);
    void getPersons(String searchTerm);
    void getAllPersons();
}
