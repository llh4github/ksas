package io.github.llh4github.ksas.service.common.impl

import io.github.llh4github.ksas.commons.consts.BucketNames
import io.github.llh4github.ksas.library.MinioService
import io.github.llh4github.ksas.service.common.OssService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class OssServiceImpl(private val minioService: MinioService) : OssService {
    @Value("\${ksas.sso.base-url}")
    private lateinit var baseUrl: String

    override fun uploadAvatar(userId: Long, file: MultipartFile): String {
        return baseUrl + minioService.uploadFile(
            BucketNames.AVATARS,
            file, mapOf("userId" to userId.toString())
        )
    }
}
