package io.github.llh4github.ksas.dbmodel.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Constraint(validatedBy = [NoSameWithIdValidator::class])
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class NoSameWithId(
    val message: String = "父级ID不能与ID相同",
    val idField: String = "id",
    val parentIdField: String = "parentId",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class NoSameWithIdValidator : ConstraintValidator<NoSameWithId, Any> {
    private lateinit var idField: String
    private lateinit var parentField: String

    override fun initialize(constraintAnnotation: NoSameWithId) {
        this.idField = constraintAnnotation.idField
        this.parentField = constraintAnnotation.parentIdField
    }

    @Suppress(names = ["UNCHECKED_CAST"])
    override fun isValid(value: Any, context: ConstraintValidatorContext?): Boolean {
        val idField = value::class.memberProperties.find { it.name == idField } ?: return true
        // 类型推断有问题，需要强转
        val idFieldValue = (idField as KProperty1<Any, *>).get(value)
        val parentField = value::class.memberProperties.find { it.name == parentField }
            ?: return true
        val parentFieldValue = (parentField as KProperty1<Any, *>).get(value)

        val isValid = idFieldValue != parentFieldValue
        if (!isValid) {
            context?.disableDefaultConstraintViolation()
            context?.buildConstraintViolationWithTemplate("父级ID不能与ID相同")
                ?.addPropertyNode(parentField.name)
                ?.addConstraintViolation()
        }
        return isValid
    }
}
