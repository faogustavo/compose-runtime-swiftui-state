package dev.valvassori.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.structuralEqualityPolicy
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
import androidx.compose.runtime.MutableState as ComposeMutableState

class BaseObservableObject : ObservableObject {
    internal val changeTrackingTrigger: ObservableObject.ChangeTrackingTrigger = ObservableObject.ChangeTrackingTrigger()
    override val changeTracking: ObservableObject.ChangeTracking = changeTrackingTrigger

    override fun <T> mutableStateOf(
        value: T,
        policy: SnapshotMutationPolicy<T>
    ): MutableState<T> = ObservableMutableStateImpl(value, policy, changeTrackingTrigger)
}

@OptIn(ExperimentalObjCName::class)
@ObjCName("KotlinObservableObject", "KotlinObservableObject")
interface ObservableObject {
    val changeTracking: ChangeTracking

    fun <T> mutableStateOf(
        value: T,
        policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    ): ComposeMutableState<T>

    interface ChangeTracking {
        fun addListener(listener: Listener): CancellationToken

        fun addWillChangeObserver(observer: () -> Unit): CancellationToken = addListener(object: Listener {
            override fun onObjectWillChange() {
                observer()
            }
        })

        fun addDidChangeObserver(observer: () -> Unit): CancellationToken = addListener(object: Listener {
            override fun onObjectDidChange() {
                observer()
            }
        })

        fun removeListener(listener: Listener)

        interface Listener {
            fun onObjectWillChange() { }
            fun onObjectDidChange() { }
        }
    }

    class ChangeTrackingTrigger: ChangeTracking, ChangeTracking.Listener {
        // Make Weak to prevent leaks
        private val listeners = mutableSetOf<ChangeTracking.Listener>()

        override fun addListener(listener: ChangeTracking.Listener): CancellationToken {
            listeners.add(listener)
            return { removeListener(listener) }
        }

        override fun removeListener(listener: ChangeTracking.Listener) {
            listeners.remove(listener)
        }

        fun notifyObjectWillChange(): Unit = listeners.forEach {
            it.onObjectWillChange()
        }

        fun notifyObjectDidChange(): Unit = listeners.forEach {
            it.onObjectDidChange()
        }

        override fun onObjectWillChange() {
            notifyObjectWillChange()
        }

        override fun onObjectDidChange() {
            notifyObjectDidChange()
        }
    }
}

typealias CancellationToken = () -> Unit