# Serveur de l'application ProjetIFT717

## Table des matières

- [Serveur de l'application ProjetIFT717](#serveur-de-lapplication-projetift717)
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
    - [Utilisation](#utilisation)
    - [Utilisateurs](#utilisateurs)
    - [Lieux](#lieux)
    - [Événements](#événements)
    - [Chatbot IA](#chatbot-ia)
    - [Notifications](#notifications)

## Présentation

Serveur de l'application lié au client mobile Android permettant de manipuler les données et de fournir les différentes fonctionnalités de l'application via une API REST. Ce serveur est lié à une base de données MongoDB stockée dans le cloud MongoDB Atlas. Cette partie a été developpée avec JavaScript et utilise Node\.js, Express\.js, Mongoose et Socket\.io.

### Contenu

#### Structure

Le serveur est construit selon une architecture inspirée du patron architectural Model-View-Controller (MVC).

Les différentes parties du serveur sont les suivantes :

- Le fichier [app.js](./app.js) contenant la logique principale du serveur. Nous y trouverons des éléments comme la connexion à la base de données, la déclaration des différentes routes principales, la route de base (/) ou encore la mise en écoute du serveur sur les ports.
- Le répertoire [models](./models/) contenant les différentes entités manipulables par l'application. Ces entités sont représentées par des schémas Mongoose permettant la liaison avec la base de données MongoDB. Les entités pouvant être représentées dans l'application sont : les utilisateurs, les lieux et les évènements.
- Le répertoire [controllers](./controllers/) contenant les différents controleurs composés de méthodes pouvant être appelées par les routes afin d'exécuter la logique métier qu'elles contiennent afin d'agir sur les entités représentées par les modèles.
- Le répertoire [routes](./routes/) contenant les différentes routes accessibles depuis les différents clients ou via un client d'API tel que Postman. Certaines peuvent nécessiter d'être authentifié ou de posséder un rôle spécifique. En appelant ces routes, ce seront les méthodes définies dans le controleur associé qui seront appelées.
- Le répertoire [middlewares](./middlewares/) contenant les middlewares d'authentification qui vont être exécutés lors de l'appel des routes pour vérifier si nécessaire l'authentification de l'utilisateur ou son accès ou non en fonction de son rôle.
- Le répertoire [utils](./utils/) contenant les utilitaires permettant de servir des fonctions annexes de l'application telles que la prise en charge de notifications ou du formatage des données au format RDF.
- Les fichiers [db.js](./db.js) et [index.html](./index.html) étant utilisés à des fins de tests, le premier pour tester et peupler la base de données et le second pour tester la réception de notifications.

#### Fonctionnalités

Ce serveur fournit les services suivants :

- Opérations élémentaires de création, de consultation, de mise à jour et de suppression des entités de base (utilisateur, lieu et évènement)
- Création d'un compte
- Connexion à un compte
- Inscrire un utilisateur à un évènement
- Désinscrire un utilisateur d'un évènement
- Envoi de notifications à un utilisateur lors de son inscription ou de sa désinscription à un évènement
- Conversation avec un robot conversationnel propulsé par l'intelligence artificielle

## Manuel de l'utilisateur

### Outils nécessaires

- Terminal sur la machine serveur
- Client Android
- Client API (ex: Postman) (facultatif, à des fins de tests)

### Installation et exécution

0. Créer un fichier .env à la racine contenant l'URI de la base de données MongoDB (il est recommandé de l'héberger sur MongoDB Atlas), un secret JWT et une clé API pour MistralAI. (uniquement à la première exécution)

Le fichier devra respecter le format suivant :

```text
DB_URI=<uri de la base de données MongoDB>
JWT_SECRET=<secret JWT>
MISTRALAI_API_KEY=<clé API de MistralAI>
```

Note : Afin de faciliter les différents tests et éviter la modification du code du client Android, on propose le secret JWT suivant : ```0a51e1709044123da266e2c48b277d47f321ba5ff0df416331fd8a74331a5dcd53656d786ffabdc016da33eb12c2f820170032f06048a0ffb74603bc5fb6d95e```. Pour une utilisation réelle, il serait nécessaire de générer un secret JWT spécifique.

1. Exécuter dans un terminal les commandes suivantes :

```shell
npm install
npm run start
```

Note : Il est possible de tester la connexion à la base de données et de la peupler avec des données échantillon en se rendant dans le fichier [db.js](./db.js), en décommentant la ligne ```await addSampleData();``` en fin de fichier et en exécutant le fichier. Avertissement : L'opération de peuplement de la base écrase l'entièreté des données déjà présentes. Un compte administrateur est créé par défaut avec les identifiants ```admin``` et ```admin``` pour les tests.

2. Ouvrir le client mobile Android et l'utiliser.

Les étapes suivantes concernent l'utilisation par un client API tel que Postman.

3. Ouvrir un client API (ex : Postman) et préfixer toutes les routes par ```http://localhost:3000``` pour une utilisation normale ou par ```http://localhost:8080``` pour la réception de notifications. Préciser la méthode HTTP ainsi que les potentiels arguments en paramètre de l'URL ou dans le corps de la requête.

Les routes primaires sont les suivantes :

- ```/users``` : Pour les utilisateurs
- ```/places``` : Pour les lieux
- ```/events``` : Pour les évènements
- ```/chat``` : Pour le robot conversationnel
- ```/notifications``` : Pour les notifications (à utiliser sur le port 8080 en suivant par ```\<id de l'utilisateur pour lequel recevoir les notifications>```)

Les routes secondaires associées à chacunes des routes primaires, leur méthode HTTP, arguments en paramètre de l'URL ou dans le corps de la requête sont détaillées dans les fichiers des répertoires [routes](./routes/) et [controllers](./controllers/).

## Extrait du rapport

### Architecture détaillée

Afin d'assurer une grande capacité d'adaptation au code de ce serveur, nous avons séparé les différents éléments en plusieurs parties permettant ainsi une compréhension aisée tout comme l'ajout de fonctionnalités. Le code est donc divisé en plusieurs fichiers qui sont réparties dans 6 lieux qui sont la racine du serveur, les modèles, les contrôleurs, les routes, les middlewares et les utilitaires. On pourrait comparer l'architecture adoptée à une architecture de type Modèle Vue Contrôleur (MVC).

Nous retrouverons dans le répertoire « models » les modèles des entités que nous utilisons qui sont au nombre de trois. Il s'agit de l'utilisateur « User », du Lieu « Place » et de l'évènement « Event ». Ces fichiers vont contenir les schémas MongoDB implémentés par Mongoose et qui vont nous servir à définir la structure de nos entités tant en tant qu'objet dans la base de données qu'en tant qu'objet pour notre API. C'est grâce à ces modèles que l'on va pouvoir instancier et manipuler des objets à notre guise.

Nous trouvons par la suite les middlewares. Les middlewares sont des fonctions que l'on va pouvoir utiliser avant l'exécution d'une fonction. Par exemple, on y retrouvera les fonctions liées à l'authentification. Les middlewares sont exécutés lors de l'appel d'une route avant l'exécution de la fonction associée à cette route.

Par la suite, nous retrouverons les contrôleurs. Les contrôleurs sont chargés de la logique métier, ils implémentent des méthodes qui seront par la suite appelées par les routes. Ils vont donc instancier les objets définis par les modèles et faire des opérations dessus comme la sauvegarde en base de données ou l'accès.

En amont des contrôleurs nous retrouvons les routes. Pour chaque fonctionnalité, nous retrouverons, tout comme pour les contrôleurs, un fichier contenant les différentes routes qu'il sera possible d'appeler puis l'extérieur pour l'entité ou la fonctionnalité donnée. Nous retrouverons notamment la méthode HTTP utilisée, l'URL, les middlewares qui vont être appelés en fonction de notre souhait de restreindre plus ou moins la route et la méthode que l'on appellera dans le contrôleur associé. Dans les utilitaires nous retrouverons les fonctions permettant entre autres de mettre en place et d'utiliser le système de notifications.

Enfin, dans la racine de l'application nous retrouverons notamment le fichier « app.js » qui est le point de départ de l'application dans lequel nous retrouverons la connexion à la base de données, la déclaration des différentes routes au premier niveau (le terme utilisé juste à la suite de l'adresse du serveur désignant la fonctionnalité ou l'entité traitée). Nous retrouverons par ailleurs une route par défaut pour tester notre API par exemple et expliquer son fonctionnement puis nous terminons le fichier avec l'ouverture de notre serveur d'API sur le port 3000 et notifications sur le port 8080.

Les fichiers « db.js » et « index.html » sont des fichiers annexes, le premier étant utilisé pour tester et inclure des données échantillon dans la base de données et le second pour tester le bon fonctionnement des notifications en se connectant à un utilisateur particulier.

Afin de stocker toutes les informations sensibles comme l'URL de connexion à la base de données, le secret JWT ou le token pour l'API d'intelligence artificielle, nous utilisons un fichier stockant les éléments sous la forme de variables d'environnement appelé « .env » ce fichier est présent localement dans la racine du serveur et n'est pas destiné à être diffusé dans le cadre d'un dépôt distant sur GitHub par exemple. Cela nous évite de stocker dans le code les informations sensibles.

### Utilisation

Afin d'utiliser cette API, on spécifiera l'adresse du serveur (```http://localhost:3000``` lorsque l'on lance le serveur et le client sur la même machine dans le cadre de nos tests). A la suite de cette adresse on spécifiera la fonctionnalité ou l'entité visée comme ```http://localhost:3000/users``` lorsque l'on souhaite agir sur les utilisateurs. Ensuite en fonction de l'action menée on pourra trouver d'autres éléments comme l'identifiant de l'objet à modifier dans le cadre d'une mise à jour comme ```http://localhost:3000/users/18826``` si l'on souhaite modifier un utilisateur avec HTTP PUT. Lorsque la requête ne peut être exécutée que par des personnes authentifiées, on spécifiera dans l'en-tête « Authorization » le token JWT de l'utilisateur. On pourra au besoin en fonction de la route, spécifier des paramètres dans le corps de la requête en utilisant le format JSON. Une requête envoyée par un client sera sérialisée dans ce format JSON et une réponse envoyée au format JSON sera désérialisée par ce même client.

### Utilisateurs

L'entité « User » représente les utilisateurs. Les opérations sur cette entité sont faites avec le préfixe « users » suivant directement l'adresse du serveur dans l'URL de la requête.

Cette entité est construite selon le schéma que l'on retrouve dans le fichier modèle, ainsi, on pourra spécifier pour un utilisateur son nom, son prénom, son adresse email qu'il utilisera comme identifiant pour se connecter, son mot de passe, le numéro de téléphone, son statut d'administrateur ou non ainsi que la liste des évènements auxquels il est inscrit. Nous retrouverons par ailleurs dans le modèle deux middlewares qui sont utilisés afin de sécuriser le stockage du mot de passe en générant un hachage lors de la création ou de la mise à jour du mot de passe permettant ainsi d'empêcher toute fuite du mot de passe en clair en cas de compromission de la base de données.

Parmi les routes proposées pour les utilisateurs, nous retrouverons les routes de consultation avec la méthode HTTP GET, permettant de consulter tous les utilisateurs ou les détails d'un utilisateur en particulier. Nous retrouverons par ailleurs une route de création d'utilisateur avec la méthode HTTP POST, une route de mise à jour avec la méthode PUT ainsi qu'une route de suppression avec DELETE. Nous trouverons enfin deux routes spéciales qui ne demandent pas d'authentification ou de rôle d'administrateur qui sont les routes de connexion et d'inscription.

Dans le contrôleur nous retrouvons les méthodes appelées par chaque route. On notera que la route de connexion est la plus complexe ici. Effectivement, lors de la connexion, on débutera par vérifier les identifiants de l'utilisateur puis on génèrera un token JWT. Le token JWT est utilisé afin de restreindre l'utilisation de certaines routes uniquement aux utilisateurs qui se sont connectés au préalable. Pour cela on utilisera un secret qui est une chaine de caractère longue et aléatoire qui va service de base à la création du token. Ce token sera donc nécessaire pour chaque requête et devra donc être placé dans l'en-tête de ces requêtes. On notera que le token est généré et valable pour une durée d'une journée au-delà de laquelle il sera invalide et il faudra donc se reconnecter, afin de gérer cela on envoie une valeur de temps signalant la date d'expiration. On enverra aussi l'identifiant de l'utilisateur connecté afin d'éviter une requête supplémentaire au niveau du client. Afin de restreindre l'accès aux utilisateurs authentifiés, on utilise un middleware situé dans le répertoire prévu à cet effet qui est appelé « authentificateToken » qui vérifie la validité. Un second middleware « checkAdmin » est chargé de vérifier le rôle de l'utilisateur afin de restreindre l'accès aux routes concernées uniquement aux administrateurs.

### Lieux

« Place » va représenter les lieux que l'on va stocker dans notre solution. Les opérations sur cette entité sont faites avec le préfixe « places » suivant directement l'adresse du serveur dans l'URL de la requête.

Nous stockons pour chaque lieu son nom, son adresse, la position décrite par la latitude et la longitude, son type dont la valeur est définie par un ensemble de valeurs prédéfinies dans une énumération comme restraurant, bar ou par exemple parc. Nous trouverons aussi les horaires d'ouverture et de fermeture du lieu et enfin l'horaire idéal de visite qui nous permet de proposer les lieux à visiter à l'utilisateur en fonction de l'heure de la journée.

Parmi les routes proposées, on retrouvera les routes « classiques » servant à la consultation, la création, la mise à jour et la suppression d'un lieu. Ces quatre fonctions étant les fonctions CRUD qui signifie « Create, Read, Update, Delete », il s'agit d'un ensemble de fonctions de base que l'on retrouvera pour chaque entité créée.

Dans le contrôleur associé, on retrouvera les méthodes appelées par les routes servant à ses opérations de base. On notera pour toutes les routes implémentées dans ce serveur, la présence d'un contrôle d'erreur avec les blocs try et catch permettant une gestion simple des erreurs. On notera par ailleurs l'utilisation des codes retour HTTP comme le code « 404 » signifiant d'une ressource n'a pas été trouvée.

### Événements

Les évènements sont représentés par l'objet « Event », les actions sur cet objet peuvent être effectuées en utilisant le mot clé « events » dans l'URL de la requête.

Pour chaque évènement, on pourra spécifier son nom, sa date et heure, sa description, son adresse, son prix d'inscription ainsi que le liste des utilisateurs qui y sont inscrits.

Parmi les routes pour cette entité, nous retrouverons les routes habituelles de consultation, création, mise à jour et suppression puis nous retrouverons quelques routes spécifiques parmi lesquelles une route pour récupérer les évènements d'un utilisateur, une route pour inscrire un utilisateur à un évènement et une route pour désinscrire un utilisateur d'un évènement.

Nous retrouvons dans le contrôleur les méthodes associées à chaque route et en particulier les méthodes d'inscription et de désinscription d'un utilisateur à un évènement dans lesquelles on retrouvera l'envoi de notifications à l'utilisateurs que nous aborderons par la suite.

### Chatbot IA

Notre solution propose un robot conversationnel fonctionnant avec l'intelligence artificielle afin de donner des recommandations de lieux à Sherbrooke ou répondre à toute question. Ce robot conversationnel est propulsé par l'API de Mistral AI qui est une entreprise française proposant des modèles d'IA génératives. Mistral propose ses modèles par une API appelée « La Plateforme » à laquelle il est possible d'accéder pour obtenir une clé API qui sera stockée dans notre serveur en tant que variable d'environnement dans le fichier « .env ». C'est avec cette clé API que l'on pourra effectuer des requêtes auprès de Mistral.

Ce service présente une unique route accessible via la méthode POST avec le préfixe « chat » dans l'URL.

Dans le contrôleur on retrouvera la méthode associée qui va récupérer dans le corps de la requête le message d'entrée fourni par l'utilisateur et va contacter Mistral AI afin qu'il retourne une réponse générée par son modèle d'IA distant. Nous noterons la présence d'un contexte fourni à l'intelligence artificielle afin de faciliter les requêtes et d'orienter les réponses. Ainsi, on indiquera à l'intelligence artificielle pour chaque requête l'objectif de son utilisation et le contexte qui est la recommandation de lieux et d'évènements à Sherbrooke. Ainsi, un utilisateur pourra poser une question générale qui sera mise automatiquement en contexte, par exemple si je demande « Aurais tu des restaurants à recommander ? », le modèle d'intelligence artificielle va comprendre grâce au contexte automatique que l'on recherche des restaurants à Sherbrooke et va fournir une réponse appropriée. Dans ce cas, nous utilisons le modèle Mistral Nemo qui est un modèle léger et open source qui a été développé en collaboration avec NVIDIA et qui est capable de faire des tâches d'ordre général.

### Notifications

Notre application implémente des notifications qui vont être envoyées lorsqu'un utilisateur va s'inscrire ou se désinscrire d'un évènement afin qu'il obtienne la confirmation de son action.

Afin d'utiliser ces notifications, il suffit de se connecter au serveur par le port spécifique prévu à cet effet, le port 8080, en utilisant le préfixe « notifications » et en utilisant l'identifiant de l'utilisateur pour lequel on souhaite écouter les notifications dans l'URL de la requête. Afin d'implémenter les notifications, nous utilisons le fichier utilitaire « socketUtils » dans lequel on va d'abord définir une méthode d'initialisation nommée « setupSocketIO » qui va nous permettre de mettre en place un canal de communication continu entre un client et le serveur grâce à SocketIO qui est une extension WebSocket. On retrouvera dans cette méthode les éléments nécessaires à la connexion et à la déconnexion d'un utilisateur sur le canal de notification. Afin d'assurer une grande modularité, nous avons choisi de définir une seule et unique méthode afin d'émettre toutes les notifications peu importe leur type. On utilisera donc dans les méthodes concernées par l'envoi de notifications, la méthode « notifyUser » qui prend en paramètre le socket, l'identifiant de l'utilisateur ainsi que les données à transporter telles que les détails de l'évènement ou encore le type de notification. Ainsi, nous avons défini deux types de notifications « EVENT_JOINED » et « EVENT_LEFT » pour l'inscription et la désinscription d'un utilisateur à un évènement respectivement. Ainsi, un client recevant une notification sera en mesure de lire ce type et d'afficher une notification personnalisée.
