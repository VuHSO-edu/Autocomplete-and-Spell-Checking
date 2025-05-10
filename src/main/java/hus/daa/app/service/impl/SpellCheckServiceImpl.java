package hus.daa.app.service.impl;

import hus.daa.app.algorithm.levenshtein_distance.LevenshteinDistance;
import hus.daa.app.algorithm.tst.TernarySearchTree;
import hus.daa.app.entity.Words;
import hus.daa.app.repository.WordRepository;
import hus.daa.app.service.SpellCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpellCheckServiceImpl implements SpellCheckService {
    private final WordRepository wordRepository;
    private final TernarySearchTree tst;
    private List<String> dictionary;

    @Autowired
    public SpellCheckServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
        this.tst = new TernarySearchTree();
        loadDictionary();
    }

    @Override
    public void loadDictionary() {
        List<Words> words = wordRepository.findAll();
        dictionary = words.stream().map(Words::getWord).collect(Collectors.toList());

        for (String word : dictionary) {
            tst.insert(word);
        }

    }

    @Override
    public List<String> getCorrectionsLD(String word, int maxDistance, int limit) {
        if (tst.search(word)) {
            return new ArrayList<>();
        }

        List<String> corrections = new ArrayList<>();
        for (String dictWord : dictionary) {
            int distance = LevenshteinDistance.calculate(word, dictWord);
            if (distance <= maxDistance) {
                corrections.add(dictWord);
            }
        }

        corrections.sort((a, b) -> Integer.compare(
                LevenshteinDistance.calculate(word, a),
                LevenshteinDistance.calculate(word, b)
        ));

        if (corrections.size() > limit) {
            return corrections.subList(0, limit);
        }

        return corrections;
    }

    @Override
    public List<String> getCorrectionsTST(String word, int limit) {
        if (tst.search(word)) {
            return new ArrayList<>();
        }

        List<String> corrections = tst.getWordsWithPrefix(word);
        return corrections.size() > limit ? corrections.subList(0, limit) : corrections;
    }
}
