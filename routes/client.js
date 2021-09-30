const express = require('express')
const routes = express.Router()

// Create
routes.post('/', (req, res) => {

    req.getConnection((err, conn) => {

        if (err) return res.send(err)

        conn.query('INSERT INTO client SET ?', [req.body], (err, rows) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.status(201).json([])
        })
    })
})

// Read
routes.get('/', (req, res) => {

    req.getConnection((err, conn) => {

        if (err) return res.send(err)

        conn.query('SELECT * FROM client', (err, rows) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json(rows)
        })
    })
})

routes.get('/:id', (req, res) => {

    req.getConnection((err, conn) => {

        if (err) return res.send(err)

        conn.query('SELECT * FROM client WHERE id = ?', [req.params.id], (err, rows) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json(rows[0] ?? [])
        })
    })
})

// Update
routes.put('/:id', (req, res) => {

    req.getConnection((err, conn) => {

        if (err) return res.send(err)

        conn.query('UPDATE client SET ? WHERE id = ?', [req.body, req.params.id], (err, rows) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json([])
        })
    })
})

// Delete
routes.delete('/:id', (req, res) => {

    req.getConnection((err, conn) => {

        if (err) return res.send(err)

        conn.query('DELETE FROM client WHERE id = ?', [req.params.id], (err, rows) => {

            if (err) return res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] })
            res.json([])
        })
    })
})

module.exports = routes