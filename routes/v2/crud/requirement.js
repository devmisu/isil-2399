const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const Requirement = require('../../../models').requirement
const RequirementLog = require('../../../models').requirement_log

// Create
routes.post('/', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name', 'projectId'])

        if (!result) { throw devMessage }

        if (await Requirement.findOne({ where: { name: req.body.name } }) != null) {
            throw 'Este requerimiento ya ha sido registrado.'
        }

        const requirement = await Requirement.create({ name: req.body.name, projectId: req.body.projectId })

        await RequirementLog.create({
            requirementId: requirement.id,
            projectId: requirement.projectId,
            name: requirement.name,
            createdAt: requirement.createdAt,
            updatedAt: requirement.updatedAt,
            deletedAt: requirement.deletedAt
        })

        res.status(201).json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find all
routes.get('/', async (req, res) => {

    try {

        const requirements = await Requirement.findAll()
        res.json(requirements)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find By Pk
routes.get('/:id', async (req, res) => {

    try {

        const requirement = await Requirement.findByPk(req.params.id)

        if (requirement == null) { throw 'No se encontro requerimiento.' }

        res.json(requirement)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Update
routes.put('/:id', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name', 'projectId'])

        if (!result) { throw devMessage }

        const requirement = await Requirement.findByPk(req.params.id)

        if (requirement == null) { throw 'No se encontro requerimiento.' }

        requirement.name = req.body.name
        requirement.projectId = req.body.projectId

        await requirement.save()

        await RequirementLog.create({
            requirementId: requirement.id,
            projectId: requirement.projectId,
            name: requirement.name,
            createdAt: requirement.createdAt,
            updatedAt: requirement.updatedAt,
            deletedAt: requirement.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Delete
routes.delete('/:id', async (req, res) => {

    try {

        var requirement = await Requirement.findByPk(req.params.id)

        if (requirement == null) { throw 'No se encontro requerimiento.' }

        requirement = await requirement.destroy()

        await RequirementLog.create({
            requirementId: requirement.id,
            projectId: requirement.projectId,
            name: requirement.name,
            createdAt: requirement.createdAt,
            updatedAt: requirement.updatedAt,
            deletedAt: requirement.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes