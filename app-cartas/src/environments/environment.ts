
// variables de configuracion usadas en el ambiente de desarrollo

// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  urlBase: 'http://localhost:8080',
  //urlService: 'http://localhost:8080',
  firebaseConfig : {
    apiKey: "AIzaSyCkKFQ-xugi92fHpMVFsg38W2cpsYwEFKg",
    authDomain: "appcards-4ebc1.firebaseapp.com",
    projectId: "appcards-4ebc1",
    storageBucket: "appcards-4ebc1.appspot.com",
    messagingSenderId: "959444708446",
    appId: "1:959444708446:web:a1e97778c70ecb73dc18cf",
    measurementId: "G-3MXPV5CKME"
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
