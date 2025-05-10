package hus.daa.app.service;

import java.util.List;

public interface AutocompleteService {
    void loadDictionary();
    List<String> getSuggestionsTST(String prefix, int limit);

    List<String> getSuggestionsLD(String prefix, int maxDistance, int limit);
}
