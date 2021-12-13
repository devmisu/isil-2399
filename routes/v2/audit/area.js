const express = require('express')
const routes = express.Router()
const AreaLog = require('../../../models').area_log

// All logs
routes.get('/', async (req, res) => {

    try {

        const areas = await AreaLog.findAll({ paranoid: false })
        res.json(areas)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All changes for area
routes.get('/:id', async (req, res) => {

    try {

        const areas = await AreaLog.findAll({ where: { areaId: req.params.id }, paranoid: false })

        if (areas == null) { throw 'No hay registros de esta area.' }

        res.json(areas)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All deleted areas
routes.get('/deleted', async (req, res) => {

    try {

        const areas = await AreaLog.findAll({ where: { deletedAt: null }, paranoid: false })

        if (areas == null) { throw 'No hay registros de esta area. '}

        res.json(areas)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes