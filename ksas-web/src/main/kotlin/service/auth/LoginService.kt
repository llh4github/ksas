package io.github.llh4github.ksas.service.auth

import io.github.llh4github.ksas.bo.LoginResultBo
import io.github.llh4github.ksas.bo.LogoutParam
import io.github.llh4github.ksas.dbmodel.auth.dto.UserLoginView

interface LoginService {

    fun login(view: UserLoginView): LoginResultBo

    fun logout(param: LogoutParam): Boolean
}
