const express = require('express');
const app = express();
const mongoose = require('mongoose');
require('dotenv').config();
const cors = require('cors');
const userRoutes = require('./routes/userRoutes');
const eventRoutes = require('./routes/eventRoutes');
const placeRoutes = require('./routes/placeRoutes');
const chatRoutes = require('./routes/chatRoutes');
const notificationRoutes = require('./routes/notificationRoutes');
const { Server } = require('socket.io');
const http = require('http');
const {setupSocketIO} = require('./utils/socketUtils');

// Middleware pour parser les corps de requêtes JSON
app.use(express.json());

// Middleware CORS
app.use(cors({
    origin: '*',
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS']
}));

// Connexion à MongoDB
const uri = process.env.DB_URI;
if (!uri) {
    console.error('Erreur : DB_URI n\'est pas défini dans le fichier .env ou fichier .env manquant');
    process.exit(1);
}
mongoose.connect(uri)
    .then(() => console.log('Connecté à MongoDB'))
    .catch(err => console.error(err));

// Créer le serveur HTTP
const server = http.createServer(app);

// Créer le serveur Socket.IO
const io = new Server(server, {
    cors: {
        origin: '*',
        methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS']
    }
});
app.set('io', io);

// Utilisation des routes
app.use('/users', userRoutes);
app.use('/events', eventRoutes);
app.use('/places', placeRoutes);
app.use('/chat', chatRoutes);
app.use('/notifications', notificationRoutes);

// Route pour la page d'accueil
app.get('/', (req, res) => {
    res.send(`
        <html lang="fr">
            <head>
                <title>ProjetIFT717</title>
            </head>
            <body>
                <h1>Bienvenue sur ProjetIFT717 !</h1>
                <h2>API</h2>
            <p>Vous pouvez accéder à l'API en utilisant l'adresse suivante suivie de la route souhaitée :</p>
            <strong>http://localhost:3000/</strong>
            <h2>Notifications</h2>
            <p>Vous pouvez accéder aux notifications en utilisant l'adresse suivante suivie de l'identifiant de l'utilisateur souhaité :</p>
            <strong> http://localhost:8080/notifications</strong>
            <h2>Note importante</h2>
            <p>Hormis les deux routes précédentes et les routes de connexion et d'inscription, toutes les autres routes doivent être accédées de manière authentifiée avec un token généré suite à la connexion par la route suivante :</p>
            <strong>POST http://localhost:3000/users/login</strong>
            <p>Il est possible de s'inscrire en tant qu'utilisateur en utilisant la route suivante :</p>
            <strong>POST http://localhost:3000/users/register</strong>
            <p>Un compte administrateur est à disposition avec l'email "admin@gmail.com" et le mot de passe "admin".</p>
            </body>
        </html>
    `);
});

// Mise en place du serveur en écoute sur le port 3000
app.listen(3000, () => {
    console.log('Serveur en écoute sur le port 3000 (API)');
});

setupSocketIO(io);

server.listen(8080, () => {
    console.log('Serveur en écoute sur le port 8080 (Notifications)');
});
