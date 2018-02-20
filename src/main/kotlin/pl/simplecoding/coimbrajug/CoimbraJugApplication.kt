package pl.simplecoding.coimbrajug

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @Author Artur Czopek
 * @Link https://simplecoding.pl/coimbra-jug
 */

@SpringBootApplication
class CoimbraJugApplication

fun main(args: Array<String>) {
    SpringApplication.run(CoimbraJugApplication::class.java, *args)
}
