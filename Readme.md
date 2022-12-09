SERVER WEB
--- Classes Existants : 
            --> WebServer.java : Le Serveur qui recoit les requetes.
            --> RequestHttp.java : Fonctions de traitement des requetes recus (specifiquement: url).
            --> ResponseHttp.java: Fonctions de traitement des reponse que le serveur va envoyer (specifiquement: fichiers et dossiers).

--- Dossier specifique :
            --> my_www : le dossier contenent les ressources.


--- Fonctions : 
        !-- RequestHttp.java
            --> GettingMethod(ArrayList lists) : Retourne la methode utiliser (GET, POST, ...).
            --> getUrl(ArrayList lists) : retourne le path envoier par le client.
            --> senddata(String url) : retourne un simple url en enlevant les valeurs envoier dans l'url (specifiquement dans la methode "GET").
        !-- ResponseHttp.java
            --> deleteSlash(String path) : supprime le premier slash de l'url (/my_www).
            --> direstories_files(String my directory) : prend tout les nom de fichier et de dossier existant dans le dossier donnee.
            --> responseSlash(String[] data): retourren le contenu de tous du dossier, data etant la listes des fichier et dossier present dans le dossier.
            --> verifyfileordirectorie(String path) : verifie si c'est un fichier ou un dossier (0 pour fichier, 1 pour dossier).
            --> verifyExisting(String path) : verifie si le dossier ou le fichier existe vraiment( 1 pour existe, 0 sinon).
            --> readingfile(String path) : lit le contenu d'un fichier (utiliser poir de l'html pure).
            --> verifyString(String string) : specifiquement pour la methode "GET" pour eviter les erreur de retournement de donner dans les donnees collecter (specifiquement pour les String comme ' -> %27);
            --> gettdata(String[] datas) : met en place les valeur envoier en forme lisable pour php.
            --> readphpfile(String path, String[] datas) : complile les codes php dans le fichier et retourne un texte html pure du contenu dans le fichier.php. (meme fonction pour post et pour get).

--- Navigateur suggerée : MOZILLA FIREFOX (Chrome presente des faits innexplicables lors du demarrage du cerveur).
--- Type de fichier qui peuvent etre lu : .html ou .php.

--- ERROR :
    --> ERROR 1778 : si le type de fichier n'est pas .php ou .html.
    --> ERROR 31: si le fichier est introuvable. 
