package com.severianfw.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.severianfw.githubuser.database.FavoriteUser
import com.severianfw.githubuser.database.FavoriteUserDao
import com.severianfw.githubuser.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavUserDao = db.favUserDao()
    }

    fun getAllFavUser(): LiveData<List<FavoriteUser>> = mFavUserDao.getAllFavUser()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavUserDao.delete(favoriteUser) }
    }

}