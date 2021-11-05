const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('app')
})

routes.use('/user', require('./user'))

module.exports = routes