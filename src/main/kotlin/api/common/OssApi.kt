package io.github.llh4github.ksas.api.common

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.llh4github.ksas.library.SecurityUtil
import io.github.llh4github.ksas.service.common.OssService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "OSS接口")
@RequestMapping("oss")
@RestController
class OssApi(private val service: OssService) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("upload/avatar")
    @Operation(summary = "上传头像")
    fun uploadAvatar(file: MultipartFile): JsonWrapper<String> {
        val rs = service.uploadAvatar(SecurityUtil.userId(), file)
        return JsonWrapper.ok(rs)
    }

    @PostMapping("upload/files")
    @Operation(summary = "上传文件")
    fun uploadFiles(file: MultipartFile): JsonWrapper<String> {
        val rs = service.uploadFile(SecurityUtil.userId(), file)
        return JsonWrapper.ok(rs)
    }

    @PostMapping("upload/temp")
    @Operation(summary = "上传临时文件")
    fun uploadTempFiles(file: MultipartFile): JsonWrapper<String> {
        val rs = service.uploadTempFile(SecurityUtil.userId(), file)
        return JsonWrapper.ok(rs)
    }
}
