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
	
	List <RichWord> dictionary;
	
	public Model () {
		this.dictionary = new ArrayList <> () ;
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
	
//	public Set <String> getUncorrectWord(String input) {
//		String[] parole = input.split(" ");
//		Set <String> result = new HashSet <> ();
//		
//		for(int i=0; i<parole.length; i++) {
//			if(!this.findWord(parole[i]))
//				result.add(parole[i]);
//		}		
//		return result;
//	}
	
	public Set<String> spellCheckerLinear(String input) {
		String[] parole = input.split(" ");
		Set <String> result = new HashSet <> ();
		
		for(String s : parole) {
			if(!this.findWord(s))
				result.add(s);
		}
		
		return result;
	}
	
	public boolean eseguiRicercaDichotomic(String s) {
		boolean found = false;
		List <Integer> index = new ArrayList<> ();
		index.add(this.dictionary.size()/2);
		int i = 0;
		boolean f1 = false;
		boolean f2 = false;
		do {
			if(s.compareTo(this.dictionary.get(index.get(i)).getWord())==0) {
				found = true;
			}
			// TODO: entra sempre in questo else if e non va nell'altro!
			else if(s.compareTo(this.dictionary.get(i).getWord()) > 0) {
				if(!f2) {
					index.add(index.get(i) + (this.dictionary.size()-index.get(i))/2); 
					f1 = true;
				} else {
					index.add(index.get(i) + (index.get(i-1)-index.get(i))/2);
				}
			} else if(s.compareTo(this.dictionary.get(i).getWord()) < 0) {
				if(!f1) {
					index.add(index.get(i) - (this.dictionary.size()-index.get(i))/2);
					f2 = true;
				}
				else {
					index.add(index.get(i) - (index.get(i)-index.get(i-1))/2);
				}			
			}
			i++;
			
		} while(!found && index.get(i)!=0 && index.get(i)!=(this.dictionary.size()-1));
		
		return found;
	}
	
	public Set<String> spellCheckerDichotomic(String input) {
		String[] parole = input.split(" ");
		Set <String> result = new HashSet <> ();	
		
		for(String s : parole) {
			boolean b = this.eseguiRicercaDichotomic(s);		
			if(!b)
				result.add(s);
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
