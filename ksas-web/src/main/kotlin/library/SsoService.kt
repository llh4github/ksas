package io.github.llh4github.ksas.library

import io.minio.MinioClient
import org.springframework.stereotype.Component

@Component
class SsoService(private val client: MinioClient) {

}
