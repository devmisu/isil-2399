const express = require('express')
const routes = express.Router()
const MemberReqLog = require('../../../models').member_requirement_log
const Sequelize = require('sequelize')

// All logs
routes.get('/', async (req, res) => {

    try {

        const reqs = await MemberReqLog.findAll({ paranoid: false })
        res.json(reqs)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All changes for member requirement
routes.get('/changes/:memberRequirementId', async (req, res) => {

    try {

        const reqs = await MemberReqLog.findAll({ where: { memberRequirementId: req.params.memberRequirementId }, paranoid: false })

        if (reqs == null) { throw 'No hay registros de este requerimiento.' }

        res.json(reqs)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All deleted areas
routes.get('/deleted', async (req, res) => {

    try {

        const reqs = await MemberReqLog.findAll({ where: {
            deletedAt: {
                [Sequelize.Op.not]: null
            } 
        }, paranoid: false })

        if (reqs == null) { throw 'No hay registros de este requerimiento.'}

        res.json(reqs)

    } catch(error) {

        console.log(error)

        res.status(400).json({ message: error })
    }
})

module.exports = routes