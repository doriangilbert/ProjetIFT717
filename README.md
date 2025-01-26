# ProjetIFT717

## Table des matières

- [ProjetIFT717](#projetift717)
  - [Table des matières](#table-des-matières)
  - [Présentation](#présentation)
    - [Contenu](#contenu)
      - [Structure](#structure)
      - [Fonctionnalités](#fonctionnalités)
  - [Manuel de l'utilisateur](#manuel-de-lutilisateur)
  - [Aperçu de l'application](#aperçu-de-lapplication)
  - [Extrait du rapport](#extrait-du-rapport)
    - [Architecture globale](#architecture-globale)

## Présentation

Projet de programmation d'une application mobile pour les étudiants internationaux primo arrivants. Projet réalisé dans le cadre du cours "IFT717 - Applications Internet et Mobilité" du programme de Maîtrise en informatique de l'Université de Sherbrooke.

### Contenu

Cette application mobile Android permet à des étudiants internationaux primo arrivants à Sherbrooke (Québec, Canada) de découvrir la ville et de participer à des évènements. L'application permet aux étudiants de consulter les lieux d'intérêt, de consulter et s'inscrire aux évènements à venir et de converser avec un robot conversationnel propulsé par l'intelligence artificielle.

#### Structure

L'application est composée de plusieurs parties chacunes développées avec des langages différents :

- [projetift717_serveur](./projetift717_serveur/) : Le serveur de l'application lié au client mobile permettant de manipuler les données et de fournir les différentes fonctionnalités de l'application via une API REST. Ce serveur est lié à une base de données MongoDB stockée dans le cloud MongoDB Atlas. Cette partie a été developpée avec JavaScript et utilise Node\.js, Express\.js, Mongoose et Socket\.io.
- [projetift717_client_android](./projetift717_client_android/) : Le client mobile Android de l'application lié au serveur permettant la consultation des lieux et des évènements, l'inscription à des évènements et la conversation avec un robot conversationnel propulsé par l'intelligence artificielle. Cette partie a été développée avec Kotlin et utilise Jetpack Compose.

#### Fonctionnalités

L'application fournit les Fonctionnalités suivantes :

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

Les documentations associées aux différentes parties sont visibles dans les fichiers README dans chaque répertoire :

- Serveur API : [Documentation](./projetift717_serveur/README.md)
- Client Android : [Documentation](./projetift717_client_android/README.md)

Le projet est configuré par défaut pour que toutes les parties fonctionnent sur une même machine. Cependant, les différentes parties du projet peuvent être déployées sur différents terminaux sous réserve de potentielles adaptations nécessaires dans le code (ex : adresse IP du serveur dans le client Android).

## Aperçu de l'application

| ![Page de connexion](./images/Page%20de%20connexion.png) | ![Page d'inscription](./images/Page%20d%20inscription.png) |
|:-------------------------------------------------------:|:----------------------------------------------------------:|
| *Page de connexion*                                      | *Page d'inscription*                                        |

| ![Page de carte](./images/Page%20de%20carte.png) | ![Boite de dialogue détaillant un lieu de la carte](./images/Boite%20de%20dialogue%20détaillant%20un%20lieu%20de%20la%20carte.png) |
|:-----------------------------------------------:|:------------------------------------------------------------------------------------------:|
| *Page de carte*                                 | *Boite de dialogue détaillant un lieu de la carte*                                          |

| ![Page des lieux](./images/Page%20des%20lieux.png) | ![Page listant les détails d'un lieu](./images/Page%20listant%20les%20détails%20d%20un%20lieu.png) |
|:-------------------------------------------------:|:----------------------------------------------------------------------------------------------:|
| *Page des lieux*                                  | *Page listant les détails d'un lieu*                                                            |

| ![Page des évènements](./images/Page%20des%20évènements.png) | ![Page détaillant un évènement](./images/Page%20détaillant%20un%20évènement.png) |
|:------------------------------------------------------------:|:--------------------------------------------------------------------------------:|
| *Page des évènements (le bouton d'ajout est visible car nous sommes connecté en tant qu'administrateur)* | *Page détaillant un évènement* |

| ![Page permettant de payer une inscription](./images/Page%20permettant%20de%20payer%20une%20inscription.png) | ![Page permettant d'ajouter un évènement](./images/Page%20permettant%20d%20ajouter%20un%20évènement.png) |
|:----------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------:|
| *Page permettant de payer une inscription*                                                                   | *Page permettant d'ajouter un évènement*                                                           |

| ![Boite de dialogue affichée lors de l'utilisation du chatbot](./images/Boite%20de%20dialogue%20affichée%20lors%20de%20l%20utilisation%20du%20chatbot.png) | ![Page du chatbot](./images/Page%20du%20chatbot.png) |
|:----------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------:|
| *Boite de dialogue affichée lors de l'utilisation du chatbot*                                                                      | *Page du chatbot*                                   |

| ![Page de profil](./images/Page%20de%20profil.png) | ![Notification d'inscription à un évènement](./images/Notification%20d%20inscription%20à%20un%20évènement.png) |
|:-------------------------------------------------:|:---------------------------------------------------------------------------------------------------:|
| *Page de profil*                                  | *Notification d'inscription à un évènement*                                                          |

## Extrait du rapport

### Architecture globale

Vis-à-vis de la conception de notre projet, nous avons choisi d'implémenter une application Android couplée à un serveur. Le serveur a pour rôle de stocker les données et de les mettre à disposition pour qu'elles soient utilisées par l'application Android qui a donc pour rôle d'interagir avec l'utilisateur via une interface graphique. L'architecture est construite de telle manière à concentrer les tâches lourdes sur le serveur et permettre ainsi de fournir une application légère à l'utilisateur. Cette architecture impliquant une responsabilité accrue du serveur nous permet de proposer une solution permettant à plusieurs clients mobiles de se connecter simultanément aux serveurs et ainsi permettre une utilisation massive de notre solution par un grand nombre d'étudiants internationaux désireux de découvrir Sherbrooke.

Par ailleurs, la solution est conçue de telle manière à fournir une capacité d'adaptation accrue ce qui pourrait permettre son extension dans le futur à d'autres villes du Canada ou du reste du monde. Cette capacité d'adaptation est notamment importante dans la mesure où elle permet à de futurs développeurs d'implémenter d'autres fonctionnalités dans le futur. L'application se compose donc du serveur qui va interagir avec la base de données et le service web externe d'intelligence artificielle afin de fournir des services à l'application Android qui fera des requêtes sur ce serveur en fonction de ses besoins.
