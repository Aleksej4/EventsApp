package eif.viko.lt.faculty.app.data

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class EventRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage,
    private val eventsRef: DatabaseReference = firebaseDatabase.getReference("events"),
    private val userRef: DatabaseReference = firebaseDatabase.getReference("users")
): EventRepository {

    override suspend fun insertEvent(event: Event, imageUri: Uri?) {
        if (imageUri != null){
            val storageRef = firebaseStorage.reference.child("${imageUri.lastPathSegment}")

            storageRef.putFile(imageUri)
                .addOnSuccessListener { _ ->
                    storageRef.downloadUrl
                        .addOnSuccessListener { downloadUri ->
                        val imageUrl = downloadUri.toString()
                        event.image = imageUrl

                        val eventId = eventsRef.push().key
                        eventId?.let {
                            val eventRef = eventsRef.child(eventId)
                            eventRef.setValue(event)
                                .addOnFailureListener { exception ->
                                    Log.e("Failure uploading event: ", exception.toString())
                                }
                        }
                    }
                        .addOnFailureListener { exception ->
                        Log.e("Failure downloading url: ", exception.toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Failure uploading image: ", exception.toString())
                }
        }

    }

    override suspend fun insertSavedEvent(eventId: String, userId: String) {
        val userDataRef = userRef.child(userId)
        val savedEventRef = userDataRef.child("savedEvents")

        savedEventRef.push().setValue(eventId)
            .addOnFailureListener { exception ->
                Log.e("Failure saving event: ", exception.toString())
            }
    }

    override suspend fun deleteSavedEvent(eventId: String, userId: String) {
        val userDataRef = userRef.child(userId)
        val savedEventsRef = userDataRef.child("savedEvents")

        savedEventsRef.orderByValue().equalTo(eventId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        snapshot.ref.removeValue(object : DatabaseReference.CompletionListener {
                            override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                                if (error != null) {
                                    Log.e("Error:", "Failed to delete event from savedEvents: $error")
                                }
                            }
                        })
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Error: ", databaseError.toString())
                }
            }
        )
    }

    override suspend fun getSavedEvent(eventId: String, userId: String): Boolean {
        val userDataRef = userRef.child(userId)
        val savedEventsRef = userDataRef.child("savedEvents")

        return suspendCoroutine { continuation ->
            savedEventsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var eventExists = false
                    for (eventSnapshot in snapshot.children) {
                        val event = eventSnapshot.getValue(String::class.java)
                        if (event == eventId) {
                            eventExists = true
                            break
                        }
                    }
                    continuation.resume(eventExists)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Error: ", error.toString())
                    continuation.resume(false)
                }
            })
        }
    }

    override suspend fun insertUser(id: String, firstName: String, lastName: String) {
        val userDataRef = userRef.child(id)
        val userData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName
        )
        userDataRef.setValue(userData)
            .addOnFailureListener { exception ->
                Log.e("Error uploading user data: ", exception.toString())
            }
    }

    override suspend fun deleteEvent(event: Event) {
        if (event.image != null){
            val storageRef = firebaseStorage.getReferenceFromUrl(event.image!!)
            storageRef.delete()
                .addOnSuccessListener {
                    if (event.id != null){
                        val eventDataRef = eventsRef.child(event.id!!)
                        eventDataRef.removeValue()
                            .addOnFailureListener { exception ->
                                Log.e("Failure deleting event: ", exception.toString())
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Failure deleting image: ", exception.toString())
                }
        }
    }

    override suspend fun getEventById(id: String): Event? = suspendCoroutine { continuation ->
        eventsRef.child(id).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val eventSnapshot: DataSnapshot? = task.result

                val title = eventSnapshot?.child("title")?.getValue(String::class.java) ?: ""
                val description = eventSnapshot?.child("description")?.getValue(String::class.java) ?: ""
                val category = eventSnapshot?.child("category")?.getValue(String::class.java) ?: ""
                val date = eventSnapshot?.child("date")?.getValue(String::class.java) ?: ""
                val image = eventSnapshot?.child("image")?.getValue(String::class.java) ?: ""
                val userId = eventSnapshot?.child("userId")?.getValue(String::class.java) ?: ""
                val eventId = eventSnapshot?.key

                val event = Event(title, description, category, date, image, userId, eventId)
                continuation.resume(event)
            } else {
                continuation.resume(null)
            }
        }
    }

    override fun getEvents(): Flow<List<Event>> = callbackFlow {
        val eventListener = eventsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventsList = mutableListOf<Event>()

                for (eventSnapshot in snapshot.children) {
                    try {
                        val title = eventSnapshot.child("title").getValue(String::class.java) ?: ""
                        val description = eventSnapshot.child("description").getValue(String::class.java) ?: ""
                        val category = eventSnapshot.child("category").getValue(String::class.java) ?: ""
                        val date = eventSnapshot.child("date").getValue(String::class.java) ?: ""
                        val image = eventSnapshot.child("image").getValue(String::class.java) ?: ""
                        val userId = eventSnapshot.child("userId").getValue(String::class.java) ?: ""
                        val id = eventSnapshot.key

                        val event = Event(title, description, category, date, image, userId, id)
                        eventsList.add(event)
                    } catch (e: Exception) {
                        Log.e("Error database: ", e.toString())
                    }
                }

                trySend(eventsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error: ", error.toString())
            }
        })

        awaitClose { eventsRef.removeEventListener(eventListener) }
    }

    override fun getSavedEvents(id: String): Flow<List<Event>> = callbackFlow {
        val savedEventRef = userRef.child(id).child("savedEvents")

        val eventListener = savedEventRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventIds: MutableList<String> = mutableListOf()

                for (eventSnapshot in snapshot.children) {
                    val eventId = eventSnapshot.getValue(String::class.java)
                    eventId?.let { eventIds.add(it) }
                }

                eventsRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(eventSnapshot: DataSnapshot) {
                        val eventsList = mutableListOf<Event>()

                        for (snapshot in eventSnapshot.children) {
                            val eventId = snapshot.key ?: ""
                            if (eventId in eventIds) {
                                val title = snapshot.child("title").getValue(String::class.java) ?: ""
                                val description = snapshot.child("description").getValue(String::class.java) ?: ""
                                val category = snapshot.child("category").getValue(String::class.java) ?: ""
                                val date = snapshot.child("date").getValue(String::class.java) ?: ""
                                val image = snapshot.child("image").getValue(String::class.java) ?: ""
                                val userId = snapshot.child("userId").getValue(String::class.java) ?: ""

                                val event = Event(title, description, category, date, image, userId, eventId)
                                eventsList.add(event)
                            }
                        }

                        trySend(eventsList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Error: ", error.toString())
                    }
                })
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Error: ", error.toString())
            }
        })

        awaitClose { savedEventRef.removeEventListener(eventListener) }
    }
}
