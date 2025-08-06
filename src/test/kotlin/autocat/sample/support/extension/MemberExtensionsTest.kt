package autocat.sample.support.extension

import autocat.sample.domain.Member
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class MemberExtensionsTest {

    @Test
    fun testToDto() {
        val member = Member("donghyeon", "dh@gmail.com", "encryptedPassword")
        val dto = member.toDto()
        assertEquals(member.id, dto.id)
        assertEquals(member.nickname, dto.nickname)
        assertEquals(member.email, dto.email)
        assertEquals(member.passwordHash, dto.passwordHash)
        assertEquals(member.status, dto.status)
    }

}
