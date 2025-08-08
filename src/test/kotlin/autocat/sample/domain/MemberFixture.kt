package autocat.sample.domain

class MemberFixture {
    companion object{

        fun createMemberRegisterRequest() : MemberRegisterRequest{
            return MemberRegisterRequest(
                nickname = "autocat",
                email = "donghyeon@gmail.com",
                passwordHash = "superEncryptedPassword"
            )
        }
        fun createMemberRegisterRequest(email: String): MemberRegisterRequest {
            return MemberRegisterRequest(
                nickname = "autocat",
                email = email,
                passwordHash = "superEncryptedPassword"
            )
        }

        fun create() :Member{
            return Member.register(createMemberRegisterRequest())
        }

        fun create(email: String) :Member{
            return Member.register(createMemberRegisterRequest(email = email))
        }

    }
}
