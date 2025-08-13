package autocat.sample.domain


import jakarta.persistence.Entity

@Entity
open class Member(
    nickname: String,
    email: String,
    passwordHash: String
) : BaseEntity() {

    var nickname: String = nickname
        protected set

    var email: String = email
        protected set

    var passwordHash: String = passwordHash
        protected set

    var status: MemberStatus = MemberStatus.PENDING
        protected set

    init {
        validateNickname(nickname)
        validateEmail(email)
        validatePasswordHash(passwordHash)
    }
    private fun validateNickname(nickname: String){
        require(nickname.isNotBlank()) { "nickname cannot be blank" }
        require(nickname.length <= 20) { "nickname must be 20 characters or less" }
    }
    private fun validateEmail(email: String){
        require(email.isNotBlank()) { "email cannot be blank" }
        require(email.contains("@")) { "email must be valid format" }
        }
    private fun validatePasswordHash(passwordHash: String){
        require(passwordHash.isNotBlank()) { "passwordHash cannot be blank" }
    }

    fun changePasswordHash(newPasswordHash: String) {
        validatePasswordHash(newPasswordHash)
        passwordHash = newPasswordHash
    }

    private fun changeNickname(newNicnkame: String){
        validateNickname(newNicnkame)
        nickname = newNicnkame
    }

    private fun changeEmail(newEmail: String){
        validateEmail(newEmail)
        email = newEmail
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
        request.nickname?.let { changeNickname(it) }
        request.email?.let { changeEmail(it) }

    }

    companion object {
        fun register(dto: MemberRegisterRequest): Member {
            return Member(dto.nickname, dto.email, dto.passwordHash)
        }
    }

}
