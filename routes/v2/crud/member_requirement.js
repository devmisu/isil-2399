const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const fcm = require('../../../common/fcm')
const MemberRequirement = require('../../../models').member_requirement
const MemberRequirementLog = require('../../../models').member_requirement_log

// Create
routes.post('/', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['memberId', 'requirementId', 'estimateStartDate', 'estimateEndDate', 'estimateHours'])

        if (!result) { throw devMessage }

        if (await MemberRequirement.findOne({ where: { memberId: req.body.memberId, requirementId: req.body.requirementId } }) != null) {
            throw 'Esta tarea ya ha sido asignada al usuario.'
        }

        const member_requirement = await MemberRequirement.create({
            memberId: req.body.memberId,
            requirementId: req.body.requirementId,
            estimateStartDate: req.body.estimateStartDate,
            estimateEndDate: req.body.estimateEndDate,
            estimateHours: req.body.estimateHours,
            realHours: req.body.realHours,
            comment: req.body.comment
        })

        await MemberRequirementLog.create({
            memberRequirementId: member_requirement.id,
            memberId: member_requirement.memberId,
            requirementId: member_requirement.requirementId,
            estimateStartDate: member_requirement.estimateStartDate,
            estimateEndDate: member_requirement.estimateEndDate,
            estimateHours: member_requirement.estimateHours,
            realHours: member_requirement.realHours,
            comment: member_requirement.comment,
            createdAt: member_requirement.createdAt,
            updatedAt: member_requirement.updatedAt,
            deletedAt: member_requirement.deletedAt
        })

        await fcm.pushNotfToMember(req.body.memberId, 'Se te asigno un requerimiento nuevo!', '')

        res.status(201).json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find all
routes.get('/', async (req, res) => {

    try {

        const member_requirements = await MemberRequirement.findAll()
        res.json(member_requirements)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Find By Pk
routes.get('/:id', async (req, res) => {

    try {

        const member_requirement = await MemberRequirement.findByPk(req.params.id)

        if (member_requirement == null) { throw 'No se encontro la tarea asignada al usuario.' }

        res.json(member_requirement)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Update
routes.put('/:id', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['memberId', 'requirementId', 'estimateStartDate', 'estimateEndDate', 'estimateHours'])

        if (!result) { throw devMessage }

        const member_requirement = await MemberRequirement.findByPk(req.params.id)

        if (member_requirement == null) { throw 'No se encontro la tarea asignada al usuario.' }

        member_requirement.memberId = req.body.memberId
        member_requirement.requirementId = req.body.requirementId
        member_requirement.estimateStartDate = req.body.estimateStartDate
        member_requirement.estimateEndDate = req.body.estimateEndDate
        member_requirement.estimateHours = req.body.estimateHours
        member_requirement.realHours = req.body.realHours
        member_requirement.comment = req.body.comment

        await member_requirement.save()

        await MemberRequirementLog.create({
            memberRequirementId: member_requirement.id,
            memberId: member_requirement.memberId,
            requirementId: member_requirement.requirementId,
            estimateStartDate: member_requirement.estimateStartDate,
            estimateEndDate: member_requirement.estimateEndDate,
            estimateHours: member_requirement.estimateHours,
            realHours: member_requirement.realHours,
            comment: member_requirement.comment,
            createdAt: member_requirement.createdAt,
            updatedAt: member_requirement.updatedAt,
            deletedAt: member_requirement.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// Delete
routes.delete('/:id', async (req, res) => {

    try {

        var member_requirement = await MemberRequirement.findByPk(req.params.id)

        if (member_requirement == null) { throw 'No se encontro la tarea asignada al usuario.' }

        member_requirement = await member_requirement.destroy()

        await MemberRequirementLog.create({
            memberRequirementId: member_requirement.id,
            memberId: member_requirement.memberId,
            requirementId: member_requirement.requirementId,
            estimateStartDate: member_requirement.estimateStartDate,
            estimateEndDate: member_requirement.estimateEndDate,
            estimateHours: member_requirement.estimateHours,
            realHours: member_requirement.realHours,
            comment: member_requirement.comment,
            createdAt: member_requirement.createdAt,
            updatedAt: member_requirement.updatedAt,
            deletedAt: member_requirement.deletedAt
        })

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

module.exports = routes