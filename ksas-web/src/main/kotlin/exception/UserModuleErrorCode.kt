package io.github.llh4github.ksas.exception

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField

@ErrorFamily
enum class UserModuleErrorCode {
    @ErrorField(name = "username", type = String::class)
    USERNAME_EXISTS,

    USERNAME_NO_EXISTS,

    PASSWORD_ERROR,
}
