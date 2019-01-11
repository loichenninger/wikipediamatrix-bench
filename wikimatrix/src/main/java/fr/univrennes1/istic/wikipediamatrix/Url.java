package fr.univrennes1.istic.wikipediamatrix;

import java.io.IOException;
import org.jsoup.Jsoup;


public class Url {
	
	String chemin;
	
	public Url() {
		
	}
	
	public Boolean estValide() {
		try {
				Jsoup.connect(chemin).get();
			}
		catch(IOException e) {
			return false;
		}
		return true;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}
	
}
