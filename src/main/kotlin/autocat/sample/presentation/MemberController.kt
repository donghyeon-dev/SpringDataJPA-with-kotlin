package autocat.sample.presentation

import autocat.sample.applicatoin.MemberService
import autocat.sample.domain.Member
import autocat.sample.domain.MemberRegisterRequest
import autocat.sample.domain.MemberUpdateRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/members")
class MemberController(private val memberService: MemberService) {

    @PostMapping
    fun register(@RequestBody memberRegisterRequest: MemberRegisterRequest): ResponseEntity<Member> {
        val created = memberService.register(memberRegisterRequest)
        return ResponseEntity.created(URI("/members/${created.id}"))
            .body(created)
    }

    @GetMapping
    fun getMembers(): ResponseEntity<MutableList<Member>> {
        return ResponseEntity.ok(memberService.getMembers())
    }

    @GetMapping("/{memberId}")
    fun findMember(@PathVariable memberId: UUID): ResponseEntity<Member>? {
        return ResponseEntity.ofNullable(memberService.findMember(memberId))
    }

    @PatchMapping("/{memberId}")
    fun updateMember(@PathVariable memberId: UUID, @RequestBody memberUpdateRequest: MemberUpdateRequest): ResponseEntity<Member>{
        return ResponseEntity.ok(memberService.updateMember(memberId, memberUpdateRequest))
    }


}
