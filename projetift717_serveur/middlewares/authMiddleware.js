const jwt = require('jsonwebtoken');
const secret = process.env.JWT_SECRET;
const invalidTokens = [];

// Middleware pour vérifier le token
function authenticateToken(req, res, next) {
    const token = req.headers.authorization?.split(' ')[1];

    if (!token) {
        return res.status(401).json({error: 'Accès refusé : Token non fourni'});
    }

    if (invalidTokens.includes(token)) {
        return res.status(401).json({error: 'Token invalidé'});
    }

    try {
        const decoded = jwt.verify(token, secret);
        req.user = decoded;
        next();
    } catch (err) {
        return res.status(401).json({error: 'Token invalide'});
    }
}

// Middleware pour vérifier si l'utilisateur est admin
function checkAdmin() {
    return (req, res, next) => {
        if (req.user.isAdmin) {
            return next();
        }
        res.status(403).json({message: 'Accès refusé : rôle non autorisé'});
    };
}

// Export des middlewares
module.exports = {authenticateToken, checkAdmin};