package org.craftit.runtime.configuration

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class FileAdapter @Inject constructor(@Named("serverDirectory") private val serverDirectory: File): TypeAdapter<File>() {
    override fun write(writer: JsonWriter, value: File) {
        writer.value(value.toRelativeString(serverDirectory))
    }

    override fun read(reader: JsonReader): File {
        val path = reader.nextString()

        return serverDirectory.resolve(path)
    }
}
