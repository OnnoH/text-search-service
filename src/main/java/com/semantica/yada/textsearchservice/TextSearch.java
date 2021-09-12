package com.semantica.yada.textsearchservice;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TextSearch {

    public static List<FoundDocument> searchIndex(String searchTerm) throws Exception
    {
        String indexDirectory = "/tmp/index-dir";
        List<FoundDocument> searchResults = new ArrayList<FoundDocument>();
        IndexSearcher searcher = null;
        try
        {
            Directory indexReadFromDir =
                    FSDirectory.open(Paths.get(indexDirectory));

            searcher = new IndexSearcher(DirectoryReader.open(indexReadFromDir));
            QueryBuilder queryBuilder = new QueryBuilder(new StandardAnalyzer());
            Query query = queryBuilder.createPhraseQuery("fulltext", searchTerm);
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
            booleanQuery.add(query, BooleanClause.Occur.SHOULD);
            BooleanQuery finalQuery = booleanQuery.build();
            TopDocs allFound = searcher.search(finalQuery, 100);
            if (allFound.scoreDocs != null) {
                for (ScoreDoc scoreDoc : allFound.scoreDocs) {
                    System.out.println("Score: " + scoreDoc.score);
                    int documentIndex = scoreDoc.doc;
                    Document documentRetrieved = searcher.doc(documentIndex);
                    if (documentRetrieved != null) {
                        FoundDocument documentToAdd = new FoundDocument();
                        IndexableField field = documentRetrieved.getField("id");
                        if (field != null) {
                            documentToAdd.setUuid(field.stringValue());
                        }
                        searchResults.add(documentToAdd);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResults;
    }

}
