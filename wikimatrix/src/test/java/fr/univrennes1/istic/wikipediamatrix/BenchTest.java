package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

/**
 * 
 * @author HP-ZBOOK-05
 *
 */

public class BenchTest {

	/*
	 *  the challenge is to extract as many relevant tables as possible
	 *  and save them into CSV files  
	 *  from the 300+ Wikipedia URLs given
	 *  see below for more details
	 **/
	@Test
	public void testBenchExtractors() throws Exception {

		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		// directory where CSV files are exported (HTML extractor) 
		String outputDirHtml = "output" + File.separator + "html" + File.separator;
		assertTrue(new File(outputDirHtml).isDirectory());
		// directory where CSV files are exported (Wikitext extractor) 
		String outputDirWikitext = "output" + File.separator + "wikitext" + File.separator;
		assertTrue(new File(outputDirWikitext).isDirectory());

		File file = new File("inputdata" + File.separator + "wikiurls.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String url;
		
		int nbTableaux = 0;
		List<String> motsEntete = new ArrayList<String>();
		int freqMax = 0;
		String motLePlusFrequent="";
		List<Integer> nbColonnes = new ArrayList<Integer>();
		List<Integer> nbLignes = new ArrayList<Integer>();
		List<Integer> nbCellules = new ArrayList<Integer>();
		
		
		int nurl = 0;
		while ((url = br.readLine()) != null) {
			String wurl = BASE_WIKIPEDIA_URL + url; 
			System.out.println("Wikipedia url: " + wurl);
			// TODO: do something with the Wikipedia URL 
			// (ie extract relevant tables for correct URL, with the two extractors)
			Url chemin = new Url();
			chemin.setChemin(wurl);
			if (chemin.estValide()) {
				Extractor page = new Extractor();
				page.init(wurl);
				//On extrait tous les tableaux valides présents sur la page
				page.extractTableaux();
				int i = 1;
				//On parcourt tous les tableaux extraits et on les transforme en csv, puis on teste le csv et on enregistre
				//les caractéristiques du tableau (nb de lignes, nb de colonnes et nb de cellules)
				for (Tableau tab : page.getTableaux()) {
					String csvFileName = mkCSVFileName(url,i);
					System.out.println("Wikipedia url: " + wurl);
					tab.genererCSV("output/html/"+csvFileName);
					nbTableaux++;
					FileReader reader = new FileReader("output/html/"+csvFileName);
					assertNotNull(reader);
					try {
						CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
						List<CSVRecord> lsEnregistrements = parser.getRecords();
						//On teste que le tableau extrait est non vide
						assertNotNull(parser);
						//On teste que l'entête les enregistrements sont non vides
						assertNotNull(parser.getHeaderMap());
						assertNotNull(lsEnregistrements);
						for (String mapKey : parser.getHeaderMap().keySet()) {
							motsEntete.add(mapKey);
						}
						//On ajoute enregistre dans une List le nombre de lignes et de colonnes
						nbColonnes.add(parser.getHeaderMap().size());
						nbLignes.add(lsEnregistrements.size());
						int nbCellulesTab=0;
						//On compte désormais le nombre de cellules
						for (CSVRecord enregistrement : lsEnregistrements) {
							nbCellulesTab+=enregistrement.size();
						}
						//On ajoute les cellules de l'entete
						nbCellulesTab+=parser.getHeaderMap().size();
						System.out.println(nbCellulesTab);
						nbCellules.add(nbCellulesTab);
						
					} catch (Exception e) {
						System.out.println(e);
					}					
					i++;
				}

			}
			nurl++;
			
		}
		br.close();	    
		assertEquals(nurl, 336);
		System.out.println(nbTableaux);
		//On compte l'entête apparaissant le plus de fois
		for (String mot : motsEntete) {
			int freqCour;
			freqCour=Collections.frequency(motsEntete, mot);
			if (freqCour>freqMax) {
				freqMax=freqCour;
				motLePlusFrequent=mot;
			}
		}
		System.out.println("Mot le plus fréquent : "+motLePlusFrequent+" ("+freqMax+" fois)");
		System.out.println("Statistiques sur les colonnes :");
		statistiques(nbColonnes);
		System.out.println("Statistiques sur les lignes :");
		statistiques(nbLignes);
		System.out.println("Statistiques sur les cellules :");
		statistiques(nbCellules);
	}
	

	private String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}
	
	private double calculeMoyenne (List<Integer> lsNombres) {
		int total=0;
		for (Integer nbCourant : lsNombres) {
			total+=nbCourant;
		}
		return (double) total/lsNombres.size();
	}
	
	private double ecartType (List<Integer> lsNombres) {
		double sommeErreurCarre=0;
		double moyenne=calculeMoyenne(lsNombres);
		for (Integer nbCourant : lsNombres) {
			sommeErreurCarre+=Math.pow((nbCourant-moyenne), 2);
		}
		return Math.sqrt(sommeErreurCarre/lsNombres.size());
	}
	
	private void statistiques (List<Integer> lsNombres) {
		System.out.println("Minimum :"+Collections.min(lsNombres));
		System.out.println("Maximum :"+Collections.max(lsNombres));
		System.out.println("Moyenne :"+calculeMoyenne(lsNombres));
		System.out.println("Ecart-type :"+ecartType(lsNombres));
	}

}
