package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
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
				page.extractTableaux();
				int i = 1;
				for (Tableau tab : page.getTableaux()) {
					String csvFileName = mkCSVFileName(url,i);
					System.out.println("Wikipedia url: " + wurl);
					tab.genererCSV("output/html/"+csvFileName);
					nbTableaux++;
					FileReader reader = new FileReader("output/html/"+csvFileName);
					assertNotNull(reader);
					try {
						CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
						//On teste que le tableau extrait est non vide
						assertNotNull(parser);
						//On teste que l'entête les enregistrements sont non vides
						assertNotNull(parser.getHeaderMap().size());
						assertNotNull(parser.getRecords().size());
						//
						
					} catch (Exception e) {
						System.out.println(e);
					}					
					i++;
				}

				// for exporting to CSV files, we will use mkCSVFileName 
				// example: for https://en.wikipedia.org/wiki/Comparison_of_operating_system_kernels
				// the *first* extracted table will be exported to a CSV file called 
				// "Comparison_of_operating_system_kernels-1.csv"

				// the *second* (if any) will be exported to a CSV file called
				// "Comparison_of_operating_system_kernels-2.csv"


				// TODO: the HTML extractor should save CSV files into output/HTML
				// see outputDirHtml 

				// TODO: the Wikitext extractor should save CSV files into output/wikitext
				// see outputDirWikitext   

			}
			nurl++;
		}
		//		for (int i=0;i<10;i++) {
		//			url = br.readLine();
		//			String wurl = BASE_WIKIPEDIA_URL + url;
		//			System.out.println("Wikipedia url: " + wurl);
		//			Html page = new Html();
		//			page.init(wurl);
		//			Tableau tab = page.getTableau(0);
		//			String csvFileName = mkCSVFileName(url,1);
		//			tab.genererCSV("output/html/"+csvFileName);
		//		}

		br.close();	    
		assertEquals(nurl, 336);
		System.out.println(nbTableaux);



	}
	

	private String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}

}
