const mongoose = require('mongoose');

// Schéma pour les événements
const eventSchema = new mongoose.Schema({
    name: {type: String, required: true},
    date: {type: Date, required: true},
    description: String,
    address: String,
    price: Number,
    users: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}]
});

// Création du modèle pour les événements
const Event = mongoose.model('Event', eventSchema);

module.exports = Event;
