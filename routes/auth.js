require('dotenv').config()
const express = require('express')
const routes = express.Router()
const {OAuth2Client} = require('google-auth-library');
const jwt = require('jsonwebtoken');

// Login
routes.post('/', (req, res) => {

    const { idToken } = req.body;

    if (!idToken) {
        return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: 'Enviar campo idToken' })
    }

    const client = new OAuth2Client(process.env.OAUTH_CLIENT_ID)

    client.verifyIdToken({ idToken: idToken }, (err, ticket) => {

        if (err) return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: err.message })

        const payload = ticket.getPayload()
        const userid = payload['sub']
        const domain = payload['hd']
        const email = payload['email']

        if (domain != 'solera.pe') {
            return res.status(400).json({ message: 'El email ingresado no pertenece a Solera.' })
        }

        const token = jwt.sign({ user_id: userid, email }, process.env.JWT_SECRET_KEY, { expiresIn: '1m' })

        res.json({
            session: {
                type: 'Bearer',
                token: token
            },
            user: {
                email: email
            }
        })
    })
})

module.exports = routes