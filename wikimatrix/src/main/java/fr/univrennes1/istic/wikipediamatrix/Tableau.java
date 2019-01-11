package fr.univrennes1.istic.wikipediamatrix;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class Tableau {
	
	Header entete;
	List<Ligne> body;
	
	public Tableau() {
		this.body= new ArrayList<Ligne>();
	}
	
	public void genererCSV(String cheminFichierSortie) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(cheminFichierSortie));
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
		csvPrinter.printRecord(this.entete.getLsCellules());
		for (Ligne ligne : this.body) {
			csvPrinter.printRecord(ligne.getLsCellules());
		}
		csvPrinter.close();
	}

	public void setEntete(Header entete) {
		this.entete = entete;
	}

	public void setBody(List<Ligne> body) {
		this.body = body;
	}

	public Header getEntete() {
		return entete;
	}

	public List<Ligne> getBody() {
		return body;
	}
	


}
