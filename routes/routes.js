const express = require('express')
const routes = express.Router()

routes.get('/', (req, res) => {
    res.send('v1')
})

// CRUD
const CRUD = ['area', 'client', 'job', 'member_requirement', 'member', 'project', 'requirement']

CRUD.forEach(route => {
    routes.use('/' + route, require('./crud/' + route))
});

// APP
const APP = ['user']

APP.forEach(route => {
    routes.use('/' + route, require('./app/' + route))
});

module.exports = routes