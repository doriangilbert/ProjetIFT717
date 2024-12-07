const mongoose = require('mongoose');

// Schéma pour les lieux
const placeSchema = new mongoose.Schema({
    name: {type: String, required: true},
    address: {type: String, required: true},
    latitude: Number,
    longitude: Number,
    type: {
        type: String,
        enum: ['restaurant', 'bar', 'museum', 'park', 'mountain', 'lake']
    },
    openingHour: Date,
    closingHour: Date,
    preferredTime: Date
});

// Création du modèle pour les lieux
const Place = mongoose.model('Place', placeSchema);

module.exports = Place;