package com.candlefinance.cache

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.Promise
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
class CacheModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  override fun getName(): String = "KitCacheManager"

  private val db = androidx.room.Room.databaseBuilder(
    reactApplicationContext,
    CacheDatabase::class.java, "cache"
  ).build()

  @ReactMethod
  fun read(key: String, promise: Promise) {
    val payload = db.stringPayloadDao().get(key)
    if (payload != null) {
      promise.resolve(payload.value)
    } else {
      promise.reject("Error the value was not found: $key")
    }
  }

  @ReactMethod
  fun write(key: String, value: String, promise: Promise) {
    db.stringPayloadDao().set(Payload(key, value))
    promise.resolve(true)
  }

  @ReactMethod
  fun delete(key: String, promise: Promise) {
    val payload = db.stringPayloadDao().get(key)
    if (payload != null) {
      db.stringPayloadDao().delete(payload)
      promise.resolve(true)
    } else {
      promise.reject("Error the value was not found: $key")
    }
  }

  @ReactMethod
  fun clear(promise: Promise) {
    db.clearAllTables()
    promise.resolve(true)
  }

}
