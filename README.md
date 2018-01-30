# androidtoolbox

##### _cours android M1_ 


### __Partie I : Création et configuration du projet__
Pour cet partie, je n'ai pas eu de problème, autant donné que j'ai déjà manipuler **Android Studio** avant.
 
 
### __Partie II : Manipulation de l’interface utilisateur__
Dans l'ensemble, comme pour la première partie, aucune difficulté, excepté pour un composant graphique, le TextInputEditText.
C'est la première fois que j'utilise ce composant, j'ai donc eu un pu de mal au début à bien m'en servir.


### __Partie III : Cycle de vie activity / fragment__
**a**. Explication : 

   Intérêt : permet une meilleur portabilité des applications 
   
**d.** Lors de la rotation du téléphone en mode paysage, le layout est sujet aussi à une rotation. Par contre, quand le téléphone revient en mode portrait, une partie des composants graphiques auront disparu, car lors de la première rotation, l'OS, à supprimer les éléments qui n'était plus visible.

**b. c. e. f.** aucune difficulté

### __Partie IV : Sauvegarder les informations au sein d’une application__
  
**a.** liste des options de stockage :
* Shared Preferences
* Internal Storage
* External Storage
* SQLite Databases
* Network Connection

**b.** SharedPreferences :
```java
getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
```

**c.** aucune difficulté

création d'un menu.xml, pour lister les items du menu,

puis dans le java activity, surcharge de 2 méthode :
```java
public boolean onCreateOptionsMenu(Menu menu) {}
```
pour récupérer les items créer dans le xml

```java
public boolean onOptionsItemSelected(MenuItem item) {}
```
pour créer les actions de chaque item

**d.** aucun problème pour créer le fichier, créer le json, et l'écrire dans le fichier

puisque j'utilise beaucoup les json en java

**e.** j'ai rencontré un problème pour la lecture du fichier

après plusieurs recherche sur internet, j'ai trouvé qu'en fait, sur android, il faut lire le fichier en byte, et donc le convertir en string, afin qu'il soit lisible et donc faire du parsing, pour générer le json

**f.** aucune difficulté

**bonus.** SQLite Databases

très fastidieux au début, du fait que j'ai voulu le faire en utilisant MySQLiteOpenHelper

méthode qui est peu récommandé, pour le temps de mise en place par rapport aux autres méthodes

je me suis donc réorienté vers l'utilisation de la librairie **Room Persistence**

assez vite, j'ai été confronté à des erreurs, puisque avec cette méthode, on ne peut pas faire une requête SQL dans le thread principal

il a donc fallu mettre en place des **AsyncTask**, afin de résoudre ces erreurs, objet qui n'est pas simple à utiliser

### __Partie V : Accès fonctionnalités du smartphone et permissions__
  
**a.** aucune difficulté

première fois que je récupère ses information

**b.** problème de permissions lors de la création de l'activity, pour charger l'image qui avait était sélectionnée en dernière

**c.**
**d.** un peu de temps passé sur la compréhension des curseurs, afin de bien sélectioner les contacts

pour les API >= 23, pour les permissions, il faut demander au priopiétaire du téléphone, la permission d'accèder aux contacts

puis j'ai eu un petit problème de redondance pour les numéros des contacts, peut-être du aux différents méthodes de stockage 

**e.** première méthode : LocationManager + LocationProvider
    problème, avec l'api 26
    
    
   deuxième méthode : fused location service
    succes
    
   ```java
   mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
   ```
   
**f.** aucun problème

petit amélioration des demandes de permissions :

s'il faut demander les 2 permissions (`READ_CONTACTS`, `ACCES_COARSE_LOCATION`), alors je n'affiche qu'une seule fois la pop-up, 
mais avec 2 onglets
   
**g.** pas eu le temps, presque 3h manquer à cause d'un rdv client pour notre **projet technique de 2nd semestre**

### __Partie VI : Utilisation des WebServices__

**a.** aucun problème

**b.** réalisation d'un xml, qui va par la suite, être utilisé comme item dans une recycleView

**c.** HttpUrlConnection => deprecated

**d.** faire cette requête dans une asynctask

ou utiliser une librairie, qui va tout gérer pour nous

**e.** beaucoup de temps pour comprendre GSON

**f.** récupération d'une classe qui override transform de Picasso, afin de faire un cercle

**g.** modification de la requête, pour avoir une liste

**h.** aucun problème
