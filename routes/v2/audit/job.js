const express = require('express')
const routes = express.Router()
const JobLog = require('../../../models').job_log
const Sequelize = require('sequelize')

// All logs
routes.get('/', async (req, res) => {

    try {

        const jobs = await JobLog.findAll({ paranoid: false })
        res.json(jobs)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All changes for job
routes.get('/changes/:id', async (req, res) => {

    try {

        const jobs = await JobLog.findAll({ where: { jobId: req.params.id }, paranoid: false })

        if (jobs == null) { throw 'No hay registros de este cargo.' }

        res.json(jobs)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All deleted jobs
routes.get('/deleted', async (req, res) => {

    try {

        const jobs = await JobLog.findAll({ where: {
            deletedAt: {
                [Sequelize.Op.not]: null
            } 
        }, paranoid: false })

        if (jobs == null) { throw 'No hay registros de este cargo.'}

        res.json(jobs)

    } catch(error) {

        console.log(error)

        res.status(400).json({ message: error })
    }
})

module.exports = routes