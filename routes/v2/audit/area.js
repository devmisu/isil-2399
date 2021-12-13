const express = require('express')
const routes = express.Router()
const AreaLog = require('../../../models').area_log
const Sequelize = require('sequelize')

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
routes.get('/changes/:id', async (req, res) => {

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

        const areas = await AreaLog.findAll({ where: {
            deletedAt: {
                [Sequelize.Op.not]: null
            } 
        }, paranoid: false })

        if (areas == null) { throw 'No hay registros de esta area.'}

        res.json(areas)

    } catch(error) {

        console.log(error)

        res.status(400).json({ message: error })
    }
})

module.exports = routes