const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const Job = require('../../../models').job
const JobLog = require('../../../models').job_log

// Create
routes.post('/', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name', 'areaId'])

        if (!result) { throw devMessage }

        if (await Job.findOne({ where: { name: req.body.name } }) != null) {
            throw 'Esta cargo ya ha sido registrado.'
        }

        const job = await Job.create({ name: req.body.name, areaId: req.body.areaId })

        await JobLog.create({
            jobId: job.id,
            areaId: job.areaId,
            name: job.name,
            createdAt: job.createdAt,
            updatedAt: job.updatedAt,
            deletedAt: job.deletedAt
        })

        res.status(201).json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find all
routes.get('/', async (req, res) => {

    try {

        const jobs = await Job.findAll()
        res.json(jobs)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find By Pk
routes.get('/:id', async (req, res) => {

    try {

        const job = await Job.findByPk(req.params.id)

        if (job == null) { throw 'No se encontro cargo.' }

        res.json(job)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Update
routes.put('/:id', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['name', 'areaId'])

        if (!result) { throw devMessage }

        const job = await Job.findByPk(req.params.id)

        if (job == null) { throw 'No se encontro cargo.' }

        job.name = req.body.name
        job.areaId = req.body.areaId

        await job.save()

        await JobLog.create({
            jobId: job.id,
            areaId: job.areaId,
            name: job.name,
            createdAt: job.createdAt,
            updatedAt: job.updatedAt,
            deletedAt: job.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Delete
routes.delete('/:id', async (req, res) => {

    try {

        var job = await Job.findByPk(req.params.id)

        if (job == null) { throw 'No se encontro cargo.' }

        job = await job.destroy()

        await JobLog.create({
            jobId: job.id,
            areaId: job.areaId,
            name: job.name,
            createdAt: job.createdAt,
            updatedAt: job.updatedAt,
            deletedAt: job.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes