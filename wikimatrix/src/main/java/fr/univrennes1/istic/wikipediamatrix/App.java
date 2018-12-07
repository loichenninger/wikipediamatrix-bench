package fr.univrennes1.istic.wikipediamatrix;

import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws IOException
	{
		String url="Comparison_of_Norwegian_Bokmål_and_Standard_Danish";
		Url chemin = new Url();
		chemin.setChemin("https://en.wikipedia.org/wiki/"+url);
		System.out.println(chemin.estValide());
//		Html pageCanon = new Html();
//		pageCanon.init("https://en.wikipedia.org/wiki/"+url);
//		pageCanon.extractTableaux();
//		int i = 1;
//		for (Tableau tab : pageCanon.getTableaux()) {
//			String csvFileName = mkCSVFileName(url,i);
//			tab.genererCSV("output/html/"+csvFileName);
//			i++;
//		}
	}
	
	private static String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}
}
