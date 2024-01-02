package eif.viko.lt.faculty.app.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eif.viko.lt.faculty.app.data.EventRepository
import eif.viko.lt.faculty.app.data.EventRepositoryImpl
import eif.viko.lt.faculty.app.data.categories.Categories
import eif.viko.lt.faculty.app.data.userManaging.UserDatabase
import eif.viko.lt.faculty.app.data.userManaging.UserManager
import eif.viko.lt.faculty.app.data.userManaging.UserRepository
import eif.viko.lt.faculty.app.data.userManaging.UserRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage{
        return FirebaseStorage.getInstance()

    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }


    @Provides
    @Singleton
    fun providesEventRepository(firebaseDatabase: FirebaseDatabase, firebaseStorage: FirebaseStorage): EventRepository{
        return EventRepositoryImpl(firebaseDatabase, firebaseStorage)
    }

    @Provides
    @Singleton
    fun providesUserManager(): UserManager{
        return UserManager()
    }

    @Provides
    @Singleton
    fun providesCategories(): Categories{
        return Categories()
    }

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDatabase{
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "user_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: UserDatabase, firebaseDatabase: FirebaseDatabase): UserRepository{
        return UserRepositoryImpl(db.dao, firebaseDatabase)
    }
}