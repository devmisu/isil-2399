const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('app')
})

routes.use('/user', require('./user'))
routes.use('/list', require('./list'))
routes.use('/requirement', require('./requirement'))

module.exports = routes