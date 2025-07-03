package com.example.imasjidhub.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import androidx.core.net.toUri
import com.google.firebase.auth.EmailAuthProvider

class FirebaseAuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message ?: "Login gagal")
                }
            }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid ?: return@addOnCompleteListener

                    val userMap = hashMapOf(
                        "fullName" to name,
                        "email" to email,
                        "phone" to "",
                        "address" to "",
                        "gender" to "",
                        "photoUrl" to ""
                    )

                    firestore.collection("users")
                        .document(uid)
                        .set(userMap)
                        .addOnSuccessListener {
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                            user.updateProfile(profileUpdates)
                            onResult(true, null)
                        }
                        .addOnFailureListener {
                            onResult(false, it.message)
                        }

                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun signInWithGoogle(idToken: String, onResult: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid ?: return@addOnCompleteListener

                    val userMap = hashMapOf(
                        "fullName" to (user.displayName ?: ""),
                        "email" to (user.email ?: ""),
                        "phone" to "",
                        "address" to "",
                        "gender" to "",
                        "photoUrl" to (user.photoUrl?.toString() ?: "")
                    )

                    firestore.collection("users").document(uid).get()
                        .addOnSuccessListener { document ->
                            if (!document.exists()) {
                                firestore.collection("users").document(uid).set(userMap)
                            }
                        }

                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun updateUserProfile(
        fullName: String,
        phone: String,
        address: String,
        gender: String,
        photoUrl: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        val user = auth.currentUser

        if (uid.isNullOrEmpty() || user == null) {
            onResult(false, "User tidak ditemukan atau belum login")
            return
        }

        val updates = hashMapOf<String, Any?>(
            "fullName" to fullName,
            "phone" to phone,
            "address" to address,
            "gender" to gender,
            "photoUrl" to photoUrl
        )

        firestore.collection("users")
            .document(uid)
            .update(updates)
            .addOnSuccessListener {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .setPhotoUri(photoUrl.toUri())
                    .build()

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onResult(true, null)
                        } else {
                            onResult(false, task.exception?.message ?: "Gagal update profil Firebase Auth")
                        }
                    }
            }
            .addOnFailureListener { e ->
                onResult(false, e.message ?: "Gagal update data Firestore")
            }
    }

    fun changePassword(
        oldPassword: String,
        newPassword: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val user = auth.currentUser
        val email = user?.email

        if (user == null || email.isNullOrEmpty()) {
            onResult(false, "User tidak ditemukan.")
            return
        }

        val credential = EmailAuthProvider.getCredential(email, oldPassword)
        user.reauthenticate(credential)
            .addOnSuccessListener {
                user.updatePassword(newPassword)
                    .addOnSuccessListener {
                        onResult(true, null)
                    }
                    .addOnFailureListener { e ->
                        onResult(false, e.message)
                    }
            }
            .addOnFailureListener { e ->
                onResult(false, "Password lama salah atau sesi kadaluarsa.")
            }
    }

    fun signOut(onResult: () -> Unit = {}) {
        FirebaseAuth.getInstance().signOut()
        onResult()
    }
}