package com.refrence.todoapplication.di

import android.app.Application
import androidx.room.Room
import com.refrence.todoapplication.data.TodoDao
import com.refrence.todoapplication.data.TodoDatabase
import com.refrence.todoapplication.data.TodoRepository
import com.refrence.todoapplication.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTodoDatabase(app : Application): TodoDatabase
    {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "Todo_db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideTodoRepository(db : TodoDatabase): TodoRepository{
         return TodoRepositoryImpl(db.dao)
    }
}