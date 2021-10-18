const { format } = require('date-fns')

const sanitize = function (params, keys) {

    let result = true

    keys.forEach(k => { if (params[k] == null || params[k] == "") result = false })

    return {
        result: result,
        devMessage: 'Debes enviar los siguientes campos: ' + keys.join(', ') + '.'
    }
}

const getConnection = async function (req) {
    return new Promise((resolve, reject) => {
        req.getConnection((err, conn) => {
            if (err) {
                reject(err)
            } else {
                resolve(conn)
            }
        })
    })
}

const execQuery = async function (conn, query, params) {
    return new Promise((resolve, reject) => {
        var exec = 'CALL ' + query
        if (params) exec += '(?)'
        conn.query( exec, [params], (err, rows) => {
            if (err) {
                reject(err)
            } else {
                resolve(rows[0])
            }
        })
    })
}

module.exports = { sanitize, getConnection, execQuery }