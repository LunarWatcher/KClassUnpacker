import oliviazoe0.processor.AutoUnpack

@AutoUnpack
data class ExampleClass(
    val x: String, val answer: Int, var mutable: Float,
    var the: String = "center"
) {
    private val secret = "Don't tell anyone the password is 1234"
    fun answer() {
        require(answer == 42) { "THE ANSWER IS A LIE!" }
        println("The answer is $answer");
    }

    companion object{
        @JvmStatic
        val leaveMeAlone = "Static String"
        val nonStat = ""
    }
}
@AutoUnpack
data class Delegated(val title: String) {
    val x by lazy { 42 }
}

@AutoUnpack
data class PrivateData(val one: String, val two: String, private val secret: String)

fun main(a: Array<String>) {
    val cls = ExampleClass("cannot hold!", 42, 3.14159f)

    var size = 0
    for (variable in cls) {
        size++;
        println(variable)
    }
    require(size == 4) { "Too many items for public-only" }
    size = 0;
    val delegated = Delegated("Hello World!")
    for (variable in delegated) {
        size++;
        println("Variable");
    }
    require(size == 2) { "Too many items for the delegate" }
    size = 0;
    val privateInclusive = PrivateData("hello", "world", "Don't look at me!")
    for (variable in privateInclusive) {
        size++;
        println(variable)
    }
    require(size == 2) { "Too many items for the privacy class" }
}