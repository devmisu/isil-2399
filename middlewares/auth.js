require('dotenv').config()
const jwt = require('jsonwebtoken')
const util = require('../common/util')

const verifyToken = (req, res, next) => {

    const { result, devMessage } = util.sanitize(req.headers, ['authorization'])

    if (!result) {
        return res.status(403).json({ message: 'Ocurrio un error inesperado.', devMessage: devMessage })
    }

    const authorization = req.headers['authorization'].split(' ')
    const token = authorization[1]

    try {
        req.user = jwt.verify(token, process.env.JWT_SECRET_KEY)
    } catch (err) {
        return res.status(401).json({ message: 'Lo sentimos, su sesion a expirado.', devMessage: err })
    }

    return next()
}

module.exports = verifyToken