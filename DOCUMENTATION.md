# DOCUMENTATION METRIZ

METRIZ est un logiciel libre et open source conçu pour fournir une solution de base pour la mesure de l'Empreinte Sociétale d'une Entreprise.

## TELECHARGEMENT

Le logiciel est téléchargeable via le lien : https://lasocietenouvelle.org/LaSocieteNouvelle-METRIZ-1.1.exe
Aucune installation n'est requise.

## FICHIERS DE DONNEES

Les données saisies sont enregistrées dans un dossier "data-metriz" dans le répertoire courant où se situe l'application. Elles sont stockées au format JSON.

## NOTICE D'UTILISATION

La Notice d'utilisation est organisée selon les onglets du menu.

### UNITE LEGALE

Les informations modifiables sont la dénomination, le numéro de siren et l'année de fin d'exercice.
Elles n'ont aucune incidence sur la mesure des indicateurs.

Le second tableau fournit un récapitulatif de l'Empreinte Sociétale de l'Entreprise, i.e. les valeurs des indicateurs pour la valeur produite de l'entreprise.
L'absence de valeur signifie que des données sont manquantes pour le calcul.

### DONNEES FINANCIERES

La partie *Données Financières* correspond à la saisie des données financières de l'entreprise. Elle regroupe trois onglets :
* *Soldes intermédiaires* pour la saisie des soldes comptables (production, montant total des charges externes, montant total des dotations aux amortissements)
* *Charges externes* pour la saisie ou l'importation des charges externes
* *Dotations aux amortissements* pour la saisie ou l'importation des dotations aux amortissements

#### Soldes intermédiaires

Le tableau permet la saisie de la production totale (chiffres d'affaires, production stockée et production immobilisée), du montant total des charges externes et du montant total des dotations aux amortissements. La valeur ajoutée nette est déduite.

Si le montant saisi pour total des charges ou des dotations est inférieur à la somme des charges ou des dotations (cf. ci-dessous), la valeur n'est pas acceptée (modification refusée). Dans le cas contraire, l'écart sera considéré comme des charges ou dotations *inconnues* et des valeurs par défaut seront utilisées pour la mesure des indicateurs.
Le montant total est ajusté si la somme des charges externes ou des dotations dépassent la valeur préalablement saisie ou calculée.

#### Charges externes

L'import des charges externes peut se faire manuellement (fournisseur par fournisseur) en saisissant son numéro de siren et le montant associé. Il est également possible d'importer un fichier .csv (séparation virgule). Dans le cas, la première ligne doit correspondre aux noms des colonnes avec *company_id* pour la colonne contenant le numéro siren et *amount* pour la colonne contenant le montant.

Les données sont modifiables au niveau de chaque ligne. Il est également possible de resynchroniser les données ou de supprimer la ligne.

La colonne *siren* est de couleur verte pour les entreprises *reconnues*.
Si l'entreprise n'est pas reconnue des valeurs génériques par défaut sont utilisées, il est cependant possible de préciser la situation géographique et la division économique à laquelle est rattachée l'entreprise, ou à laquelle elle se rapproche le plus.

Actions globales :
* Importer : importation d'un fichier .csv (les lignes importées s'ajoutent à celles existantes)
* Synchroniser tout : mettre à jour l'ensemble des données à partir des numéros siren
* Supprimer tout : supprimer toutes les lignes

#### Dotations aux amortissements

Le fonctionnement est similaire à l'onglet *Charges externes*

La colonne *année* correspond à l'année ou la dépense d'immobilisation a été effectuée. Elle n'a pour l'instant aucune incidence en l'absence de données historisées disponibles.

### INDICATEURS (INFORMATIONS GENERALES)

Pour chaque indicateur, l'interface se compose de 4 onglets :
* Onglet récapitulatif : valeurs intermédiaires pour chaque solde intermédiaire et pour la valeur produite
* Onglet *Valeur Ajoutée Nette* : onglet pour la saisie des impacts directs
* Onglet *Charges externes* : onglet affichant les valeurs pour chaque entreprise. Il est possible de modifier la valeur proposée et son incertitude.
* Onglet *Dotations aux Amortissements* : onglet affichant les valeurs pour chaque entreprise. Il est possible de modifier la valeur proposée et son incertitude.

### INDICATEURS (INFORMATIONS SPECIFIQUES)

#### Contribution aux Métiers d'Art et aux Savoir-Faire

#### Indice de répartition des rémunérations

#### Contribution à l'économie française

#### Indice d'écart de rémunérations Femmes/Hommes

#### Intensité d'émission de Gaz à Effet de Serre

#### Intensité d'Utilisation de Produits dangereux pour la santé et/ou l'environnement

#### Contribution à l'Evolution des Compétences et des Connaissances

#### Intensité d'Extraction de Matières Premières

#### Intensité de Consommation d'Energie

#### Contribution aux Acteurs d'Intérêt social

#### Intensité de Production de déchets

#### Intensité de Consommation d'Eau

