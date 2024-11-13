package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.dbmodel.auth.dto.UserAddInput
import io.github.llh4github.ksas.service.BaseService

interface UserService : BaseService<User> {
    fun addUnique(input: UserAddInput): User
}
