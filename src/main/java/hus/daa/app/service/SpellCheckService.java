package hus.daa.app.service;

import java.util.List;

public interface SpellCheckService {
    void loadDictionary();
    List<String> getCorrections(String word, int maxDistance, int limit);

}
