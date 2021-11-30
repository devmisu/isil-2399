const FirebaseAdmin = require('firebase-admin')
const serviceAccount = require('../../../solerajobs-firebase-adminsdk-w5ege-5d452ab812.json')

// let app = FirebaseAdmin.initializeApp({ credential: FirebaseAdmin.credential.cert(serviceAccount) });

        // // Obtener token de sesion
        // // Guardar fcm en tabla
        // const fcmToken = 'eMmUwb84QtSWV4smXkhDE8:APA91bFRihW9h9n5I3JXTWj7dpr-_VKS0ocm0U81NgCP4_gQ-HrfrMbjiqsbyeiKHTKIe_VpptzRqFSLLt-81H-LTnymc_ydhP90BLyugwDJh1rhVPtRP8V30EJvV_PNS_vUI5H33MVk'

        // const message = {
        //     data: {
        //         // TODO: Send data
        //     },
        //     notification: {
        //         title: 'Viejas calientes cerca a tu zona',
        //         body: 'Y DALE U'
        //     },
        //     token: fcmToken
        // }

        // app.messaging().send(message)
        // .then((response) => {
        //     // Response is a message ID string.
        //     console.log('Successfully sent message:', response);
        // })
        // .catch((error) => {
        //     console.log('Error sending message:', error);
        // });