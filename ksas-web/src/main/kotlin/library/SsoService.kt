package io.github.llh4github.ksas.library

import io.github.llh4github.ksas.commons.IdGenerator
import io.github.oshai.kotlinlogging.KotlinLogging
import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectsArgs
import io.minio.messages.DeleteObject
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.time.LocalDate

@Component
class SsoService(private val client: MinioClient) {
    private val logger = KotlinLogging.logger {}

    /**
     * 上传文件
     * @param bucket 存储桶
     * @param file 文件
     * @param tags 文件标签
     * @return 含相对路径的文件名
     */
    fun uploadFile(
        bucket: String,
        file: MultipartFile,
        tags: Map<String, String> = emptyMap()
    ): String {
        val now = LocalDate.now()
        val ext = FilenameUtils.getExtension(file.originalFilename)
        val baseName = FilenameUtils.getBaseName(file.originalFilename)
        val fileName = "/${now.year}/${now.monthValue}/$baseName-${IdGenerator.nextId()}.${ext}"
        val args = PutObjectArgs.builder()
            .bucket(bucket)
            .tags(tags)
            .`object`(fileName)
            .contentType(file.contentType)
            .stream(file.inputStream, file.size, -1)
        client.putObject(args.build())
        return fileName
    }

    /**
     * 获取文件
     * @param bucket 存储桶
     * @param fileName 含相对路径的文件名
     */
    fun fetchFile(bucket: String, fileName: String): InputStream {
        val args = GetObjectArgs.builder()
            .bucket(bucket)
            .`object`(fileName)
        return client.getObject(args.build())
    }

    /**
     * 删除文件
     * @param bucket 存储桶
     * @param fileName 含相对路径的文件名
     */
    fun deleteFile(bucket: String, fileName: String): Boolean {
        val failed = deleteFiles(bucket, listOf(fileName))
        return failed.isEmpty()
    }

    /**
     * 批量删除文件
     * @param bucket 存储桶
     * @param fileNames 含相对路径的文件名列表
     * @return 删除失败的文件名列表
     */
    @Suppress("TooGenericExceptionCaught")
    fun deleteFiles(bucket: String, fileNames: List<String>): List<String> {
        val deleteObjs = fileNames.map { DeleteObject(it) }
        val builder = RemoveObjectsArgs.builder()
            .bucket(bucket)
            .objects(deleteObjs)
        val rs = client.removeObjects(builder.build())
        return rs.mapNotNull {
            try {
                it.get()
            } catch (e: Exception) {
                logger.error(e) { "删除文件失败：${e.message}" }
                null
            }
        }.map { it.objectName() }.toList()
    }
}
