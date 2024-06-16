import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";
import { getStorage } from "firebase/storage";

const firebaseConfig = {
    apiKey: "AIzaSyA8hr1oOEwu4w4bMQwRTiQjNNwHJzZktkY",
    authDomain: "soundrevive-91d60.firebaseapp.com",
    projectId: "soundrevive-91d60",
    storageBucket: "soundrevive-91d60.appspot.com",
    messagingSenderId: "341252652672",
    appId: "1:341252652672:web:9f3c574e1ca02a59f97065"
};

export const app = initializeApp(firebaseConfig);
export const firestore = getFirestore(app);
export const storage = getStorage(app);
