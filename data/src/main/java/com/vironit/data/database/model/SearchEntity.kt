package com.vironit.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
class SearchEntity(
    @ColumnInfo(name = "query") val query: String?,
    @ColumnInfo(name = "total") val total: String?,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "isFavourite") val isFavourite: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}