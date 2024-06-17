package dev.valvassori.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.mutableStateOf

internal class ObservableMutableStateImpl<T> internal constructor(
    initialValue: T,
    private val policy: SnapshotMutationPolicy<T>,
    private val changeTracking: ObservableObject.ChangeTrackingTrigger,
) : MutableState<T> {
    private val delegate = mutableStateOf(initialValue, policy)

    // Compose MutableState methods
    override fun component1(): T = delegate.component1()

    override fun component2(): (T) -> Unit = delegate.component2()

    override var value: T
        get() = delegate.value
        set(value) {
            val oldValue = delegate.value

            // Check if not the same
            if (policy.equivalent(oldValue, value)) return

            // Notify and update
            changeTracking.notifyObjectWillChange()
            delegate.value = value
            changeTracking.notifyObjectDidChange()
        }

}
