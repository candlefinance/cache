package com.candlefinance.cache

import kotlinx.coroutines.*
import java.io.Closeable
import java.io.File
import java.io.Flushable


@Suppress("unused")
open class AndroidDiskCache(private val diskCache: DiskCache) : Closeable, Flushable {

    object Builder {
        private var folder: File? = null
        private var appVersion: Int = 1
        private var dispatcher: CoroutineDispatcher = Dispatchers.IO
        private var maxSize: Long = 524288
        private var cleanupPercentage: Double = 0.9

        fun folder(folder: File) = apply { this.folder = folder }

        fun appVersion(version: Int) = apply { this.appVersion = version }

        fun dispatcher(dispatcher: CoroutineDispatcher) = apply { this.dispatcher = dispatcher }

        fun maxSize(maxSize: Long) = apply { this.maxSize = maxSize }

        fun cleanupPercentage(cleanupPercentage: Double) { this.cleanupPercentage = cleanupPercentage }

        fun build() = folder?.let { cacheFolder ->
                AndroidDiskCache(
                    DiskCache(
                        folder = cacheFolder,
                        maxSize = maxSize,
                        appVersion = appVersion,
                        cleanupPercentage = cleanupPercentage,
                        cleanupDispatcher = dispatcher))
            } ?: throw IllegalStateException("you must provide a folder to initialize KDiskCache")
    }

    fun putToFile(key: String, writeToFile: (file: File) -> Unit) {
        diskCache.edit(key)?.let { editor ->
            writeToFile(editor.file())
            editor.commit()
        }
    }

    fun <T> getFromFile(key: String, readFromFile: (file: File) -> T) : T? {
        diskCache.get(key)?.let { snapshot ->
            val result : T? = readFromFile(snapshot.file())
            snapshot.close()
            return result
        } ?: run {
            return null
        }
    }

    fun putAsBytes(key: String, data: () -> ByteArray) {
        diskCache.edit(key)?.let { editor ->
            try {
                editor.file().bufferedOutputStream().use {
                    it.write(data())
                }
            } catch (_: Exception) {
                editor.abort()
            } finally {
                editor.commit()
            }
        }
    }

    fun putBytes(key: String, bytes: ByteArray) {
        diskCache.edit(key)?.let { editor ->
            try {
                editor.file().bufferedOutputStream().use {
                    it.write(bytes)
                }
            } catch (_: Exception) {
                editor.abort()
            } finally {
                editor.commit()
            }
        }
    }

    fun getBytes(key: String) : ByteArray? {
        return diskCache.get(key)?.let { snapshot ->
            try {
                snapshot.file().bufferedInputStream().use {
                    it.readBytes()
                }
            } catch (e: Exception) {
                null
            } finally {
                snapshot.close()
            }
        }
    }

    fun <T> getFromBytes(key: String, decode: (bytes: ByteArray?) -> T) : T? {
        return diskCache.get(key)?.let { snapshot ->
            val bytes = try {
                snapshot.file().bufferedInputStream().use {
                    it.readBytes()
                }
            } catch (e: Exception) {
                null
            } finally {
                snapshot.close()
            }
            decode(bytes)
        }
    }

    fun contains(key: String) : Boolean {
        return diskCache.get(key) != null
    }

    fun remove(key: String) : Boolean {
        return diskCache.remove(key)
    }

    fun clear() {
        diskCache.evictAll()
    }

    fun size() : Long {
        return diskCache.size()
    }

    override fun close() {
        diskCache.close()
    }

    override fun flush() {
        diskCache.flush()
    }
}
