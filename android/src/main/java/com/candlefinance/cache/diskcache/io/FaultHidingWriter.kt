package com.candlefinance.cache

import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.io.Writer

class FaultHidingWriter(
    file: File,
    val onError: (exception: Exception) -> Unit
): BufferedWriter(file.appendOutputStream()) {

    private var hasErrors = false

    override fun append(csq: CharSequence?): Writer {
        if (hasErrors) {
            return this
        }
        try {
            return super.append(csq)
        } catch (e: Exception) {
            hasErrors = true
            onError(e)
        }
        return this
    }

    override fun newLine() {
        try {
            super.newLine()
        } catch (e: Exception) {
            hasErrors = true
            onError(e)
        }
    }

    override fun flush() {
        try {
            super.flush()
        } catch (e: Exception) {
            hasErrors = true
            onError(e)
        }
    }

    override fun close() {
        try {
            super.close()
        } catch (e: IOException) {
            hasErrors = true
            onError(e)
        }
    }

}
