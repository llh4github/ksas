package io.github.llh4github.ksas.dbmodel

import com.fasterxml.jackson.annotation.JsonFormat
import io.github.llh4github.ksas.commons.DatetimeConstant
import io.swagger.v3.oas.annotations.media.Schema
import org.babyfish.jimmer.jackson.JsonConverter
import org.babyfish.jimmer.jackson.LongToStringConverter
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
interface BaseModel {

    @Id
    @GeneratedValue(generatorType = TableIdGenerator::class)
    @JsonConverter(LongToStringConverter::class) // 避免前端溢出
    val id: Long

    @get:Schema(
        title = "创建时间", description = "创建时间", example = "2021-11-11 01:01:01"
    )
    @get:JsonFormat(pattern = DatetimeConstant.DATE_TIME_FORMAT)
    val createdTime: LocalDateTime?

    @get:Schema(
        title = "更新时间", description = "更新时间", example = "2021-01-01 00:00:00"
    )
    @get:JsonFormat(pattern = DatetimeConstant.DATE_TIME_FORMAT)
    val updatedTime: LocalDateTime?
}
