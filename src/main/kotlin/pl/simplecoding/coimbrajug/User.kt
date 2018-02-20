package pl.simplecoding.coimbrajug

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.boot.CommandLineRunner
import org.springframework.data.repository.CrudRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
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
