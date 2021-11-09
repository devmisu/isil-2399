const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const auth = require('../../../middlewares/auth')
const MemberRequirement = require('../../../models').member_requirement
const { Op } = require("sequelize");

routes.get('/', auth, async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.query, ['startDate', 'endDate'])

        if (!result) throw devMessage

        const query = await MemberRequirement.findAll({
            where: {
                memberId: req.user.id,
                estimateStartDate: { [Op.lte]: new Date(req.query.endDate) },
                estimateEndDate: { [Op.gte]: new Date(req.query.startDate) }
            }
        })

        res.json(query)

    } catch(error) {
        
        res.status(400).json({ message: error })
    }
})

module.exports = routes