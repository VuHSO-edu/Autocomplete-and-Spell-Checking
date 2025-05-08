package hus.daa.app.service.impl;

import hus.daa.app.algorithm.tst.TernarySearchTree;
import hus.daa.app.entity.Words;
import hus.daa.app.repository.WordRepository;
import hus.daa.app.service.AutocompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutocompleteServiceImpl implements AutocompleteService {
    private final WordRepository wordRepository;
    private final TernarySearchTree tst;

    @Autowired
    public AutocompleteServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
        this.tst = new TernarySearchTree();
        loadDictionary();
    }


    @Override
    public void loadDictionary() {
        List<Words> words = wordRepository.findAll();
        for (Words word : words) {
            tst.insert(word.getWord());
        }
    }

    @Override
    public List<String> getSuggestions(String prefix, int limit) {
        List<String> suggestions = tst.getWordsWithPrefix(prefix);
        if (suggestions.size() > limit) {
            return suggestions.subList(0, limit);
        }
        return suggestions;
    }
}
