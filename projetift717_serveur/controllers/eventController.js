const Event = require('../models/Event');
const User = require('../models/User');
const {notifyUser} = require('../utils/socketUtils');

// Récupérer tous les événements
exports.getAllEvents = async (req, res) => {
    try {
        const events = await Event.find();
        res.json(events);
    } catch (err) {
        console.error(err);
        res.status(500).json({error: err.message});
    }
};

// Récupérer un événement par son ID
exports.getEventById = async (req, res) => {
    try {
        const event = await Event.findById(req.params.id);
        if (!event) {
            return res.status(404).json({message: 'Événement non trouvé'});
        }
        res.json(event);
    } catch (err) {
        console.error(err);
        res.status(500).json({error: err.message});
    }
};

exports.getEventsRelatedToUser = async (req, res) => {
    try {
        const events = await Event.find({ users: req.params.id });
        if (!events) {
            return res.status(404).json({error: err.message});
        }
        res.json(events);
    } catch (err) {
        console.error(err);
        res.status(500).json({error: err.message});
    }
}

// Créer un événement
exports.createEvent = async (req, res) => {
    try {
        const newEvent = new Event(req.body);
        const savedEvent = await newEvent.save();
        res.status(201).json(savedEvent);
    } catch (err) {
        console.error(err);
        res.status(400).json({error: err.message});
    }
};

// Mettre à jour un événement
exports.updateEvent = async (req, res) => {
    try {
        const updatedEvent = await Event.findByIdAndUpdate(req.params.id, req.body, {new: true});
        if (!updatedEvent) {
            return res.status(404).json({message: 'Événement non trouvé'});
        }
        res.json(updatedEvent);
    } catch (err) {
        console.error(err);
        res.status(400).json({error: err.message});
    }
};

// Supprimer un événement
exports.deleteEvent = async (req, res) => {
    try {
        const deletedEvent = await Event.findByIdAndDelete(req.params.id);
        if (!deletedEvent) {
            return res.status(404).json({message: 'Événement non trouvé'});
        }
        res.json({message: 'Événement supprimé avec succès'});
    } catch (err) {
        console.error(err);
        res.status(500).json({error: err.message});
    }
};

// Ajouter un utilisateur à un événement
exports.addUserToEvent = async (req, res) => {
    try {
        const event = await Event.findById(req.params.id);
        if (!event) {
            return res.status(404).json({message: 'Événement non trouvé'});
        }
        const user = await User.findById(req.body.userId);
        if (!user) {
            return res.status(404).json({message: 'Utilisateur non trouvé'});
        }
        event.users.push(user._id);
        user.events.push(event._id);
        await event.save();
        await user.save();

        // Notifier l'utilisateur de son inscription à l'événement
        const io = req.app.get('io');
        notifyUser(io, user._id.toString(), {type: 'EVENT_JOINED', event});

        res.json(event);
    } catch (err) {
        console.error(err);
        res.status(500).json({error: err.message});
    }
};

// Retirer un utilisateur d'un événement
exports.removeUserFromEvent = async (req, res) => {
    try {
        const event = await Event.findById(req.params.id);
        if (!event) {
            return res.status(404).json({message: 'Événement non trouvé'});
        }
        const user = await User.findById(req.body.userId);
        if (!user) {
            return res.status(404).json({message: 'Utilisateur non trouvé'});
        }
        event.users = event.users.filter(userId => userId.toString() !== req.body.userId);
        user.events = user.events.filter(eventId => eventId.toString() !== req.params.id);
        await event.save();
        await user.save();

        // Notifier l'utilisateur de sa désinscription de l'événement
        const io = req.app.get('io');
        notifyUser(io, user._id.toString(), {type: 'EVENT_LEFT', event});

        res.json(event);
    } catch (err) {
        console.error(err);
        res.status(500).json({error: err.message});
    }
};