const express = require('express')
const routes = express.Router()
const util = require('../common/util')

// Create
routes.post('/', (req, res) => {

    const { result, devMessage } = util.sanitize(req.body, ['first_name', 'last_name', 'email', 'phone', 'id_job'])

    if (!result) {
        return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
    }

    const params = {
        first_name: req.body['first_name'],
        last_name: req.body['last_name'],
        email: req.body['email'],
        phone: req.body['phone'],
        id_job: req.body['id_job'],
        created_at: util.currentDate()
    }

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('INSERT INTO member SET ?', [params], (err, _) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.status(201).json([])
        })
    })
})

module.exports = routes