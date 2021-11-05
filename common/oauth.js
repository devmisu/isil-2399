require('dotenv').config()
const { OAuth2Client } = require('google-auth-library')

module.exports = {

    async verify (idToken) {

        try {

            const options = { idToken: idToken }
            const oauth = new OAuth2Client(process.env.OAUTH_CLIENT_ID)
            const ticket = await oauth.verifyIdToken(options)
            const payload = ticket.getPayload()

            return payload

        } catch(error) {

            throw error || 'Credenciales invalidas.'
        }
    }
}