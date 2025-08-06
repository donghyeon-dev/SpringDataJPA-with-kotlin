package autocat.sample.domain

import autocat.sample.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest(showSql = true)
class MemberTest {

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    fun createMember(){
        val member = Member("donghyeon","dh.park@gmail.com", "encryptedPassword")
        val saved = memberRepository.saveAndFlush(member)

        val found = memberRepository.findById(saved.id)

        assertThat(found.isPresent).isTrue()
        assertThat(found.get().id).isEqualTo(saved.id)
        assertThat(found.get().nickname).isEqualTo(saved.nickname)

    }

}
