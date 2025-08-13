package autocat.sample.repository

import autocat.sample.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {

    fun save(member: Member): Member
}
