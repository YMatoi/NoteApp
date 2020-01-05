package com.github.ymatoi.note.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T1, T2, S> combine(
    sourceA: LiveData<T1>,
    sourceB: LiveData<T2>,
    func: (T1?, T2?) -> S?
): LiveData<S> = MediatorLiveData<S>().apply {
    addSource(sourceA) { postValue(func(sourceA.value, sourceB.value)) }
    addSource(sourceB) { postValue(func(sourceA.value, sourceB.value)) }
}
