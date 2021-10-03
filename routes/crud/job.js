const express = require('express')
const routes = express.Router()
const util = require('../../common/util')

// Create
routes.post('/', (req, res) => {

    const { result, devMessage } = util.sanitize(req.body, ['id_area', 'name'])

    if (!result) {
        return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
    }

    const params = [
        req.body['name'],
        req.body['id_area']
    ]

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('CALL create_job(?)', [params], (err, _) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.status(201).json()
        })
    })
})

// Read
routes.get('/', (req, res) => {

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('CALL get_jobs()', (err, rows) => {

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

        conn.query('CALL get_job(?)', [params], (err, rows) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json(rows[0][0])
        })
    })
})

routes.put('/:id', (req, res) => {

    const { result, devMessage } = util.sanitize(req.body, ['id_area', 'name'])

    if (!result) {
        return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
    }

    const params = [
        req.params.id,
        req.body['name'],
        req.body['id_area']
    ]

    req.getConnection((err, conn) => {

        if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err })

        conn.query('CALL update_job(?)', [params], (err, _) => {

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

        conn.query('CALL delete_job(?)', [params], (err, _) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json()
        })
    })
})

module.exports = routes