package autocat.sample.applicatoin

import autocat.sample.domain.Member
import autocat.sample.domain.MemberCreateRequest
import autocat.sample.domain.MemberDto
import autocat.sample.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(private final val memberRepository: MemberRepository) {

    fun createMember(memberCreateRequest: MemberCreateRequest): Member {
        val member = Member(memberCreateRequest.nickname, memberCreateRequest.email, memberCreateRequest.passwordHash)
        return memberRepository.save(member)
    }

    fun getMembers(): MutableList<MemberDto> {
        return memberRepository.findAll()
            .map { MemberDto(it.id, it.nickname, it.email, it.passwordHash, it.status) }
            .toMutableList()
    }
}
