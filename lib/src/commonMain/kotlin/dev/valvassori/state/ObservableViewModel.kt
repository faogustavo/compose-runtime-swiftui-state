package dev.valvassori.state

import androidx.lifecycle.ViewModel
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("KotlinObservableViewModel", "KotlinObservableViewModel")
abstract class ObservableViewModel : ViewModel(), ObservableObject by BaseObservableObject()