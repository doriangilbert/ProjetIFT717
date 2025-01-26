# Client Android de l'application ProjetIFT717

## Table des matières

- [Client Android de l'application ProjetIFT717](#client-android-de-lapplication-projetift717)
  - [Table des matières](#table-des-matières)
  - [Présentation](#présentation)
    - [Contenu](#contenu)
      - [Structure](#structure)
      - [Fonctionnalités](#fonctionnalités)
  - [Manuel de l'utilisateur](#manuel-de-lutilisateur)
    - [Outils nécessaires](#outils-nécessaires)
    - [Installation et exécution](#installation-et-exécution)
  - [Extrait du rapport](#extrait-du-rapport)
    - [Architecture détaillée](#architecture-détaillée)
    - [Connexion et inscription](#connexion-et-inscription)
    - [Carte](#carte)
    - [Lieux](#lieux)
    - [Évènements](#évènements)
    - [Chatbot IA](#chatbot-ia)
    - [Profil](#profil)
    - [Notifications](#notifications)

## Présentation

Client Android de l'application FouFouFood lié au serveur permettant aux utilisateurs de consulter les lieux d'intérêt, de consulter et s'inscrire aux évènements à venir et de converser avec un robot conversationnel propulsé par l'intelligence artificielle. Cette partie a été developpée avec Kotlin et utilise Jetpack Compose.

### Contenu

#### Structure

L'application est construite selon une architecture MVVM (Model-View-ViewModel).

#### Fonctionnalités

Cette application fournit les Fonctionnalités suivantes :

- Consulter la liste des lieux intéressants à Sherbrooke et leurs détails sous la forme d'une liste pouvant être triée de manière contextuelle en fonction de la géolocalisation de l'utilisateur ou de l'heure recommandée de visite, ces lieux pouvant être de types différents tels que des restaurants, bars, musées ou encore des parcs
- Consulter les lieux et leurs détails sur une carte interactive et les situer par rapport à la position actuelle de l'utilisateur obtenue par géolocalisation
- Consulter la liste des évènements proposés avec leurs détails
- S'inscrire afin de participer à un évènement et payer sa participation (système de paiement fictif)
- Se créer un compte et se connecter afin d'utiliser l'application
- Consulter son profil avec ses coordonnées ainsi que la liste des évènements auxquels on est inscrit
- Converser avec un robot de conversation utilisant l'IA afin d'obtenir des informations personnalisées et des recommandations sur les lieux et évènements proposés à Sherbrooke
- Se connecter en tant qu'administrateur de l'application afin de l'utiliser et d'ajouter un évènement
- Recevoir des notifications afin d'avoir la confirmation de l'inscription ou de la désinscription à un évènement

## Manuel de l'utilisateur

### Outils nécessaires

- Android Studio
- Un appareil Android ou un émulateur

### Installation et exécution

0. Effectuer la configuration initiale de l'adresse du serveur et du token JWT. (voir notes)

1. S'assurer que le serveur de l'application est en cours d'exécution et l'exécuter si ce n'est pas le cas.

2. Exécuter l'application depuis Android Studio dans un appareil Android ou un émulateur.

Note 1 : L'adresse du serveur est configurée par défaut pour se connecter au serveur en local, il est possible de la modifier dans le fichier [RetrofitInstance.kt](./app/src/main/java/com/example/projetift717/network/RetrofitInstance.kt).

Note 2 : Afin d'effectuer des requêtes authentifiées lors des tests, un token JWT lié à un compte administrateur construit sur le secret JWT utilisé lors du développement a été défini dans les fichiers Repository dans le répertoire [repository](./app/src/main/java/com/example/projetift717/repository/). Il sera donc nécessaire de générer un nouveau token administrateur avec le secret JWT défini dans le serveur avec une durée de vie illimitée (voir le fichier [authMiddleware.js](../projetift717_serveur/middlewares/authMiddleware.js)) en modifiant le code dans le serveur pour spécifier cette durée de vie et en utilisant un client API pour se connecter avec des identifiants administrateur et récupérer le token. Afin de faciliter les tests et ne pas avoir à modifier le code, il est possible d'utiliser le secret JWT proposé dans la documentation du serveur. Il aurait été préférable d'utiliser une méthode plus sécurisée, mais cela n'a pas été implémenté dans le cadre de ce projet.

## Extrait du rapport

### Architecture détaillée

Afin d'avoir un projet structuré, nous avons divisé le code en plusieurs packages afin d'assurer une séparation claire des responsabilités de chaque module. Cette séparation permet une modularité et une évolutivité simple en plus de permettre de facilement comprendre l'utilité de chaque composante de l'application.

Le module « model » sert à représenter les objets de la logique métier de l'application. Ce module est composé exclusivement de data class. Ces classes servent majoritairement à représenter les données stockées dans le serveur. Le sous-module « requests » sert à représenter les formats de données qui sont utilisés pour envoyer ou recevoir des requêtes du serveur, mais ne concernent pas l'interface de l'application.

Le module « network » sert à communiquer avec le serveur. Le module est composé presque entièrement d'interfaces Retrofit, une librairie servant à simplifier les appels HTTP. Chaque appel au serveur est donc fait à partir de ce point. Le module est également composé de la classe « RetrofitInstance », où les interfaces sont mises ensemble et l'instance de communication est configuré. Dans ce fichier, nous définissons l'adresse IP du serveur, en plus de configurations additionnelles pour transformer les réponses JSON envoyé par le serveur en objet Kotlin.

Le module « repository » sert à simplifier l'accès aux données, peu importe où elles se trouvent. À partir d'une classe Repository, nous sommes en mesure d'aller chercher les informations nécessaires, créer de nouvelles données, en modifier ou même en supprimer. Ces changements sont souvent faits au niveau du serveur mais nous avons aussi la possibilité d'accéder au SharedPreferences à partir de ce point. Bien que nous n'utilisions pas la librairie ROOM dans ce projet, l'accès aux données de la base de données SQLite se serait fait à partir de ce point également.

Le module « view » sert à définir le UI de l'application. Chaque fichier représente une page différente de l'interface. Pour ce faire, nous utilisations le cadre de développement Jetpack Compose. Pour gérer la navigation entre les pages, nous utilisons le NavController. Les données à présenter sont contenues dans un ViewModel, qui sert à faire le lien entre le UI et le serveur.

Le module « ViewModel » contient l'implémentation des ViewModel utilisés dans les vues. Ceux-ci contiennent les informations affichées les différentes interfaces et servent de glues entre le UI et le serveur. Typiquement, un viewModel est composé de données à afficher ainsi que des méthodes qui font appel à une classe « Repository ».

### Connexion et inscription

À l'ouverture de l'application, l'utilisateur a l'option de se créer un compte ou se connecter à un compte existant. Cette étape est nécessaire afin d'accéder au restant de l'application. Pour implémenter cette section, nous avons fait appel à un ViewModel pour contenir les données entrées par l'utilisateur et interfacer la communication avec le serveur.

Lors de l'inscription, l'utilisateur est appelé à entrer ces informations. Pour pouvoir se créer un compte, l'utilisateur doit fournir son prénom, son nom, son courriel ainsi qu'un mot de passe. Une fois les informations entrées correctement, le compte est créé. À ce moment, l'utilisateur va pouvoir se connecter à l'aide de la page de connexion.

Lors de la connexion, l'utilisateur a simplement besoin d'entrer son courriel ainsi que son mot de passe. Une fois les informations entrées, les données sont validées par le serveur. Si la connexion est validée, l'utilisateur a accès au restant de l'application.

### Carte

Notre application permet à l'utilisateur consulter les différents lieux intéressants présents à Sherbrooke et de connaitre sa position actuelle par l'intermédiaire d'une carte interactive qu'il est possible de manipuler à sa guise. Lorsque l'utilisateur ouvre la carte par l'intermédiaire de l'icône prévue à cet effet dans la barre navigation, il faut fasse à la carte interactive qui est automatiquement centrée sur sa position lorsqu'il en donne l'autorisation, ou par défaut sur le centre de Sherbrooke.

Afin de naviguer sur cette carte, l'utilisateur peut utiliser les différents boutons de commandes qui lui sont offerts, le premier étant utile pour recentrer la carte sur la position actuelle et les deux autres afin de zoomer en avant ou en arrière sur la carte. L'utilisateur a donc un visuel sur sa position grâce à une icône spécifique et peut donc visualiser les différents lieux dans Sherbrooke. En cliquant sur un point de la carte, l'utilisateur peut consulter les détails du lieu grâce à une boite de dialogue. Cette carte fonctionne grâce à un service Web externe appelé « osmdroid » qui est chargé de fournir les éléments nécessaires à la prise en charge de cette carte dans notre application.

Cette carte est une carte OpenStreetMap qui est un système de cartographie ouvert, en opposition à Google Maps. Ce service étant ouvert, il n'est pas nécessaire de posséder une clé API afin d'utiliser la carte. Les différents éléments permettant l'affichage de cette carte sont disponibles dans le fichier « MapView » et va utiliser le modèle, le vue modèle et le repository des lieux afin de mener toutes les opérations nécessaires. La page prend bien évidemment en charge la gestion des permissions de localisation qui sont demandées lorsque l'utilisateur ne les a pas encore acceptées. Les permissions de l'application telles que celles-ci sont spécifiées dans le fichier « AndroidManifest.xml ».

Il est important de noter que cette carte interactive est capable de couvrir le monde entier et qu'il est tout à fait possible de spécifier des lieux en dehors de Sherbrooke en les ajoutant simplement à la base de données et en spécifiant leur localisation. La prise en charge coté client de la carte restera la même peu importe si un lieu est situé à Sherbrooke ou non.

### Lieux

À partir de la section « Lieux », l'utilisateur peut accéder à une liste de points d'intérêt dans sa région. La page est composée d'une liste où on peut rapidement voir le nom de l'endroit ainsi que son adresse. Lorsque l'utilisateur appuie sur l'un de ses items, il sera amené à une page détaillée sur le lieu en question. Également, l'utilisateur a l'option de trier la liste de trois manières distinctes. La première option est de trier de manière alphabétique. Cette option va réordonner dans un ordre alphabétique selon le nom de l'endroit. La seconde option de tri est le tri par distance. Cette option permet à l'utilisateur de trouver facilement les lieux les plus proches de sa localisation actuelle. Pour ce faire, nous utilisons la localisation actuelle de l'utilisateur pour ordonner les lieux du plus proche au plus loin. Finalement, nous avons l'option de trier selon la date de préférence. Chaque lieu dispose d'un moment favorable pour le visiter. Cette option de tri permet ainsi de trouver les meilleurs endroits à visiter au moment présent. Pour ce faire, nous récupérons le temps actuel pour trier les dates du plus proche au plus loin.

### Évènements

L'application donne la possibilité à l'utilisateur de consulter les évènements proposés à Sherbrooke et de s'y inscrire. Pour cela l'utilisateur dispose d'une vue sous la forme d'une liste sur laquelle il va pouvoir retrouver l'ensemble des évènements. En cliquant sur un évènement, il aura la possibilité de consulter ses détails mais surtout de s'y inscrire. En cliquant sur le bouton prévu à cet effet, l'utilisateur sera invité à saisir ses informations de paiement afin de payer son inscription à l'évènement. Une fois inscrit, l'utilisateur n'aura pas la possibilité de s'inscrire une nouvelle fois à l'évènement. Enfin, en tant qu'administrateur, il est possible d'ajouter un évènement par l'intermédiaire d'un bouton qui est visible uniquement lorsque l'on est connecté en tant qu'administrateur.

Afin de mettre en oeuvre tous ces éléments, nous utilisons les éléments classiques tels que le repository, la vue, le vue modèle ou encore le service qui sont répartis dans les différents fichiers. En raison de contraintes de temps et matérielles, nous avons mis en place un système de paiement fictif procédant néanmoins aux vérifications d'usages sur les champs de saisie comme un numéro de carte bancaire à seize chiffres ou le format des dates.

### Chatbot IA

Le robot conversationnel est un des services proposés par notre application. Par l'intermédiaire de ce robot, l'utilisateur a la possibilité de converser avec une intelligence artificielle qui a la possibilité de répondre à toutes ces questions et plus particulièrement à ses demandes concernant Sherbrooke.

Pour cela, l'utilisateur dispose d'une interface comprenant une barre de saisie ainsi qu'un bouton d'envoi. L'utilisateur est invité à saisir sa requête dans la barre puis à l'envoyer, à la suite de quoi il recevra la réponse de l'intelligence artificielle sur le même écran. L'utilisateur peut poser plusieurs requêtes les unes à la suite des autres. Afin de présenter le fonctionnement du robot conversationnel, l'utilisateur fait face lors de sa visite à une boite de dialogue lui présentant l'outil et les consignes d'utilisation. Il est possible pour l'utilisateur de cocher une case permettant de ne plus afficher la boite de dialogue lors de ses prochaines visites, si la case n'est pas cochée, la boite s'affichera à nouveau. Afin de connaitre la volonté ou non de garder la boite de dialogue, nous avons choisi de stocker cette préférence dans les sharedPreferences de l'application ce qui nous permet de conserver la donnée.

Concernant la mise en oeuvre de ce robot conversationnel, le client va devoir passer par l'API mise en place sur le serveur en utilisant la route « chat » prévue à cet effet. Une requête sera donc créée à l'envoi et contiendra le message que l'utilisateur a entré dans son corps, puis la réponse sera retournée par le serveur et sera affichée. Le fonctionnement exact de ce robot est détaillé dans la partie le concernant dans le serveur. Nous avons choisi d'externaliser cette fonction de chat en utilisant l'API pour des raisons de sécurité et de légèreté de l'application. Faire fonctionner le chatbot à l'extérieur nous permet de faire tous les traitements lourds en dehors de celle-ci et surtout de stocker la clé API nous permettant d'y accéder, uniquement sur le serveur ce qui nous permet de se prémunir d'une éventuelle compromission de l'application par un utilisateur malveillant. L'externalisation de ce service nous permet par ailleurs de le proposer dans le futur via d'autres client comme un client web par exemple. Afin de gérer cette fonctionnalité, nous avons implémenté tous les éléments que sont le service, le modèle, le repository, le vue modèle ainsi que la vue.

### Profil

À partir de la section Profil de l'application, l'utilisateur est en mesure de consulter les informations pertinentes en lien avec son compte. La page est composée de deux parties.

Pour gérer toutes les informations à afficher, nous utilisons un ViewModel composé des informations de l'utilisateurs et la liste des événements liés. Ces données sont récupérées à partir du serveur où elles sont stockées. En premier lieu, nous avons les informations de l'utilisateur. Ce dernier peut voir son prénom, son nom et son courriel. En second lieu, l'utilisateur peut voir la liste des événements auxquels celui-ci s'est inscrit.

### Notifications

Notre application permet de prendre en charge des notifications à destination de l'utilisateur. Ces notifications sont utilisées afin de confirmer à l'utilisateur son inscription ou sa désinscription à un évènement. Lorsque l'utilisateur va s'inscrire ou se désinscrire à un évènement, l'utilisateur va recevoir une notification, par ailleurs, si un administrateur, par exemple, inscrit ou désinscrit un utilisateur, une notification sera aussi envoyée. Afin de mettre en oeuvre ce système, nous avons deux parties : la partie coté serveur que nous avons déjà expliquée précédemment, et la partie coté client que nous détaillons ici.

Comme nous l'avons vu, le serveur utilise Socket IO afin d'émettre les notifications, il a donc fallu implémenter les mécanismes nécessaires à la connexion du client au serveur afin qu'il puisse écouter les notifications, il a fallu aussi implémenter le système de réception et d'affichage des notifications en utilisant le système natif d'Android. Pour cela, nous avons implémenté ce système dans le « MainActivity », le MainActivity étant constamment en utilisation au cours de l'usage de l'application, cette implémentation nous permet d'implémenter le système une seule fois tout en sachant qu'il sera disponible tout au long du cycle d'utilisation.

Plusieurs étapes sont nécessaires à la prise en charge des notifications : la création du canal de notifications, la connexion, la capture et enfin l'affichage. Pour chaque étape on trouvera une méthode associée. Pour commencer, on mettra en place les notifications en créant un canal de notifications. Un canal de notifications permet de spécifier un type de notifications et d'y associer des comportements différents, par exemple, on pourra faire vibrer le téléphone pour des notifications classiques et ajouter un son en plus pour les notifications prioritaires. Les canaux permettent une gestion fine par l'utilisateur des notifications qu'il souhaite recevoir ou non ainsi que les comportements associés. Afin de mettre en place ce canal, on utilisera une méthode nommée « createNotificationChannel ».

Après avoir créé le canal de notifications, il nous faudra nous connecter au serveur afin de capturer les différentes notifications qu'il est susceptible d'envoyer. Pour cela on utilisera la méthode « connectToNotifications ». Dans cette méthode, nous allons récupérer l'identifiant de l'utilisateur actuellement connecté puis générer une URL de connexion aux notifications du serveur tel que nous avons pu le définir dans la partie concernant le serveur. Cette méthode va donc nous connecter au serveur et écouter tout évènement émis par le serveur relatif aux notifications. A la réception d'une notification, « connectToNotifications » va appeler la méthode suivante qui est nommée « handleNotification ».

La méthode « handleNotification » est chargée de récupérer les données contenues dans une notification afin de les traiter de de générer le message contenu dans la notification à afficher. Pour cela, la fonction va prendre en paramètre les données envoyées par le serveur dont le type de la notification et les détails concernant l'évènement auquel l'utilisateur a été inscrit ou désinscrit. La méthode va donc lire le type de la notification, qui peut être « EVENT_JOINED » pour une inscription, ou « EVENT_LEFT » pour une désinscription. Une fois le type lu, la fonction va générer le texte de la notification à afficher pour le transmettre à la méthode « showNotification ».

La fonction « showNotification » va enfin récupérer le message généré par la méthode « handleNotification » et va l'afficher. Pour cela nous allons configurer la notification en spécifiant par exemple son titre puis c'est à ce moment que nous allons envoyer la notification sous réserve que l'utilisateur ait donné sa permission sur le canal que nous avons créé au début. C'est ainsi que l'utilisateur va recevoir la notification via le système natif d'Android pour la gestion des notifications, il pourra consulter ses notifications dans le tiroir prévu à cet effet en l'ouvrant par un balayage du doigt du haut de l'écran vers le bas.
