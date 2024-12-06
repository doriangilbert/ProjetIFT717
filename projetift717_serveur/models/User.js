const mongoose = require('mongoose');
const bcrypt = require('bcrypt');

// Schéma pour les utilisateurs
const userSchema = new mongoose.Schema({
    name: {type: String, required: true},
    surname: {type: String, required: true},
    email: {type: String, required: true, unique: true},
    password: {type: String, required: true},
    phone: String,
    isAdmin: {type: Boolean, default: false, required: true},
    events: [{type: mongoose.Schema.Types.ObjectId, ref: 'Event'}],
});

// Middleware pour hacher le mot de passe avant de l'enregistrer
userSchema.pre('save', async function (next) {
    const user = this;
    if (user.isModified('password')) {
        try {
            user.password = await bcrypt.hash(user.password, 10);
        } catch (err) {
            return next(err);
        }
    }
    next();
});

// Middleware pour hacher le mot de passe avant de mettre à jour
userSchema.pre('findOneAndUpdate', async function (next) {
    const update = this.getUpdate();
    if (update.password) {
        try {
            update.password = await bcrypt.hash(update.password, 10);
        } catch (err) {
            return next(err);
        }
    }
    next();
});

// Création du modèle pour les utilisateurs
const User = mongoose.model('User', userSchema);

// Export du modèle
module.exports = User;