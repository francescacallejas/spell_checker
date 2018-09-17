/*Francesca Callejas
 *ffc2108
 *
 *HW 4
 *SpellChecker checks that the words in a given text file exist in a given
 *dictionary. It also suggests words for those that are spelled wrong based on
 *the removal of a letter, addition of a letter, or swapping of adjacent letters
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SpellChecker {

      
	static HashMap<String, ArrayList<String>> suggestions; 	
	public static void main(String[] args) throws IOException{


	//HashMap<String, ArrayList<String>> suggestions;	

	//read in files
	BufferedReader dictionary = new BufferedReader(new FileReader(new File(args[0])));
	BufferedReader text = new BufferedReader(new FileReader(new File(args[1])));
	

	//create HashSet to take in dictionary
	HashSet<String> dict = new HashSet<String>();
	String word;
        while((word = dictionary.readLine()) != null){
            word = word.toLowerCase();
	    dict.add(word);
        }

	//create HashMap for suggested words
	suggestions = new HashMap<String, ArrayList<String>>();

	//check that each word in text is in the dictionary
	check(dict, text);

	System.out.println("Here are the misspelled words and their suggestions: ");
	System.out.println(suggestions); 

    }


    public static void check(HashSet dictionary, 
	BufferedReader toCheck) throws IOException {
       String line;
       String trimmedWord;
       boolean contains;
        while ((line = toCheck.readLine()) != null) {
          
	    String words[];
	
	    if(line.isEmpty()){
		break; 
	    }else{
                words = line.split("\\s+");
	    }


	    //trims characters that are not alphanumeric from the words in each line
	    for (String word : words) {
               
		trimmedWord = word.toLowerCase();
	        
                if (!((trimmedWord.charAt(0) >= 97 && trimmedWord.charAt(0) <= 
		122) || (trimmedWord.charAt(0) >= 48 && trimmedWord.charAt(0) 
		<= 57))) {
                   trimmedWord = trimmedWord.substring(1);
		}
                   
			        
		if (!((trimmedWord.charAt(trimmedWord.length() - 1) >= 97 && 
		trimmedWord.charAt(trimmedWord.length() - 1) <= 122) ||
                (trimmedWord.charAt(trimmedWord.length() - 1) >= 48 
		&& trimmedWord.charAt(trimmedWord.length() - 1) <= 57))) {
                    if (trimmedWord == null) {
                           trimmedWord = trimmedWord.substring(0, trimmedWord.length() - 1);
                       } else {
                           trimmedWord = trimmedWord.substring(0, trimmedWord.length() - 1);
                       }
                    }

		
                    contains = dictionary.contains(trimmedWord);
		    

                    if (!contains) {
                        generateSuggestions(dictionary, trimmedWord);
                        
		    }

              


        }
        }
	


    }


    //generates suggestions for a word based on removal of a letter, addition of
    //a letter, or rearranging of letters
    public static void generateSuggestions(HashSet dictionary, String word){


	//creates string array with all letters in the alphabet
	String[] alph = new String[27];
        alph[0] = "'";
        int num = 1;
        Character alphabet;
        for(int x = 97; x < 123; x++){
            alphabet = (char) x;
            alph[num] = alphabet.toString();
            num++;
        }

	int x = 0;
        String suggestedWord;
        ArrayList<String> suggestedWords = new ArrayList<String>();
        
	while(x < word.length()){	
	    

	    //checking removal of a letter
	    if(x == 0){
                suggestedWord = word.substring(1, word.length());
            }else if(x == word.length()-1){
                suggestedWord = word.substring(0, word.length()-1);
            }else{
                suggestedWord = word.substring(0,x) + word.substring(x+1, 
		word.length());
            }
            if (dictionary.contains(suggestedWord) && !suggestedWords.contains
	    (suggestedWord)){

                suggestedWords.add(suggestedWord);
            }

	  
	    //checking the addition of a letter
	    for(String letter : alph){
	        
		if(x == 0){
		    suggestedWord = letter + word;
		}else if(x == word.length()-1){
		    suggestedWord = word + letter;  

		}else {
                    suggestedWord = word.substring(0,x) + letter + 
		    word.substring(x, word.length());
		   		
                }if(dictionary.contains(suggestedWord) && 
		!suggestedWords.contains(suggestedWord)){

		    suggestedWords.add(suggestedWord);
                }

            }

	    x++;

	}

	//check swapping of adjacent letters
	for(int a = 1; a < word.length(); a++){
 	    String suggestedWordSwap = word; 
	    if(a == 1){
		suggestedWordSwap = word.substring(a, a+1) + 
		word.substring(0,a) + word.substring(a+1, word.length());
		
	    }else {
		suggestedWordSwap = word.substring(0, a-1) + word.substring(a, 
		a+1) + word.substring(a-1, a) + word.substring(a+1, 
		word.length());

            }if(dictionary.contains(suggestedWordSwap) && 
	    !suggestedWords.contains(suggestedWordSwap)){
		suggestedWords.add(suggestedWordSwap);
            }
        }


	//add suggested words to HashSet
	suggestions.put(word, suggestedWords);


    }

}



	


	

