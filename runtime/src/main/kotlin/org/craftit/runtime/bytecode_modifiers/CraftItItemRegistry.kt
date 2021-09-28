package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import javax.inject.Inject

@ServerScope
class CraftItItemRegistry @Inject constructor(
    classPool: ClassPool,
    sourceMap: SourceMap,
) : BytecodeModifier(classPool, sourceMap) {

    override fun configure() {
        createClass("CraftItItemRegistry", { net.minecraft.util.registry.DefaultedRegistry }) {
            withSourceMap {
                delegateConstructor { it.parameterTypes.isNotEmpty() }

                method(
                    register,
                    sourceMap { net.minecraft.util.RegistryKey }(),
                    "java.lang.Object",
                    parametersCount = 3
                ) {
                    insertAfter(
                        """
                    System.out.println($1);
                        """
                    )
                }
            }
        }
    }
}
