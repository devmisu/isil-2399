const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const auth = require('../../../middlewares/auth')
const MemberRequirement = require('../../../models').member_requirement
const MemberRequirementLog = require('../../../models').member_requirement_log
const Requirement = require('../../../models').requirement
const Member = require('../../../models').member
const Project = require('../../../models').project
const Client = require('../../../models').client
const { Op } = require("sequelize");

routes.get('/', auth, async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.query, ['date'])

        if (!result) throw devMessage
        
        Requirement.belongsTo(Project)
        MemberRequirement.belongsTo(Requirement)

        const memberRequirements = await MemberRequirement.findAll({
            where: {
                memberId: req.user.id,
                estimateStartDate: { [Op.gte]: new Date(req.query.date) },
                estimateEndDate: { [Op.gte]: new Date(req.query.date) }
            }
        })

        res.json({
            workedHours: memberRequirements.map((obj) => obj.realHours || 0).reduce((a, b) => a + b),
            requirements: await Promise.all(
            
                memberRequirements.map(async (obj) => {
    
                    const requirement = await obj.getRequirement()
                    const project = await requirement.getProject()
        
                    return {
                        id: obj.id,
                        projectName: project.name,
                        requirementDescription: requirement.name,
                        estimateHours: obj.estimateHours,
                        realHours: obj.realHours || 0
                    }
                })
            )
        })

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

routes.get('/:id', auth, async (req, res) => {

    try {

        Project.belongsTo(Client)
        Project.belongsTo(Member, { as: 'Manager' })
        Requirement.belongsTo(Project)
        MemberRequirement.belongsTo(Requirement)

        const memberRequirement = await MemberRequirement.findByPk(req.params.id)
        const requirement = await memberRequirement.getRequirement()
        const project = await requirement.getProject()
        const client = await project.getClient()
        const manager = await project.getManager()

        res.json({
            id: memberRequirement.id,
            clientName: client.name,
            projectName: project.name,
            requirementDescription: requirement.name,
            projectManager: manager.firstName + ' ' + manager.lastName,
            estimateStartDate: memberRequirement.estimateStartDate,
            estimateEndDate: memberRequirement.estimateEndDate,
            estimateHours: memberRequirement.estimateHours,
            realHours: memberRequirement.realHours || 0,
            comment: memberRequirement.comment || '',
        })

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

routes.post('/', auth, async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['requirementId', 'date', 'hours'])

        if (!result) throw devMessage

        if (await MemberRequirement.findOne({ where: { memberId: req.user.id, requirementId: req.body.requirementId } }) != null) {
            throw 'Esta tarea ya ha sido asignada al usuario.'
        }

        const member_requirement = await MemberRequirement.create({
            memberId: req.user.id,
            requirementId: req.body.requirementId,
            estimateStartDate: req.body.date,
            estimateEndDate: req.body.date,
            estimateHours: req.body.hours,
            realHours: req.body.hours,
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

        res.json()

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

routes.put('/:id', auth, async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['hours'])

        if (!result) { throw devMessage }

        const member_requirement = await MemberRequirement.findByPk(req.params.id)

        if (member_requirement == null) { throw 'No se encontro la tarea asignada al usuario.' }

        member_requirement.realHours = req.body.hours
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

routes.delete('/:id', auth, async (req, res) => {

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