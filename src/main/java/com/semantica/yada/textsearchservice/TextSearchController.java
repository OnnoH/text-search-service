package com.semantica.yada.textsearchservice;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TextSearchController {
    @GetMapping(value = "/search/{searchTerm}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FoundDocument>> searchText(@PathVariable String searchTerm) throws Exception {
        List<FoundDocument> searchResult = TextSearch.searchIndex(searchTerm);
        return ResponseEntity.ok().body(searchResult);
    }
}
