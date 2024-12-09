const mongoose = require('mongoose');
const User = require('./models/User');
const Event = require('./models/Event');
const Place = require('./models/Place');
require('dotenv').config(); // Charger les variables d'environnement depuis le fichier .env

// URI pour la connexion à l'instance MongoDB locale
const db_uri = process.env.DB_URI; // Récupérer l'URI de la base de données à partir des variables d'environnement

// Fonction pour connecter à MongoDB
async function connectToDatabase() {
    try {
        await mongoose.connect(db_uri);
        console.log('Connected to MongoDB');
    } catch (err) {
        console.error('Error connecting to MongoDB:', err);
        process.exit(1); // Terminer le processus si la connexion échoue
    }
}

// Fonction pour fermer la connexion MongoDB
async function closeDatabaseConnection() {
    try {
        await mongoose.connection.close();
        console.log('Connexion à MongoDB fermée');
    } catch (err) {
        console.error('Erreur lors de la fermeture de la connexion:', err);
    }
}

// Fonction pour ajouter des données d'échantillon
async function addSampleData() {
    try {
        // Supprimer les collections existantes
        await mongoose.connection.dropCollection('users');
        await mongoose.connection.dropCollection('events');
        await mongoose.connection.dropCollection('places');

        // Création des utilisateurs
        const users = [
            new User({
                name: 'admin',
                surname: 'admin',
                email: 'admin@gmail.com',
                password: 'admin',
                isAdmin: true
            }),
            new User({
                name: 'user',
                surname: 'user',
                email: 'user@gmail.com',
                password: 'user',
                isAdmin: false
            })
        ];

        // Sauvegarde des utilisateurs
        for (const user of users) {
            await user.save();
        }

        // Création des événements
        const events = [
            new Event({
                name: 'Match de Hockey',
                date: new Date('2024-12-31T00:00:00.000+00:00'),
                description: 'Ce match de Hockey opposera l\'équipe des Phoenix de Sherbrooke contre …',
                address: 'Palais des Sports Léopold-Drolet',
                price: 5
            }),
            new Event({
                name: '5 à 8',
                date: new Date('2024-12-19T00:00:00.000+00:00'),
                description: 'Pour fêter la fin de la session Automne 2024, nous vous invitons à pro…',
                address: 'Centre culturel de l\'école',
                price: 7.5
            })
        ];

        // Sauvegarde des événements
        for (const event of events) {
            await event.save();
        }

        // Création des lieux
        const places = [
            new Place({
                name: 'Lac des Nations',
                address: '220 Rue Marchant, Sherbrooke, QC J1J 3V2',
                latitude: 45.39735614787705,
                longitude: -71.91689524091339,
                type: 'lake'
            }),
            new Place({
                name: 'Mont Bellevue',
                address: '1440 Rue Brébeuf, Sherbrooke, QC J1H 3G2',
                latitude: 45.384403137373035,
                longitude: -71.90838527840393,
                type: 'mountain'
            }),
            new Place({
                name: 'Parc du Marquis-de-Montcalm',
                address: '2050 Boulevard de Portland, Sherbrooke, QC J1J 1V9',
                latitude: 45.4027690715768,
                longitude: -71.92505078484756,
                type: 'park'
            }),
            new Place({
                name: 'Musée des Beaux-Arts de Sherbrooke',
                address: '241 Rue Dufferin, Sherbrooke, QC J1H 4M6',
                latitude: 45.40512836634967,
                longitude: -71.89451092953463,
                type: 'museum'
            }),
            new Place({
                name: 'Bar le King Hall',
                address: '286 Rue King O, Sherbrooke, QC J1H 1R1',
                latitude: 45.39951239224474,
                longitude: -71.89357917567749,
                type: 'bar'
            }),
            new Place({
                name: 'Restaurant Auguste',
                address: '82 Rue Wellington N, Sherbrooke, QC J1H 5B8',
                latitude: 45.402838864827885,
                longitude: -71.8908885337172,
                type: 'restaurant'
            }),
            new Place({
                name: 'Théâtre Granada',
                address: '53 Rue Wellington N, Sherbrooke, QC J1H 5A9',
                latitude: 45.402258864841365,
                longitude: -71.89113342116276,
                type: 'museum'
            }),
            new Place({
                name: 'Parc du Domaine-Howard',
                address: '1300 Boulevard de Portland, Sherbrooke, QC J1J 1V9',
                latitude: 45.40254739428749,
                longitude: -71.90956490502606,
                type: 'park'
            }),
            new Place({
                name: 'Café Bla-Bla',
                address: '2 Rue Wellington S, Sherbrooke, QC J1H 5C7',
                latitude: 45.4012356634831,
                longitude: -71.8899603462312,
                type: 'restaurant'
            }),
            new Place({
                name: 'Boquébière',
                address: '50 Rue Wellington N, Sherbrooke, QC J1H 5B7',
                latitude: 45.402366829104224,
                longitude: -71.89065424328896,
                type: 'bar'
            }),
            new Place({
                name: 'Parc Lucien-Blanchard',
                address: '755 Rue Cabana, Sherbrooke, QC J1K 2M2',
                latitude: 45.39460537908967,
                longitude: -71.92741076461404,
                type: 'park'
            }),
            new Place({
                name: 'Palais des sports Léopold-Drolet',
                address: '360 Rue du Cégep, Sherbrooke, QC J1E 2J9',
                latitude: 45.41032837169569,
                longitude: -71.88383031529085,
                type: 'museum'
            })
        ];

        // Sauvegarde des lieux
        for (const place of places) {
            await place.save();
        }

        console.log("Données d'échantillon ajoutées avec succès");
    } catch (err) {
        console.error('Erreur lors de l\'ajout des données:', err);
    }
}

// Exécution du script
(async () => {
    await connectToDatabase();
    // await addSampleData(); // NOTE IMPORTANTE : Décommenter cette ligne et exécuter le fichier va vider entièrement la base de données et ajouter des données d'échantillon
    await closeDatabaseConnection();
})();