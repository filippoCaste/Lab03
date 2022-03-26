package it.polito.tdp.spellchecker.model;



public class RichWord {
	
	private String parola;
	
	public RichWord(String line) {
		//  Auto-generated constructor stub
		this.parola = line;
	}

	public String getWord() {
		return parola;
	}
	
	@Override
	public boolean equals (Object o1) {
		RichWord other = (RichWord) o1;
		if(this.parola.compareTo(other.getWord())==0)
			return true;
		else
			return false;
		
	}

//	@Override
//	public int hashCode() {
//		return Objects.hash(parola);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		RichWord other = (RichWord) obj;
//		return Objects.equals(parola, other.parola);
//	}
//	
	
}
