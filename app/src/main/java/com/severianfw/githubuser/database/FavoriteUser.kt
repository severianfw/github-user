package com.severianfw.githubuser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String,

    @ColumnInfo(name = "htmlUrl")
    var htmlUrl: String,

    @ColumnInfo(name = "username")
    var login: String,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "repository")
    var repository: Int,

    @ColumnInfo(name = "following")
    var following: Int,

    @ColumnInfo(name = "followers")
    var followers: Int,
) : Parcelable