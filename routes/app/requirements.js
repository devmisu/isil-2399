require('dotenv').config()
const express = require('express')
const routes = express.Router()
const jwt = require('jsonwebtoken');
const util = require('../../common/util')
const auth = require('../../middlewares/auth')

// Get requirements by date
routes.get('/', auth, async (req, res) => {

    try {

        const { result, devMessage } = util.sanitize(req.query, ['date'])

        if (!result) {
            return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
        }

        const params = [
            req.user.email,
            req.query['date']
        ]

        const conn = await util.getConnection(req)

        Promise.all([

            util.execQuery(conn, 'app_get_worked_hours', params),
            util.execQuery(conn, 'app_get_current_requirements', params)

        ]).then(results => {

            const workedHours = results[0][0].workedHours
            const requirements = results[1]

            res.json({ workedHours, requirements })
        })
        
    } catch (err) {

        res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] ?? err })
    }
})

// Get requirement detail
routes.get('/:id', auth, async (req, res) => {

    try {

        const params = [
            req.user.email,
            req.params.id
        ]

        const conn = await util.getConnection(req)
        const requirementDetail = await util.execQuery(conn, 'app_get_requirement_detail', params);

        res.json(requirementDetail[0])
        
    } catch (err) {

        res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] ?? err })
    }
})

// Create requirement
routes.post('/', auth, async (req, res) => {

    try {

        const { result, devMessage } = util.sanitize(req.body, ['id_requirement', 'date', 'hours'])

        if (!result) {
            return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
        }

        const conn = await util.getConnection(req)
        const member = await util.execQuery(conn, 'get_member_by_email', [req.user.email])

        const params = [
            member[0].id,
            req.body['id_requirement'],
            req.body['date'],
            req.body['hours'],
            req.body['comment']
        ]

        await util.execQuery(conn, 'app_create_requirement', params)
    
        res.status(201).json()

    } catch (err) {

        console.log(err)
        res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] ?? err })
    }
})

module.exports = routes