package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model {
	
	Set <RichWord> dictionary;
	
	public Model () {
		this.dictionary = new HashSet <> () ;
	}
	
	public void add(RichWord s) {
		dictionary.add(s);
	}
	
	public void clear() {
		this.dictionary.clear();
	}	
	
	public boolean findWord(String input) {
		boolean res = false;
		
		for(RichWord r : dictionary)
			if(r.getWord().equals(input)) {
				res = true;
				break;
			}
			
			
		return res;
	}
	
	public Set <String> getUncorrectWord(String input) {
		String[] parole = input.split(" ");
		Set <String> result = new HashSet <> ();
		
		for(int i=0; i<parole.length; i++) {
			if(!this.findWord(parole[i]))
				result.add(parole[i]);
		}		
		return result;
	}
	
	public void loadDictionary(String lingua) throws UnexpectedException {
    	if(lingua.equalsIgnoreCase("English") || lingua.equalsIgnoreCase("Inglese") || lingua.equalsIgnoreCase("Italiano") || lingua.equalsIgnoreCase("italian") ) {
    		try {
				FileReader fr = new FileReader("src/main/resources/"+lingua+".txt");
				BufferedReader br = new BufferedReader(fr);
				
				String line;
				while((line = br.readLine()) != null) {
					this.add(new RichWord(line));
				}
				br.close();
				fr.close();
				
			} catch (FileNotFoundException e) {
				if(lingua.equalsIgnoreCase("italiano"))
					throw new UnexpectedException("Non è stato possibile trovare il dizionario della lingua selezionata!");
				else if(lingua.equalsIgnoreCase("english"))
					throw new UnexpectedException("Oops! An error occured. The dictionary cannot be opened. Try Later.");
				e.printStackTrace();
				return;
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
    		
    	} else
    		throw new UnexpectedException("Oops! Si è verificato un errore inaspettato. Riprovare.");
    }

}
