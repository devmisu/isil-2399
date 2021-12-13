const express = require('express')
const routes = express.Router()
const ClientLog = require('../../../models').client_log
const Sequelize = require('sequelize')

// All logs
routes.get('/', async (req, res) => {

    try {

        const clients = await ClientLog.findAll({ paranoid: false })
        res.json(clients)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All changes for client
routes.get('/changes/:id', async (req, res) => {

    try {

        const clients = await ClientLog.findAll({ where: { clientId: req.params.id }, paranoid: false })

        if (clients == null) { throw 'No hay registros de este cliente.' }

        res.json(clients)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All deleted clients
routes.get('/deleted', async (req, res) => {

    try {

        const clients = await ClientLog.findAll({ where: {
            deletedAt: {
                [Sequelize.Op.not]: null
            } 
        }, paranoid: false })

        if (clients == null) { throw 'No hay registros de este cliente.'}

        res.json(clients)

    } catch(error) {

        console.log(error)

        res.status(400).json({ message: error })
    }
})

module.exports = routes