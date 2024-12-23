package io.github.llh4github.ksas.config

import io.github.llh4github.ksas.dbmodel.BaseModel
import io.github.llh4github.ksas.dbmodel.BaseModelDraft
import io.github.llh4github.ksas.library.SecurityUtil
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BaseModelDraftInterceptor : DraftInterceptor<BaseModel, BaseModelDraft> {
    override fun beforeSave(draft: BaseModelDraft, original: BaseModel?) {
        if (!isLoaded(draft, BaseModel::updatedTime)) {
            draft.updatedTime = LocalDateTime.now()
        }
        if (!isLoaded(draft, BaseModel::updatedByUser)) {
            draft.updatedByUser {
                id = SecurityUtil.userId()
            }
        }
        if (original === null) {
            if (!isLoaded(draft, BaseModel::createdTime)) {
                draft.createdTime = LocalDateTime.now()
            }

            if (!isLoaded(draft, BaseModel::createdByUser)) {
                draft.createdByUser {
                    id = SecurityUtil.userId()
                }
            }
        }
    }
}
