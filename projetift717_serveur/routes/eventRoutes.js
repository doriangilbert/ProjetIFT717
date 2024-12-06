const express = require('express');
const router = express.Router();
const eventController = require('../controllers/eventController');
const {authenticateToken, checkAdmin} = require('../middlewares/authMiddleware');

// Route permettant de récupérer tous les événements
router.get('/', authenticateToken, eventController.getAllEvents);
// Route permettant de récupérer un événement par son ID
router.get('/:id', authenticateToken, eventController.getEventById);
// Route permettant de créer un événement
router.post('/', authenticateToken, checkAdmin(), eventController.createEvent);
// Route permettant de mettre à jour un événement
router.put('/:id', authenticateToken, checkAdmin(), eventController.updateEvent);
// Route permettant de supprimer un événement
router.delete('/:id', authenticateToken, checkAdmin(), eventController.deleteEvent);
// Route permettant d'ajouter un utilisateur à un événement
router.put('/:id/addUser', authenticateToken, eventController.addUserToEvent);
// Route permettant de retirer un utilisateur d'un événement
router.put('/:id/removeUser', authenticateToken, eventController.removeUserFromEvent);

// Export du router
module.exports = router;