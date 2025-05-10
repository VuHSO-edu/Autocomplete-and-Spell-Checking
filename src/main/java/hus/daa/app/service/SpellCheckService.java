package hus.daa.app.service;

import java.util.List;

public interface SpellCheckService {
    void loadDictionary();
    List<String> getCorrectionsLD(String word, int maxDistance, int limit);
    List<String> getCorrectionsTST(String word, int limit);
}
