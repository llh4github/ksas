package io.github.llh4github.ksas.dbmodel

import com.fasterxml.jackson.annotation.JsonFormat
import io.github.llh4github.ksas.commons.DatetimeConstant
import io.github.llh4github.ksas.dbmodel.auth.User
import io.swagger.v3.oas.annotations.media.Schema
import org.babyfish.jimmer.sql.*
import java.time.LocalDateTime

@MappedSuperclass
interface BaseModel {

    @Id
    @GeneratedValue(generatorType = TableIdGenerator::class)
//    @JsonConverter(LongToStringConverter::class)
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

    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    val createdByUser: User?

    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    val updatedByUser: User?
}
