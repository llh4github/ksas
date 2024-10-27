package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.dbmodel.auth.User
import io.github.llh4github.ksas.service.BaseService
import io.github.llh4github.ksas.service.UniqueDataUpsert

interface UserService : UniqueDataUpsert<User>, BaseService<User> {
}
