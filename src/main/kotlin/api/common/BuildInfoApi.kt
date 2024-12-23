package io.github.llh4github.ksas.api.common

import io.github.llh4github.ksas.bo.BuildInfoBo
import io.github.llh4github.ksas.commons.JsonWrapper
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
class BuildInfoApi {

    fun fetchBuildInfo(): BuildInfoBo {
        val buildTime = try {
            File("build-time.log").readLines()[0]
        } catch (_: Exception) {
            "未知编译时间"
        }
        val version = try {
            File("project.version").readLines()[0]
        } catch (_: Exception) {
            "未知版本"
        }
        return BuildInfoBo(buildTime, version)
    }

    @GetMapping("/build/info")
    @Operation(summary = "获取构建信息")
    fun info(): JsonWrapper<BuildInfoBo> {
        val rs = fetchBuildInfo()
        return JsonWrapper.ok(rs)
    }
}
