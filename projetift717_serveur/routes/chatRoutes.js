const express = require('express');
const router = express.Router();
const chatController = require('../controllers/chatController');
const {authenticateToken, checkAdmin} = require('../middlewares/authMiddleware');

// Route permettant de chatter avec l'intelligence artificielle
router.post('/', authenticateToken, chatController.getChat);

// Export du router
module.exports = router;