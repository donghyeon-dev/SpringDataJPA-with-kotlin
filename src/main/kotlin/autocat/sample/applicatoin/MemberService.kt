package autocat.sample.applicatoin

import autocat.sample.domain.Member
import autocat.sample.domain.MemberCreateRequest
import autocat.sample.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(private final val memberRepository: MemberRepository) {

    fun createMember(memberCreateRequest: MemberCreateRequest): Member {
        val member = Member.create(memberCreateRequest)
        return memberRepository.save(member)
    }

    fun getMembers(): MutableList<Member> {
        return memberRepository.findAll()
            .toMutableList()
    }

    fun findMember(memberId: Long): Member? {
        return memberRepository.findByIdOrNull(memberId)
    }
}
