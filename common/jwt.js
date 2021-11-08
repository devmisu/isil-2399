require('dotenv').config()
const jwt = require('jsonwebtoken')

module.exports = {

    generate (userId, id) {
        return jwt.sign(
            { user_id: userId, id },
            process.env.JWT_SECRET_KEY,
            { expiresIn: '15m' }
        )
    },

    verify (token) {
        try {
            return jwt.verify(token, process.env.JWT_SECRET_KEY)
        } catch {
            throw 'Lo sentimos, su sesion a expirado.'
        }
    }
}