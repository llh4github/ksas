package io.github.llh4github.ksas.service.common.impl

import io.github.llh4github.ksas.commons.consts.BucketNames
import io.github.llh4github.ksas.exception.OssException
import io.github.llh4github.ksas.library.MinioService
import io.github.llh4github.ksas.service.common.OssService
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class OssServiceImpl(private val minioService: MinioService) : OssService {
    @Value("\${ksas.sso.base-url}")
    private lateinit var baseUrl: String

    override fun uploadAvatar(userId: Long, file: MultipartFile): String {
        if (file.size > 2 * FileUtils.ONE_MB) {
            throw OssException.fileSizeExceed("文件大小不能超过2MB")
        }
        return baseUrl + minioService.uploadFile(
            BucketNames.AVATARS,
            file, mapOf("userId" to userId.toString())
        )
    }

    override fun uploadFile(userId: Long, file: MultipartFile): String {
        if (file.size > 10 * FileUtils.ONE_MB) {
            throw OssException.fileSizeExceed("文件大小不能超过10MB")
        }
        return baseUrl + minioService.uploadFile(
            BucketNames.ATTACHMENTS,
            file, mapOf("userId" to userId.toString())
        )
    }

    override fun uploadTempFile(userId: Long?, file: MultipartFile): String {
        if (file.size > 10 * FileUtils.ONE_MB) {
            throw OssException.fileSizeExceed("文件大小不能超过10MB")
        }
        return baseUrl + minioService.uploadFile(
            BucketNames.TEMP, file,
            userId?.let { mapOf("userId" to userId.toString()) }.orEmpty()
        )
    }
}
