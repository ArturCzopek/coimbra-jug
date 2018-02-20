package pl.simplecoding.coimbrajug

enum class TokenType {
    GREEN,
    RED;

    companion object {
        fun fromString(type: String) = when (type) {
            "green" -> GREEN
            "red" -> RED
            else -> throw UnsupportedOperationException()
        }
    }
}