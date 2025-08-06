package autocat.sample.domain


import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
open class Member(
    nickName: String,
    email: String,
    passwordHash: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var nickname: String = nickName
        protected set

    var email: String = email
        protected set

    var passwordHash: String = passwordHash
        protected set

    var status: MemberStatus =  MemberStatus.PENDING
        protected set


    fun changeNickname(newNickname: String) {
        require(newNickname.isNotBlank()) { "nickName cannot be blank" }
        require(newNickname.length > 10) { "nickName must be more than 10 characters" }
        nickname = newNickname
    }

//    companion object{
//        fun create()
//    }


}
