const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const auth = require('../../../middlewares/auth')
const MemberRequirement = require('../../../models').member_requirement
const Requirement = require('../../../models').requirement
const Member = require('../../../models').member
const Project = require('../../../models').project
const Client = require('../../../models').client
const { Op } = require("sequelize");

Project.belongsTo(Client)
Project.belongsTo(Member, { as: 'manager' })
Requirement.belongsTo(Project)
MemberRequirement.belongsTo(Requirement)

routes.get('/', auth, async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.query, ['startDate', 'endDate'])

        if (!result) throw devMessage

        const tasks = await MemberRequirement.findAll({
            where: {
                memberId: req.user.id,
                estimateStartDate: { [Op.lte]: new Date(req.query.endDate) },
                estimateEndDate: { [Op.gte]: new Date(req.query.startDate) }
            },
            include: {
                model: Requirement,
                include: {
                    model: Project,
                    include: [Client, { model: Member, as: 'manager' }]
                }
            }
        })

        var responseData = []

        tasks.forEach(task => {

            const project = task.requirement.project
            const manager = project.manager
            const client = project.client
            const requirements = tasks.filter(e => e.requirement.project.id == project.id)
            const totalWorkedHours = requirements.map(e => e.realHours || 0).reduce((a, b) => a + b)
            const totalEstimateHours = requirements.map(e => e.estimateHours).reduce((a, b) => a + b)

            responseData.push({
                id: project.id,
                projectName: project.name,
                clientName: client.name,
                managerName: manager.firstName + ' ' + manager.lastName,
                totalEstimateHours: totalEstimateHours,
                totalWorkedHours: totalWorkedHours,
                requirements: requirements.map(requirement => {
                    return {
                        id: requirement.id,
                        description: requirement.requirement.name,
                        startDate: requirement.estimateStartDate,
                        endDate: requirement.estimateEndDate,
                        estimateHours: requirement.estimateHours,
                        realHours: requirement.realHours || 0,
                        comment: requirement.comment || ''
                    }
                })
            })
        })

        res.json(responseData)

    } catch(error) {
        
        res.status(400).json({ message: error })
    }
})

module.exports = routes