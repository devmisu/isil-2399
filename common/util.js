const { format } = require('date-fns')

const sanitize = function (params, keys) {

    let result = true

    keys.forEach(k => { if (params[k] == null || params[k] == "") result = false })

    return {
        result: result,
        devMessage: 'Debes enviar los siguientes campos: ' + keys.join(', ') + '.'
    }
}

const currentDate = function () {
    return format(new Date(), 'yyyy-MM-dd HH:mm:ss')
}

module.exports = { sanitize, currentDate }