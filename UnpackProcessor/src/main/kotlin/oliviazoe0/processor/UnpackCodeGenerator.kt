package oliviazoe0.processor

import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class UnpackCodeGenerator : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(AutoUnpack::class.java.name)
    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        // Find elements with the annotation
        val annotatedElements = roundEnv.getElementsAnnotatedWith(AutoUnpack::class.java)
        if(annotatedElements.isEmpty()) {
            // Self-explanatory
            return false;
        }
        // Iterate the elements
        annotatedElements.forEach { element ->
            // Grab the name and package
            val name = element.simpleName.toString()
            val pkg = processingEnv.elementUtils.getPackageOf(element).toString()
            // Then generate the class
            generateClass(name,
                if (pkg == "unnamed package") "" else pkg, // This is a patch for an issue where classes in the root
                                                           // package return package as "unnamed package" rather than empty,
                                                           // which breaks syntax because "package unnamed package" isn't legal.
                element)
        }
        // Return true for success
        return true;
    }

    private fun generateClass(className: String, pkg: String, element: Element){
        val parcelableCreatorName = "CREATOR" // Ignore class field used for implementing the Android Parcelable interface
        val classVariables = element.enclosedElements
            .filter { it.kind == ElementKind.FIELD && it.simpleName != parcelableCreatorName } // Grab the variables
        if (classVariables.isEmpty()) return; // Self-explanatory

        val file = FileSpec.builder(pkg, className)
            .addFunction(FunSpec.builder("iterator") // For automatic unpacking in a for loop
                .receiver(element.asType().asTypeName().copy()) // Add it as an extension function of the class
                .addStatement("return listOf(${classVariables.joinToString(", ") { it.simpleName }}).iterator()") // add the return statement. Create a list, push an iterator.
                .addModifiers(KModifier.PUBLIC, KModifier.OPERATOR) // This needs to be public. Because it's an iterator, the function also needs the `operator` keyword
                .build()
            ).build()

        // Grab the generate directory.
        val genDir = processingEnv.options["kapt.kotlin.generated"]!!
        // Then write the file.
        file.writeTo(File(genDir, "${className}Generated.kt"))
    }

}
