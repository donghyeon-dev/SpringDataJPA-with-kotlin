package autocat.sample.presentation

import autocat.sample.applicatoin.MemberService
import autocat.sample.domain.Member
import autocat.sample.domain.MemberCreateRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/members")
class MemberController(private val memberService: MemberService) {

    @PostMapping
    fun createMember(@RequestBody memberCreateRequest: MemberCreateRequest): ResponseEntity<Member> {
        val created = memberService.createMember(memberCreateRequest)
        return ResponseEntity.created(URI("/members/${created.id}"))
            .body(created)
    }

    @GetMapping
    fun getMembers(): ResponseEntity<MutableList<Member>> {
        return ResponseEntity.ok(memberService.getMembers())
    }

    @GetMapping("/{memberId}")
    fun findMember(@PathVariable memberId: Long): ResponseEntity<Member>? {
        return ResponseEntity.ofNullable(memberService.findMember(memberId))
    }


}
