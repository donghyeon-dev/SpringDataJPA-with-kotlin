package autocat.sample.applicatoin

import autocat.sample.domain.Member
import autocat.sample.domain.MemberRegisterRequest
import autocat.sample.domain.MemberUpdateRequest
import autocat.sample.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(private final val memberRepository: MemberRepository) {

    fun register(memberRegisterRequest: MemberRegisterRequest): Member {
        val member = Member.register(memberRegisterRequest)
        return memberRepository.save(member)
    }

    fun getMembers(): MutableList<Member> {
        return memberRepository.findAll()
            .toMutableList()
    }

    fun findMember(memberId: Long): Member? {
        return memberRepository.findByIdOrNull(memberId)
    }

    fun updateMember(memberId: Long, memberUpdateRequest: MemberUpdateRequest): Member {
        val member = requireNotNull(memberRepository.findByIdOrNull(memberId)) { "Member not found" }
        member.update(memberUpdateRequest)
        return memberRepository.save(member)
    }
}
