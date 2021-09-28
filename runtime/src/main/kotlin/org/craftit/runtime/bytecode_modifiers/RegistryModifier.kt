package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import org.craftit.runtime.source_maps.SourceMap
import javax.inject.Inject

@Suppress("LocalVariableName")
class RegistryModifier @Inject constructor(
    classPool: ClassPool,
    sourceMap: SourceMap,
) : BytecodeModifier(classPool, sourceMap) {

    override fun configure() {
        modifyClass({ net.minecraft.util.registry.Registry }) {
            withSourceMap {
                method(
                    registerDefaulted,
                    sourceMap { net.minecraft.util.RegistryKey }(),
                    "java.lang.String",
                    parametersCount = 4
                ) {
                    val DefaultedRegistry = sourceMap {net.minecraft.util.registry.DefaultedRegistry}()
                    setBody(
                        """
                    if ($1 == $ITEM_REGISTRY) {
                        return ($DefaultedRegistry) $internalRegister($1, new CraftItItemRegistry($2, $1, $3), $4, $3);
                    } else {
                        return ${registerDefaulted}_native($1, $2, $3, $4);
                    }
                        """
                    )
                }
            }
        }
    }
}
