package com.candlefinance.cache

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

@Entity
data class Payload(
  @PrimaryKey @ColumnInfo(name = "id") val id: String,
  @ColumnInfo(name = "value") val value: String
)

@Dao
interface StringPayloadDao {
  @androidx.room.Query("SELECT * FROM Payload WHERE id = :id")
  fun get(id: String): Payload?

  @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
  fun set(payload: Payload)

  @androidx.room.Delete
  fun delete(payload: Payload)
}

@Database(entities = [Payload::class], version = 1)
abstract class CacheDatabase : androidx.room.RoomDatabase() {
  abstract fun stringPayloadDao(): StringPayloadDao
}

@Suppress("unused")
class CacheManager(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  override fun getName(): String = "KitCacheManager"


  private val db = androidx.room.Room.databaseBuilder(
    reactApplicationContext,
    CacheDatabase::class.java, "cache"
  ).build()

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun read(key: String, callback: Callback) {
    val payload = db.stringPayloadDao().get(key)
    if (payload != null) {
      callback(payload.value)
    } else {
      callback(null)
    }
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun write(key: String, value: String, callback: Callback) {
    db.stringPayloadDao().set(Payload(key, value))
    callback(true)
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun delete(key: String, callback: Callback) {
    val payload = db.stringPayloadDao().get(key)
    if (payload != null) {
      db.stringPayloadDao().delete(payload)
      callback(true)
    } else {
      callback(false)
    }
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  fun clear(callback: Callback) {
    db.clearAllTables()
    callback(true)
  }

}
