package com.source.core;

import java.util.*;


public class CrossWord {
	
	
	
    private static final int TABLE_LENGTH = 13;
    private static final String SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    
    public static char[][] generateTable(int TABLE_LENGTH, Stack<String> arrayWord) throws Exception{
    	//Table with 2 dimensional arrays 
    	char[][] table = new char[TABLE_LENGTH][TABLE_LENGTH];
    	
    	//List of table content
    	List<Character> result = new ArrayList<>();
    	for (int i = 0; i< TABLE_LENGTH*TABLE_LENGTH; i ++) {
    		result.add('a');
    	}
    	
    	//Calculate number of characters in word list
    	int character = 0;
        for (int i = 0; i < arrayWord.size(); i++) {
        	character += arrayWord.get(i).length();
        }
        
        character ++;
        
        //Check Table length eligible 
        //Throw exception if the size is lower than number of character
        if (TABLE_LENGTH*TABLE_LENGTH < character) {
			throw new Exception ("Increase the table length");
        }
        
        System.out.println("array before sort: ");
        System.out.println(arrayWord);
        arrayWord = sortWordArrayByLength(arrayWord);
        System.out.println("array after sort: ");
        System.out.println(arrayWord);
        if (addListWordToTable(arrayWord, table,TABLE_LENGTH)) {
            printTable("RESULT", table,TABLE_LENGTH,result);
        }
        printAnswerKey();
        
        System.out.println(result);
        
        return table;
        
    }
    
	public static void main(String[] args) throws Exception {
    	//Add initial value to list 
    	//for (int i = 0; i< TABLE_LENGTH*TABLE_LENGTH; i ++) {
    		//result.add('a');
    	//}
        
        Stack<String> arrayWord = new Stack<>();
        arrayWord.push("CROSSWORD");
        arrayWord.push("exception");
        arrayWord.push("Reverse");
        arrayWord.push("STRING");
        arrayWord.push("LIGHT");
        arrayWord.push("FIGHTING");
        arrayWord.push("SAW");
        arrayWord.push("printf");
        arrayWord.push("functional");
        arrayWord.push("generate");
        arrayWord.push("character");
        arrayWord.push("involves");
        arrayWord.push("birthday");
        arrayWord.push("connection");
        arrayWord.push("positively");
        arrayWord.push("holiday");
        arrayWord.push("hollywood");
        arrayWord.push("event");
        arrayWord.push("announce");
        
        generateTable(13, arrayWord);
       
    }

    private static boolean addListWordToTable(Stack<String> wordArray, char[][] table, int TABLE_LENGTH) {
        if (wordArray.size() <= 0) return true;
        else {
            Random random = new Random();
            List<Integer> gridPositions = new ArrayList<>();
            int row = random.nextInt(TABLE_LENGTH);
            int column = random.nextInt(TABLE_LENGTH);
            List<Integer> listContainWordGridPosition = new ArrayList<>();
            String word = wordArray.peek();
            new WordPositionManagement();
			WordPositionManagement wordPositionManagement =  WordPositionManagement.getInstance();
            while (!isFullGridPosition(gridPositions, TABLE_LENGTH)) {
                if (isContainPosition(row, column, gridPositions, TABLE_LENGTH)) {
                    row = random.nextInt(TABLE_LENGTH);
                    column = random.nextInt(TABLE_LENGTH);
                    continue;
                }
                int direction = random.nextInt(3);
                word = revertWord(word, random);
                char[] wordCharArray = word.toUpperCase().toCharArray();
                if (isValid(table, wordCharArray, row, column, direction, TABLE_LENGTH)) {
                    wordArray.pop();
                    addContainWordToList(row, column, direction, table, wordCharArray, listContainWordGridPosition, TABLE_LENGTH);
                    addWordCharArrayToTable(row, column, direction, table, wordCharArray);
                    wordPositionManagement.addWordPosition(row, column, word, direction);
//                    System.out.println("---->Add: " + word);
//                    printTable("CREATE", table);
                    if (addListWordToTable(wordArray, table,TABLE_LENGTH)) return true;
                    else {
                        removeWordCharArray(row, column, direction, table, wordCharArray, listContainWordGridPosition,TABLE_LENGTH);
                        wordArray.push(word);
                        wordPositionManagement.removeWordPosition(word);
//                        System.out.println("---->REMOVE: " + word);
//                        printTable("Drop word: ", table);
                    }
                }
            }
            return false;
        }
    }

    private static void addContainWordToList(int row, int column, int direction, char[][] table, 
    		char[] wordCharArray, List<Integer> listContainWordGridPosition, int TABLE_LENGTH) {
        if (direction == 0) {
            for (int i = 0; i < wordCharArray.length; i++) {
                if (table[row][column + i] == wordCharArray[i]) {
                    int grdPosition = row * TABLE_LENGTH + column + i;
                    listContainWordGridPosition.add(grdPosition);
                }
            }
        } else if (direction == 1) {
            for (int i = 0; i < wordCharArray.length; i++) {
                if (table[row + i][column] == wordCharArray[i]) {
                    int grdPosition = (row + i) * TABLE_LENGTH + column;
                    listContainWordGridPosition.add(grdPosition);
                }
            }
        } else {
            for (int i = 0; i < wordCharArray.length; i++) {
                if (table[row + i][column + i] == wordCharArray[i]) {
                    int grdPosition = (row + i) * TABLE_LENGTH + column + i;
                    listContainWordGridPosition.add(grdPosition);
                }
            }
        }
    }

    private static void removeWordCharArray(int row, int column, int direction, char[][] table, 
    		char[] wordCharArray, List<Integer> listContainWordGridPosition, int TABLE_LENGTH) {
        if (direction == 0) {
            for (int i = 0; i < wordCharArray.length; i++) {
                int grdPosition = row * TABLE_LENGTH + (column + i);
                if (!listContainWordGridPosition.contains(grdPosition))
                    table[row][column + i] = '\0';
            }
        } else if (direction == 1) {
            for (int i = 0; i < wordCharArray.length; i++) {
                int grdPosition = (row + i) * TABLE_LENGTH + column;
                if (!listContainWordGridPosition.contains(grdPosition))
                    table[row + i][column] = '\0';
            }
        } else {
            for (int i = 0; i < wordCharArray.length; i++) {
                int grdPosition = (row + i) * TABLE_LENGTH + (column + i);
                if (!listContainWordGridPosition.contains(grdPosition))
                    table[row + i][column + i] = '\0';
            }
        }
        listContainWordGridPosition.clear();
    }

    private static void addWordCharArrayToTable(int row, int column, int direction, char[][] table, char[] wordCharArray) {
        if (direction == 0) {
            for (int i = 0; i < wordCharArray.length; i++) {
                table[row][column + i] = wordCharArray[i];
            }
        } else if (direction == 1) {
            for (int i = 0; i < wordCharArray.length; i++) {
                table[row + i][column] = wordCharArray[i];
            }
        } else {
            for (int i = 0; i < wordCharArray.length; i++) {
                table[row + i][column + i] = wordCharArray[i];
            }
        }
    }

    private static boolean isContainPosition(int row, int column, List<Integer> gridPositions, int TABLE_LENGTH) {
        int grdPosition = row * TABLE_LENGTH + column;
        if (gridPositions.contains(grdPosition)) {
            return true;
        }
        System.out.println(grdPosition);
        gridPositions.add(grdPosition);
        return false;
    }

    private static boolean isFullGridPosition(List<Integer> gridPositions, int TABLE_LENGTH) {
        return gridPositions.size() >= (Math.pow(TABLE_LENGTH, 2) - 1);
    }

    private static boolean isValid(char[][] table, char[] wordCharArray, int row, int column, int direction, int TABLE_LENGTH) {
        if (direction == 0) {   //ngang
            if (column > TABLE_LENGTH - wordCharArray.length) return false;
            for (int i = 0; i < wordCharArray.length; i++) {
                if ((table[row][column + i] != '\0' && table[row][column + i] != wordCharArray[i])) {
                    return false;
                }
            }
        } else if (direction == 1) { //doc
            if (row > TABLE_LENGTH - wordCharArray.length) return false;
            for (int i = 0; i < wordCharArray.length; i++) {
                if ((table[row + i][column] != '\0' && table[row + i][column] != wordCharArray[i])) {
                    return false;
                }
            }
        } else if (direction == 2) { //cheo
            if (row > TABLE_LENGTH - wordCharArray.length || column > TABLE_LENGTH - wordCharArray.length) return false;
            for (int i = 0; i < wordCharArray.length; i++) {
                if ((table[row + i][column + i] != '\0' && table[row + i][column + i] != wordCharArray[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    private static Stack<String> sortWordArrayByLength(Stack<String> input) {
        Stack<String> tmpStack = new Stack<>();
        while (!input.isEmpty()) {
            String tmp = input.pop();
            while (!tmpStack.isEmpty() && tmpStack.peek().length() > tmp.length()) {
                input.push(tmpStack.pop());
            }
            tmpStack.push(tmp);
        }
        return tmpStack;
    }

    private static String revertWord(String word, Random random) {
        int revert = random.nextInt(2);
        if (revert == 1) {
            StringBuilder stringBuilder = new StringBuilder(word);
            word = stringBuilder.reverse().toString();
        }
        return word;
    }


    private static int getTotalNumberWord(Stack<String> arrayWord) {
        int totalNumberWord = 0;
        for (String word : arrayWord) {
            totalNumberWord += word.length();
        }
        return totalNumberWord;
    }
    
    public WordPositionManagement returnWordPositionManagement(){
    	WordPositionManagement management = WordPositionManagement.getInstance();
    	return management;
    }

    private static void printAnswerKey() {
        WordPositionManagement management = WordPositionManagement.getInstance();
        for (Map.Entry<String, WordPosition> stringWordPositionEntry : management.getWordPositionMap().entrySet()) {
            System.out.println("word: " + ((Map.Entry) stringWordPositionEntry).getKey() + "- position: " + ((Map.Entry) stringWordPositionEntry).getValue());
        }
        
    }

    private static void printTable(String title, char[][] table, int TABLE_LENGTH, List<Character> result) {
    	String SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        System.out.println(title);
        for (int i = 0; i < TABLE_LENGTH; i++) {
            for (int j = 0; j < TABLE_LENGTH; j++) {
                char charRandom = SALT_CHARS.charAt(random.nextInt(26));
                if (table[i][j] == '\0') {
                	table[i][j] = charRandom;
                }
                result.set(i*TABLE_LENGTH + j, table[i][j]);
                System.out.print((table[i][j] == '\0') ? ("-|") : (table[i][j] + "|"));
            }
            System.out.println();
        }
    }
}
