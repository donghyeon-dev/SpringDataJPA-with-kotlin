package autocat.sample.support.extension

import autocat.sample.domain.Member
import autocat.sample.domain.MemberDto


fun Member.toDto(): MemberDto = MemberDto(
    id = this.id,
    nickname = this.nickname,
    email = this.email,
    passwordHash = this.passwordHash,
    status = this.status
)
