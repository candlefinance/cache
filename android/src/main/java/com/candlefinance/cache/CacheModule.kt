  package com.candlefinance.cache

  import android.util.Log
  import com.facebook.react.bridge.Promise
  import com.facebook.react.bridge.ReactApplicationContext
  import com.facebook.react.bridge.ReactContextBaseJavaModule
  import com.facebook.react.bridge.ReactMethod
  import kotlinx.coroutines.CoroutineScope
  import kotlinx.coroutines.Dispatchers
  import kotlinx.coroutines.launch
  import kotlinx.coroutines.withContext

  @Suppress("unused")
  class CacheModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String = "KitCacheManager"

    private lateinit var db: AndroidDiskCache
    private suspend fun initializeKache() = withContext(Dispatchers.IO) {
      val cacheDir = reactApplicationContext.cacheDir
      val packageName = reactApplicationContext.packageName
      db = AndroidDiskCache.Builder
        .folder(cacheDir)
        .appVersion(1)
        .maxSize(1024 * 1024 * 200) // 200MB
        .dispatcher(Dispatchers.IO)
        .build()
    }

    init {
      Log.d("CacheModule", "Initializing CacheModule")
      CoroutineScope(Dispatchers.IO).launch {
        initializeKache()
      }
    }

    @ReactMethod
    fun read(key: String, promise: Promise) {
        try {
          val result = db.getFromFile(key) { file ->
            val value = file.readText()
            value
          }
          promise.resolve(result)
        } catch (e: Exception) {
          promise.reject("READ_FAILED", e)
        }
      }

    @ReactMethod
    fun write(key: String, value: String, promise: Promise) {
        try {
          db.putToFile(key) { file ->
            file.writeText(value)
            promise.resolve(true)
          }
        } catch (e: Exception) {
          promise.reject("WRITE_FAILED", e)
        }
      }

    @ReactMethod
    fun delete(key: String, promise: Promise) {
        try {
          val result = db.remove(key)
          promise.resolve(result)
        } catch (e: Exception) {
          Log.e("DELETE_FAILED", "Failed to delete from cache", e)
          promise.reject("DELETE_FAILED", e)
        }
      }

    @ReactMethod
    fun clear(promise: Promise) {
        try {
          db.clear()
          promise.resolve(true)
        } catch (e: Exception) {
          Log.e("CLEAR_FAILED", "Failed to clear cache", e)
          promise.reject("CLEAR_FAILED", e)
        }
      }

  }
