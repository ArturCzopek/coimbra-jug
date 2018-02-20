package pl.simplecoding.coimbrajug

/**
 * @Author Artur Czopek
 * @Link https://simplecoding.pl/coimbra-jug
 */

enum class TokenType {
    GREEN,
    RED;

    companion object {
        @JvmStatic
        fun fromString(type: String) = when (type) {
            "green" -> GREEN
            "red" -> RED
            else -> throw UnsupportedOperationException()
        }
    }
}