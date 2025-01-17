package com.example.authapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.authapp.db.models.User

@Database (entities = [User::class], version = 1)
abstract class UserDatabase:  RoomDatabase() {
    abstract fun getUserDao() : UserDao
//    companion object {
//        @Volatile
//        private var instance: UserDatabase? = null
//        private var LOCK = Any()
//        operator fun invoke(context: Context) = instance ?:
//        synchronized(LOCK) {
//            instance ?:
//            createDatabase(context).also{
//                instance = it
//            }
//        }
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                UserDatabase::class.java,
//                "user_db"
//            ).build()
//    }
}