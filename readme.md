# Wikimatrix

L'objectif de ce projet est de créer un programme permettant à partir d'une liste d'url pointant sur des pages Wikipédia d'extraire le maximum de tableaux contenu dans ces pages et les exporter au format csv dans le dossier output du projet.

## Licence
```
GNU V3
``` 
## Executer

Importer le dossier **wikimatrix** et se rendre dans la classe **BenchTest**. Il faut ensuite l’exécuter, les résultats se trouveront dans le fichier **output**.

Pour extraire d'autres urls, il faut modifier le fichier **wikiurls.txt** en ajoutant les urls à la liste. 

Pour stocker dans un autre répertoire changer l'attribut destination
```
String destination = "output" + File.separator + "html" + File.separator;
```
## Maven dependances

Ajouter les dépendances suivantes dans le fichier pom.xml qui gère les dépendances maven.
```
    <dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<!-- jsoup HTML parser library @ https://jsoup.org/ -->
			<groupId>org.jsoup</groupId>
			<artifactId>jsoup</artifactId>
			<version>1.11.3</version>
		</dependency>
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-csv</artifactId>
			<version>1.6</version>
		</dependency>
``` 




## Diagramme de classe :

![Ci dessus le diagramme de classe de cette application](https://github.com/loichenninger/wikipediamatrix-bench/blob/master/diagUML.png?raw=true "Title")

