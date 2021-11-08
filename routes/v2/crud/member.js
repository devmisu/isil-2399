const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const Member = require('../../../models').member
const MemberLog = require('../../../models').member_log

// Create
routes.post('/', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['firstName', 'lastName', 'email', 'phone', 'jobId'])

        if (!result) { throw devMessage }

        if (await Member.findOne({ where: { email: req.body.email } }) != null) {
            throw 'Este usuario ya ha sido registrado.'
        }

        const member = await Member.create({
            firstName: req.body.firstName,
            lastName: req.body.lastName,
            email: req.body.email,
            phone: req.body.phone,
            jobId: req.body.jobId
        })

        await MemberLog.create({
            memberId: member.id,
            firstName: member.firstName,
            lastName: member.lastName,
            email: member.email,
            phone: member.phone,
            jobId: member.jobId,
            createdAt: member.createdAt,
            updatedAt: member.updatedAt,
            deletedAt: member.deletedAt
        })

        res.status(201).json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find all
routes.get('/', async (req, res) => {

    try {

        const members = await Member.findAll()
        res.json(members)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find By Pk
routes.get('/:id', async (req, res) => {

    try {

        const member = await Member.findByPk(req.params.id)

        if (member == null) { throw 'No se encontro usuario.' }

        res.json(member)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Update
routes.put('/:id', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['firstName', 'lastName', 'phone', 'jobId'])

        if (!result) { throw devMessage }

        const member = await Member.findByPk(req.params.id)

        if (member == null) { throw 'No se encontro usuario.' }

        member.firstName = req.body.firstName
        member.lastName = req.body.lastName
        member.phone = req.body.phone
        member.jobId = req.body.jobId

        await member.save()

        await MemberLog.create({
            memberId: member.id,
            firstName: member.firstName,
            lastName: member.lastName,
            email: member.email,
            phone: member.phone,
            jobId: member.jobId,
            createdAt: member.createdAt,
            updatedAt: member.updatedAt,
            deletedAt: member.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Delete
routes.delete('/:id', async (req, res) => {

    try {

        var member = await Member.findByPk(req.params.id)

        if (member == null) { throw 'No se encontro usuario.' }

        member = await member.destroy()

        await MemberLog.create({
            memberId: member.id,
            firstName: member.firstName,
            lastName: member.lastName,
            email: member.email,
            phone: member.phone,
            jobId: member.jobId,
            createdAt: member.createdAt,
            updatedAt: member.updatedAt,
            deletedAt: member.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes