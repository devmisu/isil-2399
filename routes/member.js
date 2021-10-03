const express = require('express')
const routes = express.Router()
const util = require('../common/util')

// Create
routes.post('/', (req, res) => {

    const { result, devMessage } = util.sanitize(req.body, ['id_job', 'first_name', 'last_name', 'email', 'phone'])

    if (!result) {
        return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
    }

    const params = [
        req.body['id_job'],
        req.body['first_name'],
        req.body['last_name'],
        req.body['email'],
        req.body['phone']
    ]

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('CALL create_member(?)', [params], (err, _) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.status(201).json()
        })
    })
})

// Read
routes.get('/', (req, res) => {

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('CALL get_members()', (err, rows) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json(rows[0])
        })
    })
})

routes.get('/:id', (req, res) => {

    const params = [
        req.params.id
    ]

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('CALL get_member(?)', [params], (err, rows) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json(rows[0][0])
        })
    })
})

routes.put('/:id', (req, res) => {

    const { result, devMessage } = util.sanitize(req.body, ['id_job', 'first_name', 'last_name', 'email', 'phone'])

    if (!result) {
        return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
    }

    const params = [
        req.params.id,
        req.body['id_job'],
        req.body['first_name'],
        req.body['last_name'],
        req.body['email'],
        req.body['phone']
    ]

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('CALL update_member(?)', [params], (err, _) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json()
        })
    })
})

routes.delete('/:id', (req, res) => {

    const params = [
        req.params.id
    ]

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('CALL delete_member(?)', [params], (err, _) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json()
        })
    })
})

module.exports = routes