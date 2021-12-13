const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('audit')
})

routes.use('/area', require('./area'))
routes.use('/client', require('./client'))
routes.use('/job', require('./job'))
routes.use('/requirement', require('./requirement'))
routes.use('/member_requirement', require('./member_requirement'))
routes.use('/project', require('./project'))
routes.use('/member', require('./member'))

module.exports = routes