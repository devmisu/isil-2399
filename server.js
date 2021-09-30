const express = require('express')
const mysql = require('mysql')
const myconn = require('express-myconnection')

const app = express()
app.set('port', process.env.PORT || 80)

const dbOptions = {
    host: '127.0.0.1',
    port: 3306,
    user: 'root',
    password: '#4tKTNH2W*n%',
    database: 'solera_jobs'
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
app.listen(app.get('port'), () => {
    console.log('server running on port', app.get('port'))
})