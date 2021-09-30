require('dotenv').config()
const express = require('express')
const mysql = require('mysql')
const myconn = require('express-myconnection')

const app = express()

const dbOptions = {
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE
}

// Middlewares
app.use(myconn(mysql, dbOptions, 'single'))
app.use(express.json())

// Routes
app.get('/', (req, res) => {
    res.send('Solera Jobs!')
})

app.use('/api', require('./routes/routes'))

// Server
app.listen(process.env.SERVER_PORT, () => {
    console.log('server running on port', process.env.SERVER_PORT)
})