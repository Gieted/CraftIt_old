package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain
import javax.inject.Inject
import javax.inject.Named

class PropertyManagerModifier @Inject constructor(
    @Named("server") private val classLoader: ClassLoader,
    private val sourceMap: SourceMap,
    private val classPool: ClassPool,
    private val protectionDomain: ProtectionDomain
) : BytecodeModifier {

    override fun modify() {
        with(sourceMap { net.minecraft.server.dedicated.PropertyManager }) {
            val propertyManager = classPool.get(this())

            propertyManager.getDeclaredMethod(loadFromFile, arrayOf(classPool.get("java.nio.file.Path"))).setBody(
                """{
                java.util.Properties properties = new org.craftit.runtime.DatelessProperties();

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

            propertyManager.toClass(classLoader, protectionDomain)
        }
    }
}
