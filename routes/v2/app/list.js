const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const auth = require('../../../middlewares/auth')
const Client = require('../../../models').client
const Project = require('../../../models').project
const Requirement = require('../../../models').requirement

routes.get('/', auth, async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.query, ['id'])

        if (!result) throw devMessage
        
        switch (req.query['id']) {

            case 'client':
                res.json(await Client.findAll())
                break

            case 'project':
                res.json(await Project.findAll({ where: { clientId: req.query['parent'] } }))
                break

            case 'requirement':
                res.json(await Requirement.findAll({ where: { projectId: req.query['parent'] } }))
                break

            default:
                throw 'id invalido.'
        }

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes