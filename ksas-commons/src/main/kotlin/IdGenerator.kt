package io.github.llh4github.ksas.commons

import com.github.yitter.contract.IdGeneratorOptions
import com.github.yitter.idgen.YitIdHelper

object IdGenerator {

    fun nextId(): Long = YitIdHelper.nextId()

    fun nextIdStr(): String = YitIdHelper.nextId().toString()

    fun configGenerator(property: IdGeneratorProperty) {
        val options = IdGeneratorOptions(property.workerId)
        options.SeqBitLength = property.seqBitLength
        YitIdHelper.setIdGenerator(options)
    }
}

open class IdGeneratorProperty {
    /**
     *  WorkerId 最大值为2^6-1，即默认最多支持64个节点。
     *
     *  **多实例部署时最好修改为不同值。**
     */
    var workerId: Short = 1

    /**
     * **一般不用改这个配置。**
     *
     * 默认值6，限制每毫秒生成的ID个数。若生成速度超过5万个/秒，建议加大 SeqBitLength 到 10。
     */
    @Suppress("MagicNumber")
    var seqBitLength: Byte = 6
}
