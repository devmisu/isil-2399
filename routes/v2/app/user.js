const express = require('express')
const routes = express.Router()
const validator = require('../../../common/validator')
const oauth = require('../../../common/oauth')
const jwt = require('../../../common/jwt')
const Member = require('../../../models').member
const Job = require('../../../models').job
const Area = require('../../../models').area
const Fcm = require('../../../models').fcm
const auth = require('../../../middlewares/auth')

Job.belongsTo(Area)
Member.belongsTo(Job)

// Login
routes.post('/login', async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['idToken'])

        if (!result) throw devMessage

        const payload = await oauth.verify(req.body['idToken'])

        const userId = payload['sub']
        const domain = payload['hd']
        const email = payload['email']

        if (domain != 'solera.pe') throw 'El email ingresado no pertenece a Solera.'

        const user = await Member.findOne({ where: { email: email } })

        if (user == null) throw 'Tu cuenta no esta activa.'

        const job = await user.getJob()
        const area = await job.getArea()

        const token = jwt.generate(userId, user.id)

        res.json({
            session: {
                type: 'Bearer',
                token: token },
            user: {
                firstName: user.firstName,
                lastName: user.lastName,
                email: user.email,
                phone: user.phone,
                job: job.name,
                area: area.name
            }
        })

    } catch(error) {
        
        res.status(400).json({ message: error })
    }
})

// Register FCM Token
routes.post('/fcm', auth, async (req, res) => {

    try {

        const { result, devMessage } = validator.valid(req.body, ['fcmToken'])

        if (!result) throw devMessage

        const fcm = await Fcm.findOne({ where: { memberId: req.user.id } })

        if (fcm == null) {

            await Fcm.create({ memberId: req.user.id, fcmToken: req.body.fcmToken })

        } else {
            
            fcm.fcmToken = req.body.fcmToken
            await fcm.save()
        }

        res.json()

    } catch(error) {

        console.log(error)
        res.status(400).json({ message: error })
    }
})

module.exports = routes