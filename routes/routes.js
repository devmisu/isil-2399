const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('v1')
})

// CRUD
const CRUD = ['area', 'client', 'job', 'member_requirement', 'member', 'project', 'requirement']

CRUD.forEach(route => {
    routes.use('/crud/' + route, require('./crud/' + route))
});

// APP
const APP = ['user', 'list', 'requirements']

APP.forEach(route => {
    routes.use('/app/' + route, require('./app/' + route))
});

module.exports = routes