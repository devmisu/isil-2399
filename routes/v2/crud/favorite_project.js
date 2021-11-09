const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const FavoriteProject = require('../../../models').favorite_project
const FavoriteProjectLog = require('../../../models').favorite_project_log

// Create
routes.post('/', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['memberId', 'projectId'])

        if (!result) { throw devMessage }

        if (await FavoriteProject.findAll({ where: { memberId: req.body.memberId } }).length == 3) {
            throw 'No puedes agregar mas de 3 favoritos.'
        }

        if (await FavoriteProject.findOne({ where: { memberId: req.body.memberId, projectId: req.body.projectId } }) != null) {
            throw 'Este proyecto ya esta en favoritos.'
        }

        const favorite_project = await FavoriteProject.create({ memberId: req.body.memberId, projectId: req.body.projectId })

        await FavoriteProjectLog.create({
            favoriteProjectId: favorite_project.id,
            memberId: favorite_project.memberId,
            projectId: favorite_project.projectId,
            createdAt: favorite_project.createdAt,
            updatedAt: favorite_project.updatedAt,
            deletedAt: favorite_project.deletedAt
        })

        res.status(201).json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find all
routes.get('/', async (req, res) => {

    try {

        const favorite_projects = await FavoriteProject.findAll()
        res.json(favorite_projects)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find By Pk
routes.get('/:id', async (req, res) => {

    try {

        const favorite_project = await FavoriteProject.findByPk(req.params.id)

        if (favorite_project == null) { throw 'No se encontro favorito.' }

        res.json(favorite_project)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Update
routes.put('/:id', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['memberId', 'projectId'])

        if (!result) { throw devMessage }

        const favorite_project = await FavoriteProject.findByPk(req.params.id)

        if (favorite_project == null) { throw 'No se encontro favorito.' }

        favorite_project.memberId = req.body.memberId
        favorite_project.projectId = req.body.projectId

        await favorite_project.save()

        await FavoriteProjectLog.create({
            favoriteProjectId: favorite_project.id,
            memberId: favorite_project.memberId,
            projectId: favorite_project.projectId,
            createdAt: favorite_project.createdAt,
            updatedAt: favorite_project.updatedAt,
            deletedAt: favorite_project.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Delete
routes.delete('/:id', async (req, res) => {

    try {

        var favorite_project = await FavoriteProject.findByPk(req.params.id)

        if (favorite_project == null) { throw 'No se encontro favorito.' }

        favorite_project = await favorite_project.destroy()

        await FavoriteProjectLog.create({
            favoriteProjectId: favorite_project.id,
            memberId: favorite_project.memberId,
            projectId: favorite_project.projectId,
            createdAt: favorite_project.createdAt,
            updatedAt: favorite_project.updatedAt,
            deletedAt: favorite_project.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes