const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('v1')
})

routes.use('/client', require('./client'))
routes.use('/auth', require('./auth'))

module.exports = routes