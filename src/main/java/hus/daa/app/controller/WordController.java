package hus.daa.app.controller;

import hus.daa.app.service.AutocompleteService;
import hus.daa.app.service.SpellCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordController {
    private final AutocompleteService autocompleteService;
    private final SpellCheckService spellCheckService;


    @GetMapping("/tst/autocomplete")
    public ResponseEntity<Map<String, Object>> autocompleteTST(@RequestParam String prefix,
                                                            @RequestParam(defaultValue = "10") int limit) {

        Map<String, Object> response = new HashMap<>();
        response.put("prefix", prefix);
        response.put("suggestions", autocompleteService.getSuggestionsTST(prefix, limit));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/tst/spellcheck")
    public ResponseEntity<Map<String, Object>> spellCheckTST(@RequestParam String word,
                                                              @RequestParam(defaultValue = "10") int limit) {

        Map<String, Object> response = new HashMap<>();
        response.put("word", word);

        List<String> corrections = spellCheckService.getCorrectionsTST(word, limit);
        response.put("isCorrect", corrections.isEmpty());
        response.put("suggestions", corrections);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ld/autocomplete")
    public ResponseEntity<Map<String, Object>> autocompleteLD(  @RequestParam String perfix,
                                                                @RequestParam(defaultValue = "2") int maxDistance,
                                                                @RequestParam(defaultValue = "10") int limit) {

        Map<String, Object> response = new HashMap<>();
        response.put("prefix", perfix);
        response.put("suggestions", autocompleteService.getSuggestionsLD(perfix,maxDistance, limit));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ld/spellcheck")
    public ResponseEntity<Map<String, Object>> spellCheck(@RequestParam String word,
                                                          @RequestParam(defaultValue = "2") int maxDistance,
                                                          @RequestParam(defaultValue = "10") int limit) {

        Map<String, Object> response = new HashMap<>();
        response.put("word", word);

        List<String> corrections = spellCheckService.getCorrectionsLD(word, maxDistance, limit);
        response.put("isCorrect", corrections.isEmpty());
        response.put("suggestions", corrections);

        return ResponseEntity.ok(response);
    }

}
