package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import javax.inject.Inject

@ServerScope
class PropertyManagerModifier @Inject constructor(
    classPool: ClassPool,
    sourceMap: SourceMap,
) : BytecodeModifier(classPool, sourceMap) {

    override fun configure() {
        modifyClass({ net.minecraft.server.dedicated.PropertyManager }) {
            withSourceMap {
                method(loadFromFile, "java.nio.file.Path") {
                    setBody(
                        """{
                java.util.Properties properties = new org.craftit.runtime.ConsistentProperties();

                try {
                    java.io.InputStream inputStream = java.nio.file.Files.newInputStream($1, new java.nio.file.OpenOption[0]);
                    properties.load(inputStream);
                    inputStream.close();
                } catch (java.io.IOException ioexception) {
                    $LOGGER.error("Failed to load properties from file: " + $1);
                }

                return properties;
                    }"""
                    )
                }
            }
        }
    }
}
