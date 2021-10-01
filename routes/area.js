const express = require('express')
const routes = express.Router()
const util = require('../common/util')

// Create
routes.post('/', (req, res) => {

    const { result, devMessage } = util.sanitize(req.body, ['name'])

    if (!result) {
        return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
    }

    const params = {
        name: req.body['name'],
        created_at: util.currentDate()
    }

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('INSERT INTO area SET ?', [params], (err, _) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.status(201).json([])
        })
    })
})

module.exports = routes