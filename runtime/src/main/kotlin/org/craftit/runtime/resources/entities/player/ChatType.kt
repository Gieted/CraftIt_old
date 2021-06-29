
package org.craftit.runtime.resources.entities.player

import org.craftit.runtime.source_maps.SourceMap
import javax.inject.Inject
import javax.inject.Named

@Suppress("PropertyName")
class ChatType @Inject constructor(sourceMap: SourceMap, classLoader: ClassLoader) {
    val CHAT: Any
    val SYSTEM: Any
    val GAME_INFO: Any

    init {
        with(sourceMap { net.minecraft.util.text.ChatType }) {
            val chatTypeClass = classLoader.loadClass(this())
            val constants = chatTypeClass.declaredFields.filter { it.isEnumConstant }
            this@ChatType.CHAT = constants.find { it.name == CHAT }!!.get(null)
            this@ChatType.SYSTEM = constants.find { it.name == SYSTEM }!!.get(null)
            this@ChatType.GAME_INFO = constants.find { it.name == GAME_INFO }!!.get(null)
        }
    }
}

