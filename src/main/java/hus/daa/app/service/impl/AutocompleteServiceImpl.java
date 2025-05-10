package hus.daa.app.service.impl;

import hus.daa.app.algorithm.levenshtein_distance.LevenshteinDistance;
import hus.daa.app.algorithm.tst.TernarySearchTree;
import hus.daa.app.entity.Words;
import hus.daa.app.repository.WordRepository;
import hus.daa.app.service.AutocompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutocompleteServiceImpl implements AutocompleteService {
    private final WordRepository wordRepository;
    private final TernarySearchTree tst;
    private List<String> dictionary;

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
        dictionary = words.stream().map(Words::getWord).collect(Collectors.toList());
    }

    @Override
    public List<String> getSuggestionsTST(String prefix, int limit) {
        List<String> suggestions = tst.getWordsWithPrefix(prefix);
        if (suggestions.size() > limit) {
            return suggestions.subList(0, limit);
        }
        return suggestions;
    }

    @Override
    public List<String> getSuggestionsLD(String prefix, int maxDistance, int limit) {
        List<String> suggestions = new ArrayList<>();
        for (String word : dictionary) {
            if (LevenshteinDistance.calculate(prefix, word) <= maxDistance) {
                suggestions.add(word);
            }
        }

        suggestions.sort((a, b) -> Integer.compare(
                LevenshteinDistance.calculate(prefix, a),
                LevenshteinDistance.calculate(prefix, b)
        ));

        return suggestions.size() > limit ? suggestions.subList(0, limit) : suggestions;
    }

}
