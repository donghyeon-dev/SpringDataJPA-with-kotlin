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

    init {
        require(nickname.isNotBlank()) { "nickname cannot be blank" }
        require(nickname.length <= 20) { "nickname must be 20 characters or less" }
        require(email.isNotBlank()) { "email cannot be blank" }
        require(email.contains("@")) { "email must be valid format" }
        require(passwordHash.isNotBlank()) { "passwordHash cannot be blank" }
    }


    fun changeNickname(newNickname: String) {
        require(newNickname.isNotBlank()) { "nickname cannot be blank" }
        nickname = newNickname
    }
    fun changeEmail(newEmail: String){
        require(newEmail.isNotBlank()) { "email cannot be blank" }
        require(newEmail.contains("@")) { "email must be valid format" }
        email = newEmail
    }
    fun changePasswordHash(newPasswordHash: String){
        require(newPasswordHash.isNotBlank()) { "passwordHash cannot be blank" }
        passwordHash = newPasswordHash
    }
    fun changeStatus(newStatus: MemberStatus){
        require(newStatus != MemberStatus.PENDING) { "status cannot be PENDING" }
        status = newStatus
    }

    companion object{
        fun create(dto: MemberCreateRequest) : Member{
            return Member(dto.nickname, dto.email, dto.passwordHash)
        }
    }


}
