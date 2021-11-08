const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const Client = require('../../../models').client
const ClientLog = require('../../../models').client_log

// Create
routes.post('/', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name'])

        if (!result) { throw devMessage }

        if (await Client.findOne({ where: { name: req.body.name } }) != null) {
            throw 'Este cliente ya ha sido registrado.'
        }

        const client = await Client.create({ name: req.body.name })

        await ClientLog.create({
            clientId: client.id,
            name: client.name,
            createdAt: client.createdAt,
            updatedAt: client.updatedAt,
            deletedAt: client.deletedAt
        })

        res.status(201).json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find all
routes.get('/', async (req, res) => {

    try {

        const clients = await Client.findAll()
        res.json(clients)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find By Pk
routes.get('/:id', async (req, res) => {

    try {

        const client = await Client.findByPk(req.params.id)

        if (client == null) { throw 'No se encontro cliente.' }

        res.json(client)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Update
routes.put('/:id', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name'])

        if (!result) { throw devMessage }

        const client = await Client.findByPk(req.params.id)

        if (client == null) { throw 'No se encontro cliente.' }

        client.name = req.body.name

        await client.save()

        await ClientLog.create({
            clientId: client.id,
            name: client.name,
            createdAt: client.createdAt,
            updatedAt: client.updatedAt,
            deletedAt: client.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Delete
routes.delete('/:id', async (req, res) => {

    try {

        var client = await Client.findByPk(req.params.id)

        if (client == null) { throw 'No se encontro cliente.' }

        client = await client.destroy()

        await ClientLog.create({
            clientId: client.id,
            name: client.name,
            createdAt: client.createdAt,
            updatedAt: client.updatedAt,
            deletedAt: client.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes