package io.github.llh4github.ksas.exception

import io.github.llh4github.ksas.commons.JsonWrapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExpHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleException(e: NoResourceFoundException): JsonWrapper<String> {
        val path = e.resourcePath
        logger.error(e) { "访问不存在的资源: $path" }
        return JsonWrapper.fail(data = "资源不存在: $path", code = "NOT_FOUND", msg = "NO_RESOURCE")
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleException(e: RuntimeException): JsonWrapper<String> {
        logger.error(e) { "系统出现未知错误: ${e.message}" }
        return JsonWrapper.fail(data = e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): JsonWrapper<String> {
        logger.error(e) { "系统出现未知错误: ${e.message}" }
        return JsonWrapper.fail(data = e.message)
    }
}
