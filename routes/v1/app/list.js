require('dotenv').config()
const express = require('express')
const routes = express.Router()
const jwt = require('jsonwebtoken');
const util = require('../../common/util')
const auth = require('../../middlewares/auth')

// Get list by id
routes.get('/', auth, async (req, res) => {

    try {

        const { result, devMessage } = util.sanitize(req.query, ['id'])

        if (!result) {
            return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
        }

        const params = [
            req.query['parent']
        ]

        var list

        const conn = await util.getConnection(req)

        switch (req.query['id']) {
            case 'client':
                list = await util.execQuery(conn, 'app_get_clients', null)
                break
            case 'project':
                list = await util.execQuery(conn, 'app_get_projects', [params])
                break
            case 'requirement':
                list = await util.execQuery(conn, 'app_get_requirements', [params])
                break
            default:
                return res.status(400).json({ message: 'Ocurrio un error inesperado.', devMessage: 'id invalido.' })
        }

        res.json(list)
        
    } catch (err) {

        res.status(500).json({ message: 'Ocurrio un error inesperado.', devMessage: err['sqlMessage'] ?? err })
    }
})

module.exports = routes