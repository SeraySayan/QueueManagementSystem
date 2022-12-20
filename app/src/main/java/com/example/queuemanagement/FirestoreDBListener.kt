package com.example.queuemanagement

import android.app.Activity
import com.google.firebase.firestore.util.Listener

/**
 * This interface is on progress
 * I'm trying to create my own Listeners
 * because Firestore listeners are in
 * the "FirestoreDB" class.
 * So, this is just an experiment LOL
 * */


interface FirestoreDBListener : Listener<FirestoreDB> {

    // I rage quit at some-point and delete codes. Yes it was a dumb move :/
    // But hey, I figured out that issue without an extra Listener. Horaay :D


}