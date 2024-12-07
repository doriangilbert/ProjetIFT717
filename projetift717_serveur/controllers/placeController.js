const Place = require('../models/Place');

// Récupérer tous les lieux
exports.getAllPlaces = async (req, res) => {
    try {
        const places = await Place.find();
        res.json(places);
    } catch (err) {
        console.error(err);
        res.status(500).json({error: err.message});
    }
};

// Récupérer un lieu par son ID
exports.getPlaceById = async (req, res) => {
    try {
        const place = await Place.findById(req.params.id);
        if (!place) {
            return res.status(404).json({message: 'Lieu non trouvé'});
        }
        res.json(place);
    } catch (err) {
        console.error(err);
        res.status(500).json({error: err.message});
    }
};

// Créer un lieu
exports.createPlace = async (req, res) => {
    try {
        const newPlace = new Place(req.body);
        const savedPlace = await newPlace.save();
        res.status(201).json(savedPlace);
    } catch (err) {
        console.error(err);
        res.status(400).json({error: err.message});
    }
};

// Mettre à jour un lieu
exports.updatePlace = async (req, res) => {
    try {
        const updatedPlace = await Place.findByIdAndUpdate(req.params.id, req.body, {new: true});
        if (!updatedPlace) {
            return res.status(404).json({message: 'Lieu non trouvé'});
        }
        res.json(updatedPlace);
    } catch (err) {
        console.error(err);
        res.status(400).json({error: err.message});
    }
};

// Supprimer un lieu
exports.deletePlace = async (req, res) => {
    try {
        const deletedPlace = await Place.findByIdAndDelete(req.params.id);
        if (!deletedPlace) {
            return res.status(404).json({message: 'Lieu non trouvé'});
        }
        res.json({message: 'Lieu supprimé avec succès'});
    } catch (err) {
        console.error(err);
        res.status(500).json({error: err.message});
    }
};
