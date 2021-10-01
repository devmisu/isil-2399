const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('v1')
})

// routes.use('/client', require('./client'))
routes.use('/area', require('./area'))
routes.use('/job', require('./job'))
routes.use('/member', require('./member'))
routes.use('/auth', require('./auth'))

module.exports = routes