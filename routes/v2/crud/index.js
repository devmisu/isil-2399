const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('crud')
})

routes.use('/client', require('./client'))

module.exports = routes