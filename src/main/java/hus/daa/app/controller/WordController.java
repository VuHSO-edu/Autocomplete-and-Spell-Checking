package hus.daa.app.controller;

import hus.daa.app.service.AutocompleteService;
import hus.daa.app.service.SpellCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordController {
    private final AutocompleteService autocompleteService;
    private final SpellCheckService spellCheckService;


    @GetMapping("/autocomplete")
    public ResponseEntity<Map<String, Object>> autocomplete(@RequestParam String prefix,
                                                            @RequestParam(defaultValue = "10") int limit) {

        Map<String, Object> response = new HashMap<>();
        response.put("prefix", prefix);
        response.put("suggestions", autocompleteService.getSuggestions(prefix, limit));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/spellcheck")
    public ResponseEntity<Map<String, Object>> spellCheck(@RequestParam String word,
                                                          @RequestParam(defaultValue = "2") int maxDistance,
                                                          @RequestParam(defaultValue = "10") int limit) {

        Map<String, Object> response = new HashMap<>();
        response.put("word", word);

        List<String> corrections = spellCheckService.getCorrections(word, maxDistance, limit);
        response.put("isCorrect", corrections.isEmpty());
        response.put("suggestions", corrections);

        return ResponseEntity.ok(response);
    }

}
