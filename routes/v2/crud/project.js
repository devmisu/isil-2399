const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const Project = require('../../../models').project
const ProjectLog = require('../../../models').project_log

// Create
routes.post('/', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name', 'clientId', 'managerId'])

        if (!result) { throw devMessage }

        if (await Project.findOne({ where: { name: req.body.name } }) != null) {
            throw 'Este proyecto ya ha sido registrado.'
        }

        const project = await Project.create({ name: req.body.name, clientId: req.body.clientId, managerId: req.body.managerId })

        await ProjectLog.create({
            projectId: project.id,
            clientId: project.clientId,
            managerId: project.managerId,
            name: project.name,
            createdAt: project.createdAt,
            updatedAt: project.updatedAt,
            deletedAt: project.deletedAt
        })

        res.status(201).json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find all
routes.get('/', async (req, res) => {

    try {

        const projects = await Project.findAll()
        res.json(projects)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find By Pk
routes.get('/:id', async (req, res) => {

    try {

        const project = await Project.findByPk(req.params.id)

        if (project == null) { throw 'No se encontro proyecto.' }

        res.json(project)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Update
routes.put('/:id', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name', 'clientId', 'managerId'])

        if (!result) { throw devMessage }

        const project = await Project.findByPk(req.params.id)

        if (project == null) { throw 'No se encontro proyecto.' }

        project.name = req.body.name
        project.clientId = req.body.clientId
        project.managerId = req.body.managerId

        await project.save()

        await ProjectLog.create({
            projectId: project.id,
            clientId: project.clientId,
            managerId: project.managerId,
            name: project.name,
            createdAt: project.createdAt,
            updatedAt: project.updatedAt,
            deletedAt: project.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Delete
routes.delete('/:id', async (req, res) => {

    try {

        var project = await Project.findByPk(req.params.id)

        if (project == null) { throw 'No se encontro proyecto.' }

        project = await project.destroy()

        await ProjectLog.create({
            projectId: project.id,
            clientId: project.clientId,
            managerId: project.managerId,
            name: project.name,
            createdAt: project.createdAt,
            updatedAt: project.updatedAt,
            deletedAt: project.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes