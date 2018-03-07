package pl.simplecoding.coimbrajug

import org.junit.Assert
import org.junit.Test

class SimpleTest {

    @Test
    fun `should two plus to returns four`() {
        val addOperation = { a: Int, b: Int -> a + b }
        Assert.assertEquals(4, addOperation(2, 2))
    }
}