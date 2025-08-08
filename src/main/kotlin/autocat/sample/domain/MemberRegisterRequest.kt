package autocat.sample.domain

data class MemberRegisterRequest(
    val nickname: String,
    val email: String,
    val passwordHash: String
){

}
