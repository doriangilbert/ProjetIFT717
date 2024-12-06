const express = require('express');
const router = express.Router();
const userController = require('../controllers/userController');
const {authenticateToken, checkAdmin} = require('../middlewares/authMiddleware');

// Route permettant de récupérer tous les utilisateurs
router.get('/', authenticateToken, checkAdmin(), userController.getAllUsers);
// Route permettant de récupérer un utilisateur par son ID
router.get('/:id', authenticateToken, userController.getUserById);
// Route permettant de créer un utilisateur
router.post('/', authenticateToken, checkAdmin(), userController.createUser);
// Route permettant de mettre à jour un utilisateur
router.put('/:id', authenticateToken, checkAdmin(), userController.updateUser);
// Route permettant de supprimer un utilisateur
router.delete('/:id', authenticateToken, checkAdmin(), userController.deleteUser);
// Route permettant de se connecter
router.post('/login', userController.login);
// Route permettant de s'inscrire
router.post('/register', userController.register);

// Export du router
module.exports = router;