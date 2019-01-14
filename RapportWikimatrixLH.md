# Projet Wikimatrix
L'objectif de ce projet est de créer un programme permettant à partir d'une liste d'url pointant sur des pages Wikipédia d'extraire le maximum de tableaux contenu dans ces pages. Les tableaux doivent avoir une forme correspondant au visuel de la page Wikipédia. Je présenterai dans un premier temps les résultats obtenus, puis dans un second temps nous verrons les qualités et les faiblesses du programme, et pour finir nous discuterons de la qualité des tableaux obtenus.

## Quelques chiffres
Au départ, la liste fournie contient 336 urls à analyser. Le programme réalisé a permis d'extraire 686 tableaux.

### Mot le plus fréquent
Le mot que l'on retrouve le plus dans les entêtes est le mot **License** avec 132 occurrences.

### Nombre de lignes, de colonnes et de cellules

|     | Colonnes | Lignes | Cellules |
|--|--|--|--|
| Min | 2 | 0 | 4 |
| Max | 30 | 593 | 4540 |
| Moyenne | 8,9 | 28,1 | 262 |
| Écart-type | 4,4 | 39,1 | 334,7 |

Rq : Un tableau est à zéro ligne car le tableau ne contient qu'une seule ligne contenant des signes qui non pas été extrait.

## Qualités et faiblesses du programme

### Dépendance à l'attribut **wikitable sortable**

Lors de différents essais pour extraire les tableaux des différentes pages Wikipédia, je me suis vite rendu compte que les balises *\<table\>* qui peuvent servir à repérer les tableaux dans une page ont d'autres usages que de contenir de la donnée. Ils servent notamment à la mise en page. Ainsi il a fallu trouver un moyen de ne prendre en compte que les tableaux qui nous intéresse. Pour cela le programme n'extrait que les tableaux qui ont pour **class** la valeur **wikitable sortable**. C'est une faiblesse de ce programme en effet il est tout à fait possible que des contributeurs n'aient pas utilisé cet attribut pour leurs tableaux. Dans ce cas le programme sera incapable d'extraire de tels tableaux.

### Non robuste aux tableaux exotiques

Ce programme n'est pas robuste aux tableaux "exotiques". Par exemple il ne gère pas les tableaux compliqués où les certaines cases peuvent être sur plusieurs lignes ou colonnes  (en utilisant les attribut **rowspan** ou **colspan**. Dans ce cas les tableaux sont extraits mais inexploitables. 
Cela apparaît comme une faiblesse, bien qu'à relativiser. Ces tableaux bien souvent ne contiennent pas de données exploitables de façon statistiques. De plus chaque contributeur semble utilisé sa propre façon de construire ces tableaux. Il n'y a pas vraiment de norme, et prendre en compte tous les cas de figures possibles demanderait énormément de travail et serait difficilement réalisable dans le cadre de ce projet.

### Un extracteur efficace dans la majorité des cas
Lorsque que les tableaux restent relativement simples, l'extracteur fonctionne très bien. Il gère également lorsque les entêtes sont répétées à la fin du tableau. Dans ce cas l'extracteur filtre cette dernière ligne. 
Dans un premier temps l'extracteur utilisait la balise \<td\> pour repérer les lignes du tableau hors entête. Cependant certains tableaux utilisaient la balise \<th\> pour la première colonne. Dans un deuxième temps l'extracteur a pris en compte cette contrainte.


## Exploitation statistique des tableaux extraits
Même lorsque les tableaux sont extraits correctement encore faut-il qu'ils contiennent une information exploitable. Hors beaucoup de tableaux extraits ne contiennent pas de données exploitables statistiquement : par exemple les tableaux qui contiennent des mots dans une langue donnée ainsi que leur traduction en anglais.
Pour extraire uniquement de la donnée utilisable il faudrait soit sélectionner les urls spécifiquement, soit effectuer des traitements a posteriori de l'extraction pour sélectionner parmi les tableaux extraits sont qui contiennent effectivement de la donnée exploitable.


