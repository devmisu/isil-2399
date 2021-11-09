const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const auth = require('../../../middlewares/auth')
const Client = require('../../../models').client
const Project = require('../../../models').project
const Favorite = require('../../../models').favorite_project
const FavoriteLog = require('../../../models').favorite_project_log

routes.get('/', auth, async (req, res) => {

    try {

        Project.belongsTo(Client)

        const favorites = await Favorite.findAll({ where: { memberId: req.user.id } })
        const projects = await Project.findAll()

        const response = await Promise.all(
            projects.map(async (obj) => {
                const client = await obj.getClient()
                return {
                    id: obj.id,
                    title: client.name,
                    description: obj.name,
                    selected: favorites.some(e => e.projectId == obj.id)
                }
            })
        )

        res.json(response)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

routes.post('/', auth, async (req, res) => {

    try {

        if (!Array.isArray(req.body)) throw 'Debes enviar una lista valida.'

        const favorites = await Favorite.findAll({ where: { memberId: req.user.id } })

        await favorites.forEach(async (favorite) => {

            const deletedFavorite = await favorite.destroy()

            await FavoriteLog.create({
                favoriteProjectId: deletedFavorite.id,
                memberId: deletedFavorite.memberId,
                projectId: deletedFavorite.projectId,
                createdAt: deletedFavorite.createdAt,
                updatedAt: deletedFavorite.updatedAt,
                deletedAt: deletedFavorite.deletedAt
            })
        })

        await req.body.forEach(async (projectId) => {

            const newFavorite = await Favorite.create({ memberId: req.user.id, projectId: projectId })
            
            await FavoriteLog.create({
                favoriteProjectId: newFavorite.id,
                memberId: newFavorite.memberId,
                projectId: newFavorite.projectId,
                createdAt: newFavorite.createdAt,
                updatedAt: newFavorite.updatedAt,
                deletedAt: newFavorite.deletedAt
            })
        })

        res.json()

    } catch(error) {
        
        res.status(400).json({ message: error })
    }
})

module.exports = routes