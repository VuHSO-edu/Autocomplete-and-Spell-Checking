package hus.daa.app.service;

import java.util.List;

public interface AutocompleteService {
    void loadDictionary();
    List<String> getSuggestions(String prefix, int limit);
}
