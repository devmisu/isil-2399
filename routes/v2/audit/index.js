const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('audit')
})

routes.use('/area', require('./area'))
routes.use('/client', require('./client'))
routes.use('/job', require('./job'))
routes.use('/member_requirement', require('./member_requirement'))

module.exports = routes