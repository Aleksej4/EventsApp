package eif.viko.lt.faculty.app.data

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    suspend fun insertEvent(event: Event, imageUri: Uri?)

    suspend fun insertSavedEvent(eventId: String, userId: String)

    suspend fun deleteSavedEvent(eventId: String, userId: String)

    suspend fun getSavedEvent(eventId: String, userId: String): Boolean

    suspend fun insertUser(id: String, firstName: String, lastName: String)

    suspend fun deleteEvent(event: Event)

    suspend fun getEventById(id: String): Event?

    fun getEvents(): Flow<List<Event>>

    fun getSavedEvents(id: String): Flow<List<Event>>
}