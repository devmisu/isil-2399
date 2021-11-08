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
        
        var list;

        switch (req.query['id']) {

            case 'client':
                list = await Client.findAll()
                break

            case 'project':
                if (validator.valid(req.query, ['parent']).result) {
                    list = await Project.findAll({ where: { clientId: req.query['parent'] } })
                } else {
                    list = await Project.findAll()
                }
                break

            case 'requirement':
                if (validator.valid(req.query, ['parent']).result) {
                    list = await Requirement.findAll({ where: { projectId: req.query['parent'] } })
                } else {
                    list = await Requirement.findAll()
                }
                break

            default:
                throw 'id invalido.'
        }

        res.json(list.map((obj) => {
            return {
                id: obj.id,
                name: obj.name
            }
        }))

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes