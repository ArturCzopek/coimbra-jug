package pl.simplecoding.coimbrajug

import com.fasterxml.jackson.annotation.JsonIgnore
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
