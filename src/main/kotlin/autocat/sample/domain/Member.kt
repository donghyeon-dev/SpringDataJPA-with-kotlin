package autocat.sample.domain


import jakarta.persistence.Column
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

    @Column(nullable = false, length = 20 )
    var nickname: String = nickName
        protected set

    @Column(nullable = false)
    var email: String = email
        protected set

    @Column(nullable = false)
    var passwordHash: String = passwordHash
        protected set

    @Column(nullable = false)
    var status: MemberStatus = MemberStatus.PENDING
        protected set

    init {
        require(nickname.isNotBlank()) { "nickname cannot be blank" }
        require(nickname.length <= 20) { "nickname must be 20 characters or less" }
        require(email.isNotBlank()) { "email cannot be blank" }
        require(email.contains("@")) { "email must be valid format" }
        require(passwordHash.isNotBlank()) { "passwordHash cannot be blank" }
    }


    fun changePasswordHash(newPasswordHash: String) {
        require(newPasswordHash.isNotBlank()) { "passwordHash cannot be blank" }
        passwordHash = newPasswordHash
    }

    fun activate() {
        require(status == MemberStatus.PENDING) { "status must be PENDING to activate" }
        status = MemberStatus.ACTIVE
    }

    fun deactivate() {
        require(status == MemberStatus.ACTIVE) { "status must be ACTIVE to deactivate" }
        status = MemberStatus.INACTIVE
    }

    fun update(request: MemberUpdateRequest) {
        this.apply {
            request.nickname.let { this.nickname = it }
            request.email.let { this.email = it }
        }

    }

    companion object {
        fun register(dto: MemberRegisterRequest): Member {
            return Member(dto.nickname, dto.email, dto.passwordHash)
        }
    }


}
