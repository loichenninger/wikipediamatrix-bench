package fr.univrennes1.istic.wikipediamatrix;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws IOException
	{
		Html pageCanon = new Html();
		pageCanon.init("https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras");
		Tableau tab = pageCanon.getTableau(0);
		tab.genererCSV("output/html/test3.csv");
	}
}
