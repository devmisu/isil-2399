const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const auth = require('../../../middlewares/auth')
const Client = require('../../../models').client
const Project = require('../../../models').project
const Favorite = require('../../../models').favorite_project

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

module.exports = routes