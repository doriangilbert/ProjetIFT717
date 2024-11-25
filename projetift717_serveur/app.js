const express = require('express');
const app = express();
const mongoose = require('mongoose');
require('dotenv').config();
const cors = require('cors');

app.use(express.json());

app.use(cors({
    origin: '*',
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS']
}));

const uri = process.env.DB_URI;
if (!uri) {
    console.error('Erreur : DB_URI n\'est pas défini dans le fichier .env ou fichier .env manquant');
    process.exit(1);
}
mongoose.connect(uri)
    .then(() => console.log('Connecté à MongoDB'))
    .catch(err => console.error(err));

app.get('/', (req, res) => {
    res.send(`
        <html lang="fr">
            <head>
                <title>ProjetIFT717</title>
            </head>
            <body>
                <h1>Bienvenue sur ProjetIFT717 !</h1>
            </body>
        </html>
    `);
});

app.listen(3000, () => {
    console.log('Serveur en écoute sur le port 3000');
});