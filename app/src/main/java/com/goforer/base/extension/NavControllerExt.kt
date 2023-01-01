package com.goforer.base.extension

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.safeNavigate(@IdRes resId: Int, direction: NavDirections) {
    val action = currentDestination?.getAction(direction.actionId) ?: graph.getAction(resId)

    if (currentDestination == null)
        navigateUp()

    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(direction)
    }
}

fun NavController.safeNavigate(
    @IdRes resId: Int,
    direction: NavDirections,
    navOptions: NavOptions?
) {
    val action = currentDestination?.getAction(direction.actionId) ?: graph.getAction(resId)

    if (currentDestination == null)
        navigateUp()

    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(direction, navOptions)
    }
}