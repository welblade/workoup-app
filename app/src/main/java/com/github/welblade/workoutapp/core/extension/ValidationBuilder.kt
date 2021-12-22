package com.github.welblade.workoutapp.core.extension

import io.konform.validation.Constraint
import io.konform.validation.ValidationBuilder

fun ValidationBuilder<String>.email(): Constraint<String> {
    val pattern = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}"
    return addConstraint(
        "must be a valid email",
        pattern
    ) {
        it.matches(pattern.toRegex())
    }
}