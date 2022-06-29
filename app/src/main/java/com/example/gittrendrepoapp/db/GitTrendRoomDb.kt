package com.example.gittrendrepoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gittrendrepoapp.model.GitRepoListResponse

@Database(entities = [GitRepoListResponse::class], version = 1, exportSchema = false)
abstract class GitTrendRoomDb : RoomDatabase() {

    abstract fun gitRepoListDao(): GitRepoListDao

    companion object {
        @Volatile
        private var instance: GitTrendRoomDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

            instance ?: createDatabase(context).also { instance = it }

        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GitTrendRoomDb::class.java,
            "git_repo_list.db"
        ).build()


    }


}