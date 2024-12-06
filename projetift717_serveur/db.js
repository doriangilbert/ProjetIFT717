const mongoose = require('mongoose');
const User = require('./models/User');
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