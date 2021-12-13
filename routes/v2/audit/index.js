const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('audit')
})

routes.use('/area', require('./area'))

module.exports = routes