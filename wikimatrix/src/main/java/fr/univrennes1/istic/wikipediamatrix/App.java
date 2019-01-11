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
		String url="Comparison_of_browser_synchronizers";
		Url chemin = new Url();
		chemin.setChemin("https://en.wikipedia.org/wiki/"+url);
		System.out.println(chemin.estValide());
		Extractor page = new Extractor();
		page.init("https://en.wikipedia.org/wiki/"+url);
		Elements lignesHtml =page.codeSource.getElementsByClass("wikitable sortable").get(0).select("tr");
		int i=1;
		for (Element ligneHtml : lignesHtml) {
			System.out.println("Ligne "+i);
			System.out.println(ligneHtml);
			i++;
		}
//		int i = 1;
//		for (Tableau tab : page.getTableaux()) {
//			String csvFileName = mkCSVFileName(url,i);
//			tab.genererCSV("output/html/"+csvFileName);
//			i++;
//		}
	}
	
	private static String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}
}
