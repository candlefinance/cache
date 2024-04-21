package com.candlefinance.cache

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

fun File.appendOutputStream() = FileOutputStream(this, true).writer(StandardCharsets.UTF_8)

fun File.bufferedOutputStream() = FileOutputStream(this).buffered()

fun File.bufferedInputStream() = FileInputStream(this).buffered()
