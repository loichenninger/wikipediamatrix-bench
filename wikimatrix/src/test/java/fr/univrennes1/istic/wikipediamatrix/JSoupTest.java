package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class JSoupTest {

	@Test
	public void testUrl() throws IOException {
		Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		System.out.println(doc.title());
		Elements newsHeadlines = doc.select("#mp-itn b a");
		for (Element headline : newsHeadlines) {
			System.out.println("%s\n\t%s"+headline.attr("title")+headline.absUrl("href"));
		}
	}

	@Test
	public void testUrlCanon()  throws IOException{
		BufferedWriter writer = Files.newBufferedWriter(Paths.get("output/html/test.csv"));
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
		String url="https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras";
		Document doc = Jsoup.connect(url).get();
		Element tableau = doc.select("table").get(0);
		Element premiereLigne = tableau.select("tr").first();
		Elements nomsColonnes = premiereLigne.select("th");
		List<String> lsColonnes = new ArrayList<String>();
		for (Element colonne : nomsColonnes) {
			lsColonnes.add(colonne.text());
		}
		csvPrinter.printRecord(lsColonnes);
		Elements lignes = tableau.select("tr");
		for(Element ligne : lignes) {
			Elements cellules = ligne.select("td");
			if (cellules.size()>0) {
				List<String> lsCellules = new ArrayList<String>();
				for(Element cellule : cellules) {
					lsCellules.add(cellule.text());
				}
				System.out.println(cellules);
				csvPrinter.printRecord(lsCellules);
			}
		}
		System.out.println("done!");		
		csvPrinter.close();
	}
	
	@Test
	public void  CSVReaderSimple() throws IOException {
		FileReader reader = new FileReader("output/html/test.csv");
		assertNotNull(reader);
		CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
		assertNotNull(parser);
		assertEquals(18,parser.getHeaderMap().size());
		assertEquals(62,parser.getRecords().size());
		
	}

	
	
	@Test
	public void csvPrinterTest() throws IOException {
		
			BufferedWriter writer = Files.newBufferedWriter(Paths.get("output/html/test2.csv"));	
	        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID", "Name", "Designation", "Company"));

			csvPrinter.printRecord("1", "Sundar Pichai", "CEO", "Google");
			csvPrinter.printRecord("2", "Satya Nadella", "CEO", "Microsoft");
			csvPrinter.printRecord("3", "Tim cook", "CEO", "Apple");
			csvPrinter.printRecord(Arrays.asList("4", "Mark Zuckerberg", "CEO", "Facebook"));
			csvPrinter.flush();            
		}



}
