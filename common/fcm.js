const FirebaseAdmin = require('firebase-admin')
const serviceAccount = require('../solerajobs-firebase-adminsdk-w5ege-5d452ab812.json')
const Fcm = require('../models').fcm

module.exports = {
        async pushNotfToMember(memberId, title, body) {
                
                try {

                        const fcm = await Fcm.findOne({ where: { memberId: memberId } })

                        if (fcm == null) { throw 'No se encontro token FCM asociado al usuario.' }

                        let app = FirebaseAdmin.initializeApp({ credential: FirebaseAdmin.credential.cert(serviceAccount) });
                        
                        const message = {
                                data: {
                                        // TODO: Send data
                                },
                                notification: {
                                        title: title,
                                        body: body
                                },
                                token: fcm.fcmToken
                        }

                        let result = await app.messaging().send(message)

                        console.log(result)

                        return true

                } catch(error) {

                        throw 'No se pudo enviar notificacion push.'
                }
        }
}