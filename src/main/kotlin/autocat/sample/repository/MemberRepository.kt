package autocat.sample.repository

import autocat.sample.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun save(member: Member): Member
}
