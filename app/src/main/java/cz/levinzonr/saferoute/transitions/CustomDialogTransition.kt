package cz.levinzonr.saferoute.transitions

import androidx.compose.ui.window.DialogProperties
import cz.levinzonr.saferoute.core.transitions.DialogRouteTransition

object CustomDialogTransition : DialogRouteTransition() {
    override val properties: DialogProperties
        get() = DialogProperties(dismissOnBackPress = false)
}