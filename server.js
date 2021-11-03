require('dotenv').config()
const express = require('express')
const winston = require('winston')
const expressWinston = require('express-winston')

const app = express()

// Middlewares
app.use(express.json())

// Logger
app.use(expressWinston.logger({
    transports: [
        new winston.transports.Console()
    ],
    format: winston.format.simple(),
    requestWhitelist: ['headers', 'body'],
    responseWhitelist: ['body']
}))

// Routes
app.get('/', (req, res) => {
    res.send('Solera Jobs!')
})

// app.use('/api', require('./routes/routes'))

// Error logger
// if (process.env.NODE_ENV == 'development') {
//     app.use(expressWinston.errorLogger({
//         transports: [
//             new winston.transports.Console()
//         ],
//         format: winston.format.combine(
//             winston.format.colorize(),
//             winston.format.json()
//         )
//     }))
// }

// Server
app.listen(process.env.SERVER_PORT, () => {
    console.log('server running on port', process.env.SERVER_PORT)
})