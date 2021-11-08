const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('api')
})

routes.use('/v2', require('./v2'))

module.exports = routes