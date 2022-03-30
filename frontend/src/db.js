import firebase from "firebase/app";
import "firebase/database";

const config = {
    apiKey: 'AIzaSyChofLeXXMEbV3TYaqy8E1kMBeTBU07LVA',
  authDomain: 'fir-2992b.firebaseapp.com',
  projectId: 'fir-2992b',
  storageBucket: 'fir-2992b.appspot.com',
  messagingSenderId: '152328774798',
  appId: '1:152328774798:web:46688332cffad0251555df',
  measurementId: 'G-NY5K4MXRKV'
}

const db = firebase.initializeApp(config);
export default db;
