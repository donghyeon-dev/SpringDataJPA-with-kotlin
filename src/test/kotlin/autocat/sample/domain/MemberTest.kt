package autocat.sample.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest(showSql = true)
@DisplayName("Member 도메인 테스트")
class MemberTest() {

    private lateinit var member: Member

    @BeforeEach
    fun setup() {
        member = Member.register(
            MemberFixture.createMemberRegisterRequest()
        )
    }

    @Nested
    @DisplayName("Member 생성")
    inner class MemberCreation {

        @Test
        @DisplayName("Member를 생성할 수 있다")
        fun `should create member successfully`() {
            // given & when
            val member = Member.register(MemberFixture.createMemberRegisterRequest())

            // then
            with(member) {
                assertThat(id).isNull()
                assertThat(nickname).isEqualTo(MemberFixture.createMemberRegisterRequest().nickname)
                assertThat(email).isEqualTo(MemberFixture.createMemberRegisterRequest().email)
                assertThat(status).isEqualTo(MemberStatus.PENDING)
            }
        }
    }

    @Nested
    @DisplayName("업데이트")
    inner class MemberUpdate {

        @Test
        @DisplayName("유효한 닉네임으로 변경할 수 있다")
        fun `should update nickname with valid input`() {
            // given
            val newNickname = "newNickname"
            val updateRequest = MemberUpdateRequest(newNickname, "donghyeond@gmail.com")

            // when
            member.update(updateRequest)

            // then
            assertThat(member.nickname).isEqualTo(updateRequest.nickname)
        }

        @Test
        @DisplayName("유효한 이메일로 변경할 수 있다")
        fun `should update email with valid input`() {
            // given
            val newEmail = "newemail@test.com"
            val updateRequest = MemberUpdateRequest("autoacat", newEmail)

            // when
            member.update(updateRequest)

            // then
            assertThat(member.email).isEqualTo(newEmail)
        }

        @Test
        @DisplayName("유효한 이메일과 닉네임으로 변경할 수 있다")
        fun `should update with valid input`() {
            // given
            val updateRequest = MemberUpdateRequest("new autoacat", "newdonghyeon@gmail.new")

            // when
            member.update(updateRequest)

            // then
            with(member) {
                assertThat(email).isEqualTo(updateRequest.email)
                assertThat(nickname).isEqualTo(updateRequest.nickname)
            }
        }

        @Test
        @DisplayName("빈 닉네임으로 변경 시 예외가 발생한다")
        fun `should throw exception when updating to blank nickname`() {
            // given
            val blacnkNickname = ""
            val updateRequest = MemberUpdateRequest(blacnkNickname, "donghyeond@gmail.com")

            // when & then
            assertThatThrownBy { member.update(updateRequest) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("nickname cannot be blank")
        }

        @Test
        @DisplayName("공백만 있는 닉네임으로 변경 시 예외가 발생한다")
        fun `should throw exception when updating to whitespace nickname`() {
            // given
            val whitespaceNickname = " "
            val updateRequest = MemberUpdateRequest(whitespaceNickname, "donghyeond@gmail.com")

            // when & then
            assertThatThrownBy { member.update(updateRequest) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("nickname cannot be blank")
        }

        @Test
        @DisplayName("20자를 초과하는 닉네임으로 업데이트시 예외가 발생한다")
        fun `should throw exception when updating with nickname longer than 20 characters`() {
            // given
            val longNickname = "a".repeat(21)
            val updateRequest = MemberUpdateRequest(longNickname, "dongheond@gmail.com")

            // when & then
            assertThatThrownBy { member.update(updateRequest) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("nickname must be 20 characters or less")
        }

        @Test
        @DisplayName("빈 이메일로 변경 시 예외가 발생한다")
        fun `should throw exception when changing to blank email`() {
            // given
            val blankEmail = ""
            val updateRequest = MemberUpdateRequest("autoacat", blankEmail)

            // when & then
            assertThatThrownBy { member.update(updateRequest) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("email cannot be blank")
        }

        @Test
        @DisplayName("@가 없는 이메일로 변경 시 예외가 발생한다")
        fun `should throw exception when changing to invalid email format`() {
            // given
            val invalidEmail = "invalidemail"
            val updateRequest = MemberUpdateRequest("autoacat", invalidEmail)

            // when & then
            assertThatThrownBy { member.update(updateRequest) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("email must be valid format")
        }

        @Test
        @DisplayName("null 필드로 업데이트 시 업데이트가 일어나지 않는다")
        fun `should not update when updating with null fields`() {
            // given
            val created = MemberFixture.create()
            val updateRequest = MemberUpdateRequest(null, null)

            // when
            created.update(updateRequest)

            // then
            with(created) {
                assertThat(nickname).isEqualTo(MemberFixture.createMemberRegisterRequest().nickname).isNotNull()
                assertThat(email).isEqualTo(MemberFixture.createMemberRegisterRequest().email).isNotNull()
            }
        }
    }

    @Nested
    @DisplayName("비밀번호 해시 변경")
    inner class ChangePasswordHash {

        @Test
        @DisplayName("유효한 비밀번호 해시로 변경할 수 있다")
        fun `should change password hash with valid input`() {
            // given
            val newPasswordHash = "newEncryptedPassword"

            // when
            member.changePasswordHash(newPasswordHash)

            // then
            assertThat(member.passwordHash).isEqualTo(newPasswordHash)
        }

        @Test
        @DisplayName("빈 비밀번호 해시로 변경 시 예외가 발생한다")
        fun `should throw exception when changing to blank password hash`() {
            // given
            val blankPasswordHash = ""

            // when & then
            assertThatThrownBy { member.changePasswordHash(blankPasswordHash) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("passwordHash cannot be blank")
        }
    }

    @Nested
    @DisplayName("상태 변경(활성화)")
    inner class ChangeStatus {

        @Test
        @DisplayName("PENDING 상태는 ACTIVE 상태로 변경할 수 있다")
        fun `should change status to active`() {
            // given
            val member = Member.register(MemberFixture.createMemberRegisterRequest())

            // when
            member.activate()

            // then
            assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
        }

        @Test
        @DisplayName("ACTIVE 상태는 INACTIVE 상태로 변경할 수 있다")
        fun `should change status to inactive`() {
            // given
            val member = Member.register(MemberFixture.createMemberRegisterRequest())
            member.activate()

            //when
            member.deactivate()

            // then
            assertThat(member.status).isEqualTo(MemberStatus.INACTIVE)
        }

        @Test
        @DisplayName("PENDING 상태에서 INACTIVE 변경 시 예외가 발생한다")
        fun `should throw exception when changing status pendig to inactive`() {
            // given
            val member = Member.register(MemberFixture.createMemberRegisterRequest())

            //  when & then
            assertThatThrownBy {
                member.deactivate()
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("status must be ACTIVE to deactivate")
        }

        @Test
        @DisplayName("INACTIVE 상태에서 ACTIVE 변경 시 예외가 발생한다")
        fun `should throw exception when changing status inactive to active`() {
            // given
            val member = Member.register(MemberFixture.createMemberRegisterRequest())
            member.activate()
            member.deactivate()

            //  when & then
            assertThatThrownBy { member.activate() }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("status must be PENDING to activate")
        }
    }


    @Nested
    @DisplayName("Member 생성 시 검증")
    inner class MemberCreationValidation {

        @Test
        @DisplayName("빈 닉네임으로 생성 시 예외가 발생한다")
        fun `should throw exception when creating with blank nickname`() {
            assertThatThrownBy {
                Member.register(MemberRegisterRequest("", "test@test.com", "password"))
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("nickname cannot be blank")
        }

        @Test
        @DisplayName("빈 이메일로 생성 시 예외가 발생한다")
        fun `should throw exception when creating with blank email`() {
            assertThatThrownBy {
                Member.register(MemberRegisterRequest("nickname", "", "password"))
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("email cannot be blank")
        }

        @Test
        @DisplayName("잘못된 이메일 형식으로 생성 시 예외가 발생한다")
        fun `should throw exception when creating with invalid email format`() {
            assertThatThrownBy {
                Member.register(MemberRegisterRequest("nickname", "invalidemail", "password"))
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("email must be valid format")
        }

        @Test
        @DisplayName("빈 비밀번호로 생성 시 예외가 발생한다")
        fun `should throw exception when creating with blank password`() {
            assertThatThrownBy {
                Member.register(MemberRegisterRequest("nickname", "test@test.com", ""))
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("passwordHash cannot be blank")
        }
    }
}
