const express = require('express')
const routes = express.Router()
const RequirementLog = require('../../../models').requirement_log
const Sequelize = require('sequelize')

// All logs
routes.get('/', async (req, res) => {

    try {

        const requirements = await RequirementLog.findAll({ paranoid: false })
        res.json(requirements)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All changes for requirement
routes.get('/changes/:id', async (req, res) => {

    try {

        const requirements = await RequirementLog.findAll({ where: { requirementId: req.params.id }, paranoid: false })

        if (requirements == null) { throw 'No hay registros de este requerimiento.' }

        res.json(requirements)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All deleted requirements
routes.get('/deleted', async (req, res) => {

    try {

        const requirements = await RequirementLog.findAll({ where: {
            deletedAt: {
                [Sequelize.Op.not]: null
            } 
        }, paranoid: false })

        if (requirements == null) { throw 'No hay registros de este requerimiento.'}

        res.json(requirements)

    } catch(error) {

        console.log(error)

        res.status(400).json({ message: error })
    }
})

module.exports = routes