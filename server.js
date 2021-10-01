require('dotenv').config()
const express = require('express')
const mysql = require('mysql')
const myconn = require('express-myconnection')
const winston = require('winston')
const expressWinston = require('express-winston')

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

// Logger
app.use(expressWinston.logger({
    transports: [
        new winston.transports.Console()
    ],
    format: winston.format.combine(
        winston.format.colorize(),
        winston.format.json()
    ),
    requestWhitelist: ['body'],
    responseWhitelist: ['body']
}))

// Routes
app.get('/', (req, res) => {
    res.send('Solera Jobs!')
})

app.use('/api', require('./routes/routes'))

// Error logger
if (process.env.NODE_ENV == 'development') {
    
    app.use(expressWinston.errorLogger({
        transports: [
            new winston.transports.Console()
        ],
        format: winston.format.combine(
            winston.format.colorize(),
            winston.format.json()
        )
    }))
}

// Server
app.listen(process.env.SERVER_PORT, () => {
    console.log('server running on port', process.env.SERVER_PORT)
})