const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('v2')
})

routes.use('/app', require('./app'))
routes.use('/crud', require('./crud'))
routes.use('/audit', require('./audit'))

module.exports = routes