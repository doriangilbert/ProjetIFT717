require('dotenv').config();
const { Mistral } = require('@mistralai/mistralai');

const apiKey = process.env.MISTRALAI_API_KEY;
const client = new Mistral({ apiKey: apiKey });

// Chatter avec l'intelligence artificielle
exports.getChat = async (req, res) => {
    try {
        const contextMessages = [
            { role: 'system', content: 'You are a helpful assistant specialized in recommending places and events in Sherbrooke.' },
            { role: 'user', content: '' }
        ];

        const userMessage = { role: 'user', content: req.body.message };
        const messages = [...contextMessages, userMessage];

        const chatResponse = await client.chat.complete({
            model: "open-mistral-nemo",
            messages: messages
        });

        res.json({ message: chatResponse.choices[0].message.content });
    } catch (err) {
        console.error(err);
        res.status(500).json({ error: err.message });
    }
};