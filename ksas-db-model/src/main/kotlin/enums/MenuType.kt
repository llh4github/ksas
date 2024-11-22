package io.github.llh4github.ksas.dbmodel.enums

import org.babyfish.jimmer.sql.EnumType

/**
 * 定义顺序很重要
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class MenuType {

    /**
     * 菜单
     */
    MENU,

    /**
     * iframe
     */
    IFrame,

    /**
     * 外链
     */
    Link,

    /**
     * 按钮
     */
    Button
}