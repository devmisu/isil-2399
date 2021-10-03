require('dotenv').config()
const express = require('express')
const routes = express.Router()
const {OAuth2Client} = require('google-auth-library');
const jwt = require('jsonwebtoken');
const util = require('../../common/util')
const auth = require('../../middlewares/auth')

// Login
routes.post('/login', (req, res) => {

    const { result, devMessage } = util.sanitize(req.body, ['idToken'])

    if (!result) {
        return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
    }

    const idToken = req.body['idToken']
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

        const token = jwt.sign({ user_id: userid, email }, process.env.JWT_SECRET_KEY, { expiresIn: '15m' })

        req.getConnection((err, conn) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

            conn.query('CALL get_member_info(?)', [email], (err, rows) => {

                if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })

                if (rows[0][0] == null) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: 'El usuario no existe en la base de datos.' })

                res.json({
                    session: {
                        type: 'Bearer',
                        token: token
                    },
                    user: rows[0][0]
                })
            })
        })
    })
})

// Get current requirements
routes.get('/requirements', auth, (req, res) => {

    const { result, devMessage } = util.sanitize(req.query, ['date'])

    if (!result) {
        return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
    }

    const params = [
        req.user.email,
        req.query['date']
    ]

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('CALL get_current_requirements(?)', [params], (err, rows) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })

            if (rows[0] == null) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: 'El usuario no existe en la base de datos.' })

            res.json(rows[0])
        })
    })
})

module.exports = routes