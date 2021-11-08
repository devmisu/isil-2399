const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const Area = require('../../../models').area
const AreaLog = require('../../../models').area_log

// Create
routes.post('/', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name'])

        if (!result) { throw devMessage }

        if (await Area.findOne({ where: { name: req.body.name } }) != null) {
            throw 'Esta area ya ha sido registrada.'
        }

        const area = await Area.create({ name: req.body.name })

        await AreaLog.create({
            areaId: area.id,
            name: area.name,
            createdAt: area.createdAt,
            updatedAt: area.updatedAt,
            deletedAt: area.deletedAt
        })

        res.status(201).json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find all
routes.get('/', async (req, res) => {

    try {

        const areas = await Area.findAll()
        res.json(areas)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find By Pk
routes.get('/:id', async (req, res) => {

    try {

        const area = await Area.findByPk(req.params.id)

        if (area == null) { throw 'No se encontro area.' }

        res.json(area)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Update
routes.put('/:id', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name'])

        if (!result) { throw devMessage }

        const area = await Area.findByPk(req.params.id)

        if (area == null) { throw 'No se encontro area.' }

        area.name = req.body.name

        await area.save()

        await AreaLog.create({
            areaId: area.id,
            name: area.name,
            createdAt: area.createdAt,
            updatedAt: area.updatedAt,
            deletedAt: area.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Delete
routes.delete('/:id', async (req, res) => {

    try {

        var area = await Area.findByPk(req.params.id)

        if (area == null) { throw 'No se encontro area.' }

        area = await client.destroy()

        await AreaLog.create({
            areaId: area.id,
            name: area.name,
            createdAt: area.createdAt,
            updatedAt: area.updatedAt,
            deletedAt: area.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes