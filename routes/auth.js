require('dotenv').config()
const express = require('express')
const routes = express.Router()
const {OAuth2Client} = require('google-auth-library');

// Login
routes.post('/', (req, res) => {

    const idToken = req.body['idToken']

    if (idToken == null || idToken == '') {
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

        // Que hago con el 'sub'??
        // Usar 'email' para sacar info de bd
        // Crear sesion y retornar data

        console.log(payload)
        res.json(payload)
    })
})

module.exports = routes