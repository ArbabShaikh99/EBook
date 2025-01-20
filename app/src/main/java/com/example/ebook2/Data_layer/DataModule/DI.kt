package com.example.ebook2.Data_layer.DataModule

import android.content.Context
import androidx.room.Room
import com.example.ebook2.Data_layer.Repo.Repo
import com.example.ebook2.localDataBase.dao.EBookDao
import com.example.ebook2.localDataBase.dataBase.AppDatabase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DI {

    @Provides
    @Singleton
    fun providefirebaseRealTimeDataBase():FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun ProvideRepo(firebaseDatabase: FirebaseDatabase):Repo{
        return Repo(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context):Context{
        return context
    }

    @Provides
    @Singleton
    fun ProvideRoomDataBase(appContext : Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
          AppDatabase::class.java,
            "EBookDataBase"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(database: AppDatabase) : EBookDao {
        return database.bookDao()
    }

}