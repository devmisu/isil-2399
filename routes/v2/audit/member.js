const express = require('express')
const routes = express.Router()
const MemberLog = require('../../../models').member_log
const Sequelize = require('sequelize')

// All logs
routes.get('/', async (req, res) => {

    try {

        const members = await MemberLog.findAll({ paranoid: false })
        res.json(members)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All changes for member
routes.get('/changes/:id', async (req, res) => {

    try {

        const members = await MemberLog.findAll({ where: { memberId: req.params.id }, paranoid: false })

        if (members == null) { throw 'No hay registros de este miembro.' }

        res.json(members)

    } catch(error) {

        res.status(400).json({ message: error })
    }
})

// All deleted members
routes.get('/deleted', async (req, res) => {

    try {

        const members = await MemberLog.findAll({ where: {
            deletedAt: {
                [Sequelize.Op.not]: null
            } 
        }, paranoid: false })

        if (members == null) { throw 'No hay registros de este miembro.'}

        res.json(members)

    } catch(error) {

        console.log(error)

        res.status(400).json({ message: error })
    }
})

module.exports = routes