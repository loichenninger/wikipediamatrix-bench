package fr.univrennes1.istic.wikipediamatrix;

import java.util.ArrayList;
import java.util.List;

public class Ligne {
	
	private List<String> lsCellules;
	
	public Ligne() {
		this.lsCellules = new ArrayList<String>();
	}

	public void setLigne(List<String> ligne) {
		this.lsCellules = ligne;
	}
	
	public void addCell(String cellule) {
		this.lsCellules.add(cellule);
	}

	public List<String> getLsCellules() {
		return lsCellules;
	}

	public void setLsCellules(List<String> lsCellules) {
		this.lsCellules = lsCellules;
	}
	
	
}
