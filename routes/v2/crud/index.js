const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('crud')
})

routes.use('/client', require('./client'))
routes.use('/area', require('./area'))
routes.use('/job', require('./job'))
routes.use('/member', require('./member'))
routes.use('/project', require('./project'))
routes.use('/requirement', require('./requirement'))
routes.use('/member_requirement', require('./member_requirement'))

module.exports = routes