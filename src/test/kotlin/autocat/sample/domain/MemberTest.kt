package autocat.sample.domain

import autocat.sample.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest(showSql = true)
@DisplayName("Member 도메인 테스트")
class MemberTest {

    @Autowired
    private lateinit var memberRepository: MemberRepository

    private lateinit var member: Member

    @BeforeEach
    fun setup() {
        member = Member.create(
            MemberCreateRequest(
                nickname = "donghyeon",
                email = "dh@google.com",
                passwordHash = "encryptedPassword"
            )
        )
    }

    @Nested
    @DisplayName("Member 생성 및 저장")
    inner class MemberCreationAndPersistence {

        @Test
        @DisplayName("Member를 생성하고 저장할 수 있다")
        fun `should create and save member successfully`() {
            // given & when
            val saved = memberRepository.save(member)
            val savedId = requireNotNull(saved.id) { "저장된 Member의 ID는 null일 수 없습니다" }

            // then
            val found = requireNotNull(memberRepository.findByIdOrNull(savedId))

            with(found) {
                assertThat(id).isEqualTo(saved.id)
                assertThat(nickname).isEqualTo(saved.nickname)
                assertThat(email).isEqualTo(saved.email)
                assertThat(status).isEqualTo(MemberStatus.PENDING)
            }
        }

        @Test
        @DisplayName("올바른 초기값으로 Member가 생성된다")
        fun `should create member with correct initial values`() {
            // then
            assertThat(member.nickname).isEqualTo("donghyeon")
            assertThat(member.email).isEqualTo("dh@google.com")
            assertThat(member.passwordHash).isEqualTo("encryptedPassword")
            assertThat(member.status).isEqualTo(MemberStatus.PENDING)
        }
    }

    @Nested
    @DisplayName("닉네임 변경")
    inner class ChangeNickname {

        @Test
        @DisplayName("유효한 닉네임으로 변경할 수 있다")
        fun `should change nickname with valid input`() {
            // given
            val newNickname = "newNickname"

            // when
            member.changeNickname(newNickname)

            // then
            assertThat(member.nickname).isEqualTo(newNickname)
        }

        @Test
        @DisplayName("빈 닉네임으로 변경 시 예외가 발생한다")
        fun `should throw exception when changing to blank nickname`() {
            // given
            val blankNickname = ""

            // when & then
            assertThatThrownBy { member.changeNickname(blankNickname) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("nickname cannot be blank")
        }

        @Test
        @DisplayName("공백만 있는 닉네임으로 변경 시 예외가 발생한다")
        fun `should throw exception when changing to whitespace nickname`() {
            // given
            val whitespaceNickname = "   "

            // when & then
            assertThatThrownBy { member.changeNickname(whitespaceNickname) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("nickname cannot be blank")
        }

        @Test
        @DisplayName("20자를 초과하는 닉네임으로 생성 시 예외가 발생한다")
        fun `should throw exception when creating with nickname longer than 20 characters`() {
            // given
            val longNickname = "a".repeat(21)

            // when & then
            assertThatThrownBy {
                Member.create(MemberCreateRequest(longNickname, "test@test.com", "password"))
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("nickname must be 20 characters or less")
        }
    }

    @Nested
    @DisplayName("이메일 변경")
    inner class ChangeEmail {

        @Test
        @DisplayName("유효한 이메일로 변경할 수 있다")
        fun `should change email with valid input`() {
            // given
            val newEmail = "newemail@test.com"

            // when
            member.changeEmail(newEmail)

            // then
            assertThat(member.email).isEqualTo(newEmail)
        }

        @Test
        @DisplayName("빈 이메일로 변경 시 예외가 발생한다")
        fun `should throw exception when changing to blank email`() {
            // given
            val blankEmail = ""

            // when & then
            assertThatThrownBy { member.changeEmail(blankEmail) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("email cannot be blank")
        }

        @Test
        @DisplayName("@가 없는 이메일로 변경 시 예외가 발생한다")
        fun `should throw exception when changing to invalid email format`() {
            // given
            val invalidEmail = "invalidemail"

            // when & then
            assertThatThrownBy { member.changeEmail(invalidEmail) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("email must be valid format")
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
    @DisplayName("상태 변경")
    inner class ChangeStatus {

        @Test
        @DisplayName("ACTIVE 상태로 변경할 수 있다")
        fun `should change status to active`() {
            // given
            val newStatus = MemberStatus.ACTIVE

            // when
            member.changeStatus(newStatus)

            // then
            assertThat(member.status).isEqualTo(newStatus)
        }

        @Test
        @DisplayName("INACTIVE 상태로 변경할 수 있다")
        fun `should change status to inactive`() {
            // given
            val newStatus = MemberStatus.INACTIVE

            // when
            member.changeStatus(newStatus)

            // then
            assertThat(member.status).isEqualTo(newStatus)
        }

        @Test
        @DisplayName("PENDING 상태로 변경 시 예외가 발생한다")
        fun `should throw exception when changing to pending status`() {
            // given
            val pendingStatus = MemberStatus.PENDING

            // when & then
            assertThatThrownBy { member.changeStatus(pendingStatus) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("status cannot be PENDING")
        }
    }


    @Nested
    @DisplayName("Member 생성 시 검증")
    inner class MemberCreationValidation {

        @Test
        @DisplayName("빈 닉네임으로 생성 시 예외가 발생한다")
        fun `should throw exception when creating with blank nickname`() {
            assertThatThrownBy {
                Member.create(MemberCreateRequest("", "test@test.com", "password"))
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("nickname cannot be blank")
        }

        @Test
        @DisplayName("빈 이메일로 생성 시 예외가 발생한다")
        fun `should throw exception when creating with blank email`() {
            assertThatThrownBy {
                Member.create(MemberCreateRequest("nickname", "", "password"))
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("email cannot be blank")
        }

        @Test
        @DisplayName("잘못된 이메일 형식으로 생성 시 예외가 발생한다")
        fun `should throw exception when creating with invalid email format`() {
            assertThatThrownBy {
                Member.create(MemberCreateRequest("nickname", "invalidemail", "password"))
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("email must be valid format")
        }

        @Test
        @DisplayName("빈 비밀번호로 생성 시 예외가 발생한다")
        fun `should throw exception when creating with blank password`() {
            assertThatThrownBy {
                Member.create(MemberCreateRequest("nickname", "test@test.com", ""))
            }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("passwordHash cannot be blank")
        }
    }
}
