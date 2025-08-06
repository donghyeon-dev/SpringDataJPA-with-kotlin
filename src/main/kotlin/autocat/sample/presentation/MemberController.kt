package autocat.sample.presentation

import autocat.sample.applicatoin.MemberService
import autocat.sample.domain.Member
import autocat.sample.domain.MemberCreateRequest
import autocat.sample.domain.MemberDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController(private val memberService: MemberService) {

    @PostMapping
    fun createMember(@RequestBody memberCreateRequest: MemberCreateRequest): Member {
        return memberService.createMember(memberCreateRequest)
    }

    @GetMapping
    fun getMembers():MutableList<MemberDto>{
        return memberService.getMembers();
    }


}
