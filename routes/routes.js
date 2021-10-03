const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('v1')
})

// CRUD
const API = [
    'area',
    'client',
    'job',
    'member_requirement',
    'member',
    'project',
    'requirement',
    'auth'
]

API.forEach(route => {
    routes.use('/' + route, require('./' + route))
});

module.exports = routes