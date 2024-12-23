package io.github.llh4github.ksas.service.common

import org.springframework.web.multipart.MultipartFile

interface OssService {
    /**
     * 上传头像，返回可访问的URL
     */
    fun uploadAvatar(userId: Long, file: MultipartFile): String

    /**
     * 上传文件，返回可访问的URL
     */
    fun uploadFile(userId: Long, file: MultipartFile): String

    /**
     * 上传临时文件，返回可访问的URL
     * 过期后自动删除
     */
    fun uploadTempFile(userId: Long? = null, file: MultipartFile): String
}
