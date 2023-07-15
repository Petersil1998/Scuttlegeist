package net.petersil98.scuttlegeist.collection;

import net.petersil98.scuttlegeist.data.VocabTerm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VocabTerms {

    private static Map<String, VocabTerm> vocabTerms;

    public static VocabTerm getVocabTerm(String id) {
        return vocabTerms.get(id);
    }

    public static List<VocabTerm> getVocabTerms() {
        return new ArrayList<>(vocabTerms.values());
    }
}
