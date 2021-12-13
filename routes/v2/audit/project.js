const express = require('express')
const routes = express.Router()
const ProjectLog = require('../../../models').project_log
const Sequelize = require('sequelize')

// All logs
routes.get('/', async (req, res) => {

    try {

        const projects = await ProjectLog.findAll({ paranoid: false })
        res.json(projects)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All changes for project
routes.get('/changes/:id', async (req, res) => {

    try {

        const projects = await ProjectLog.findAll({ where: { projectId: req.params.id }, paranoid: false })

        if (projects == null) { throw 'No hay registros de este proyecto.' }

        res.json(projects)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All deleted projects
routes.get('/deleted', async (req, res) => {

    try {

        const projects = await ProjectLog.findAll({ where: {
            deletedAt: {
                [Sequelize.Op.not]: null
            } 
        }, paranoid: false })

        if (projects == null) { throw 'No hay registros de este proyecto.'}

        res.json(projects)

    } catch(error) {

        console.log(error)

        res.status(400).json({ message: error })
    }
})

module.exports = routes