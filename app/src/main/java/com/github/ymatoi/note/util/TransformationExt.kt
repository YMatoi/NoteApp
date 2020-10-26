package com.github.ymatoi.note.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <S1, S2, T> combine(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    func: (S1?, S2?) -> T?
): LiveData<T> = MediatorLiveData<T>().apply {
    addSource(source1) { postValue(func(source1.value, source2.value)) }
    addSource(source2) { postValue(func(source1.value, source2.value)) }
}
