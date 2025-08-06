package autocat.sample.applicatoin

import autocat.sample.domain.Member
import autocat.sample.domain.MemberCreateRequest
import autocat.sample.domain.MemberDto
import autocat.sample.repository.MemberRepository
import autocat.sample.support.extension.toDto
import org.springframework.stereotype.Service

@Service
class MemberService(private final val memberRepository: MemberRepository) {

    fun createMember(memberCreateRequest: MemberCreateRequest): Member {
        val member = Member.create(memberCreateRequest)
        return memberRepository.save(member)
    }

    fun getMembers(): MutableList<MemberDto> {
        return memberRepository.findAll()
            .map { it.toDto() }
            .toMutableList()
    }
}
