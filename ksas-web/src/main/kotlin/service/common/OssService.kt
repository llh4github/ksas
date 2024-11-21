package io.github.llh4github.ksas.service.common

import org.springframework.web.multipart.MultipartFile

interface OssService {
    /**
     * 上传头像，返回可访问的URL
     */
    fun uploadAvatar(userId: Long, file: MultipartFile): String
}
