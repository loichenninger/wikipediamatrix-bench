package fr.univrennes1.istic.wikipediamatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Extractor {
	
	Document codeSource;
	private List<Tableau> tableaux;
	
	public Extractor () {
		tableaux = new ArrayList<Tableau>();
	}
	
	public void init (String url) throws IOException {
		this.codeSource = Jsoup.connect(url).get();
	}
	
	public Tableau getTableau(Element tableauHtml) {
		//On sélectionne ième premier tableau
		Tableau tableau = new Tableau();
		
		//On définit le header du tableau
		tableau.setEntete(extractHeader(tableauHtml));
		
		//On définit le body
		tableau.setBody(extractBody(tableauHtml));
		return tableau;
	}
	
	public void extractTableaux() {
		//On regarde si il y a des tableaux exploitables sur la page
		if (presenceTableauValide()) {
			Elements tableauxValideHtml = codeSource.getElementsByClass("wikitable sortable");
			for (int i=0;i<tableauxValideHtml.size();i++) {
				tableaux.add(getTableau(tableauxValideHtml.get(i)));
			}
		}
	}
	
	public Header extractHeader(Element tableHtml) {
		Element premiereLigne = tableHtml.select("tr").first();
		Elements nomsColonnes = premiereLigne.select("th");
		Header entete = new Header();
		for (Element colonne : nomsColonnes) {
			entete.addCell(colonne.text());
		}
		return entete;
	}
	
	public List<Ligne> extractBody(Element tableHtml) {
		Elements lignes = tableHtml.select("tr");
		List<Ligne> corpsTableau = new ArrayList<Ligne>();
		for(Element ligne : lignes) {
			Elements cellulesEntete = lignes.select("th");
			Elements cellules = ligne.select("td");
			if (cellules.size()>0) {
				corpsTableau.add(extractLigneBody(ligne));
			}
		}
		return corpsTableau;
	}
	
	public Ligne extractLigneBody(Element ligneHtml) {
		Ligne ligneBody = new Ligne();
		//Si il y a une entete sur la ligne elle est au debut
		Elements cellulesEnt = ligneHtml.select("th");
		if (cellulesEnt.size()>0) {
			for (Element cellule : cellulesEnt) {
				ligneBody.addCell(cellule.text());
			}
		}
		//On extrait ensuite les balises td
		Elements cellulesBody = ligneHtml.select("td");
		for (Element cellule : cellulesBody) {
			ligneBody.addCell(cellule.text());
		}
		return ligneBody;
	}
	
	public int getNombreTableau() {
		return codeSource.select("table").size();
	}
	
	public boolean presenceTableauValide() {
		return (this.codeSource.getElementsByClass("wikitable sortable").size()!=0);
	}

	public List<Tableau> getTableaux() {
		return tableaux;
	}

	public void setTableaux(List<Tableau> tableaux) {
		this.tableaux = tableaux;
	}


}
