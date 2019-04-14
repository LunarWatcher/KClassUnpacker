import oliviazoe0.processor.AutoUnpack

@AutoUnpack
data class ExampleClass(
    val x: String, val answer: Int, var mutable: Float,
    var the: String = "center"
) {
    fun answer() {
        require(answer == 42) { "THE ANSWER IS A LIE!" }
        println("The answer is $answer");
    }
}

fun main(a: Array<String>) {
    val cls = ExampleClass("cannot hold!", 42, 3.14159f)

    for (variable in cls) {
        println(variable)
    }
}