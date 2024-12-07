const express = require('express');
const router = express.Router();
const placeController = require('../controllers/placeController');
const {authenticateToken, checkAdmin} = require('../middlewares/authMiddleware');

// Route permettant de récupérer tous les lieux
router.get('/', authenticateToken, placeController.getAllPlaces);
// Route permettant de récupérer un lieu par son ID
router.get('/:id', authenticateToken, placeController.getPlaceById);
// Route permettant de créer un lieu
router.post('/', authenticateToken, checkAdmin(), placeController.createPlace);
// Route permettant de mettre à jour un lieu
router.put('/:id', authenticateToken, checkAdmin(), placeController.updatePlace);
// Route permettant de supprimer un lieu
router.delete('/:id', authenticateToken, checkAdmin(), placeController.deletePlace);

// Export du router
module.exports = router;