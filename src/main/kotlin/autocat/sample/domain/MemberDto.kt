package autocat.sample.domain

import java.io.Serializable

/**
 * DTO for {@link autocat.sample.domain.Member}
 */
data class MemberDto(
    var id: Long? = null,
    var nickname: String ,
    var email: String ,
    var passwordHash: String,
    var status: MemberStatus = MemberStatus.PENDING
)
