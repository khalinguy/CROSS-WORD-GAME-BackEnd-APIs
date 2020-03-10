package com.source.core;

import java.util.HashMap;
import java.util.Map;

public class WordPositionManagement {
    private static WordPositionManagement _instance = new WordPositionManagement();
    private Map<String, WordPosition> wordPositionMap = new HashMap<>();

    public WordPositionManagement() {
    }

    public static WordPositionManagement getInstance() {
        return _instance;
    }

    public WordPositionManagement(Map<String, WordPosition> wordPositionMap) {
        this.wordPositionMap = wordPositionMap;
    }

    public Map<String, WordPosition> getWordPositionMap() {
        return wordPositionMap;
    }

    public void setWordPositionMap(Map<String, WordPosition> wordPositionMap) {
        this.wordPositionMap = wordPositionMap;
    }

    public void addWordPosition(int x, int y, String word, int direction) {
        WordPosition wordPosition = new WordPosition(x, y, direction);
        wordPositionMap.put(word, wordPosition);
    }

    public void removeWordPosition(String word) {
        wordPositionMap.remove(word);
    }
}
