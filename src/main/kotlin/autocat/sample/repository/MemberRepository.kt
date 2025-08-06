package autocat.sample.repository

import autocat.sample.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface MemberRepository : JpaRepository<Member, Long> {

}
