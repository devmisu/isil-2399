const jwt = require('../common/jwt')
const validator = require('../common/validator')

module.exports = (req, res, next) => {

    try {

        const { result, devMessage } = validator.valid(req.headers, ['authorization'])

        if (!result) throw devMessage
    
        const authorization = req.headers['authorization'].split(' ')
        const token = authorization[1]
    
        req.user = jwt.verify(token)
    
        return next()   

    } catch(error) {

        return res.status(401).json({ message: error })
    }
}