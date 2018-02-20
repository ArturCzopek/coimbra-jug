package pl.simplecoding.coimbrajug

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.boot.CommandLineRunner
import org.springframework.data.repository.CrudRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.security.Principal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


/**
 * @Author Artur Czopek
 * @Link https://simplecoding.pl/coimbra-jug
 */

@Entity
@Table(name = "Users")
data class User(
        @Id @Column(name = "user_id") var id: Long = 0,
        @Column(name = "username") var name: String = "Name",
        @JsonIgnore @Column(name = "password") var password: String = "Password",
        @JsonIgnore @Column(name = "enabled") var enabled: Boolean = true,
        @JsonIgnore @Column(name = "authority_id") var authorityId: Long = 0,
        @Column(name = "received_green") var receivedGreen: Int = 0,
        @Column(name = "received_red") var receivedRed: Int = 0,
        @Column(name = "to_give_green") var toGiveGreen: Int = 10,
        @Column(name = "to_give_red") var toGiveRed: Int = 10
)


interface UserRepository : CrudRepository<User, Long> {

    override fun findAll(): List<User>

    fun findOneByName(name: String): User
}


@Service
class UserInsertRunner(
        private val userRepository: UserRepository,
        private val authRepository: AuthRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        (1..20).forEach {
            val id = it.toLong()
            val name = "user$id"

            val auth = Authority(id, name, "user")
            authRepository.save(auth)

            val user = User(name = name, id = id, authorityId = id, password = bCryptPasswordEncoder.encode(name))
            userRepository.save(user)
        }
    }
}


typealias UsersDataList = List<Map<String, Any>>

@Service
class UserService(private val userRepository: UserRepository) {

    fun getData(principal: Principal) = userRepository.findOneByName(principal.name)

    fun giveToken(toUserId: Long, tokenType: TokenType, principal: Principal) {
        val loggedInUser = userRepository.findOneByName(principal.name)
        val userToGive = userRepository.findById(toUserId).get()

        when (tokenType) {
            TokenType.GREEN -> giveGreenToken(loggedInUser, userToGive)
            TokenType.RED -> giveRedToken(loggedInUser, userToGive)
        }
    }

    fun getAllUsers(principal: Principal): UsersDataList {
        return userRepository
                .findAll()
                .map { mapOf("id" to it.id, "name" to it.name) }
                .filter { it["name"] as String != principal.name }
    }

    private fun giveGreenToken(loggedInUser: User, userToGive: User) {
        if (loggedInUser.toGiveGreen <= 0) {
            return
        } else {
            loggedInUser transferGreenTokenTo userToGive
            userRepository.run {
                save(loggedInUser)
                save(userToGive)
            }
        }
    }

    private fun giveRedToken(loggedInUser: User, userToGive: User) {
        if (loggedInUser.toGiveRed <= 0) {
            return
        } else {
            loggedInUser transferRedTokenTo userToGive
            userRepository.run {
                save(loggedInUser)
                save(userToGive)
            }
        }
    }
}

infix fun User.transferGreenTokenTo(userTo: User) {
    this.toGiveGreen--
    userTo.receivedGreen++
    println("""
        Sent green token from
        ${this.name}
        to
        ${userTo.name}
    """.trimIndent())
}

infix fun User.transferRedTokenTo(userTo: User) {
    this.toGiveRed--
    userTo.receivedRed++
    println("""
        Sent red token from
        ${this.name}
        to
        ${userTo.name}
    """.trimIndent())
}