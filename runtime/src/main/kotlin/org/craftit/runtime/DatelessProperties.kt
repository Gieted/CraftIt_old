package org.craftit.runtime

import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.util.*

class DatelessProperties : Properties() {
    companion object {
        private val hexDigit = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        )

        private fun toHex(nibble: Int): Char {
            return hexDigit[nibble and 0xF]
        }

        private fun writeComments(bw: BufferedWriter, comments: String) {
            bw.write("#")
            val len = comments.length
            var current = 0
            var last = 0
            val uu = CharArray(6)
            uu[0] = '\\'
            uu[1] = 'u'
            while (current < len) {
                val c = comments[current]
                if (c > '\u00ff' || c == '\n' || c == '\r') {
                    if (last != current) bw.write(comments.substring(last, current))
                    if (c > '\u00ff') {
                        uu[2] = toHex(c.code shr 12 and 0xf)
                        uu[3] = toHex(c.code shr 8 and 0xf)
                        uu[4] = toHex(c.code shr 4 and 0xf)
                        uu[5] = toHex(c.code and 0xf)
                        bw.write(String(uu))
                    } else {
                        bw.newLine()
                        if (c == '\r' && current != len - 1 && comments[current + 1] == '\n') {
                            current++
                        }
                        if (current == len - 1 ||
                            comments[current + 1] != '#' &&
                            comments[current + 1] != '!'
                        ) bw.write("#")
                    }
                    last = current + 1
                }
                current++
            }
            if (last != current) bw.write(comments.substring(last, current))
            bw.newLine()
        }


    }

    private fun store0(bw: BufferedWriter, comments: String?) {
        if (comments != null) {
            writeComments(bw, comments)
        }
        synchronized(this) {
            val e: Enumeration<*> = keys()
            while (e.hasMoreElements()) {
                var key = e.nextElement() as String
                var `val` = get(key) as String
                key = saveConvert(key, true)
                `val` = saveConvert(`val`, false)
                bw.write("$key=$`val`")
                bw.newLine()
            }
        }
        bw.flush()
    }

    private fun saveConvert(
        theString: String,
        escapeSpace: Boolean,
    ): String {
        val len = theString.length
        var bufLen = len * 2
        if (bufLen < 0) {
            bufLen = Int.MAX_VALUE
        }
        val outBuffer = StringBuffer(bufLen)
        for (x in 0 until len) {
            val aChar = theString[x]
            if (aChar.code in 62..126) {
                if (aChar == '\\') {
                    outBuffer.append('\\')
                    outBuffer.append('\\')
                    continue
                }
                outBuffer.append(aChar)
                continue
            }
            when (aChar) {
                ' ' -> {
                    if (x == 0 || escapeSpace) outBuffer.append('\\')
                    outBuffer.append(' ')
                }
                '\t' -> {
                    outBuffer.append('\\')
                    outBuffer.append('t')
                }
                '\n' -> {
                    outBuffer.append('\\')
                    outBuffer.append('n')
                }
                '\r' -> {
                    outBuffer.append('\\')
                    outBuffer.append('r')
                }
                '\u000C' -> {
                    outBuffer.append('\\')
                    outBuffer.append('f')
                }
                '=', ':', '#', '!' -> {
                    outBuffer.append('\\')
                    outBuffer.append(aChar)
                }
                else -> if ((aChar.code < 0x0020 || aChar.code > 0x007e)) {
                    outBuffer.append('\\')
                    outBuffer.append('u')
                    outBuffer.append(toHex(aChar.code shr 12 and 0xF))
                    outBuffer.append(toHex(aChar.code shr 8 and 0xF))
                    outBuffer.append(toHex(aChar.code shr 4 and 0xF))
                    outBuffer.append(toHex(aChar.code and 0xF))
                } else {
                    outBuffer.append(aChar)
                }
            }
        }
        return outBuffer.toString()
    }

    override fun store(out: OutputStream, comments: String?) {
        store0(BufferedWriter(OutputStreamWriter(out, "8859_1")), comments)
    }
}
