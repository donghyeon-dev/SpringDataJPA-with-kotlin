package autocat.sample.domain

data class MemberCreateRequest(
    val nickname: String,
    val email: String,
    val passwordHash: String
){

}
