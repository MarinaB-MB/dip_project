package com.deadely.it_lingua.utils

import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.Disposable

inline fun validateInput(
    inputLayout: TextInputLayout,
    inputView: TextView,
    crossinline body: () -> Unit
): Disposable {
    return RxView.focusChanges(inputView)
        .skipInitialValue()
        .map {
            if (!it) {
                body()
            }
            return@map it
        }
        .flatMap { hasFocus ->
            return@flatMap RxTextView.textChanges(inputView)
                .skipInitialValue()
                .map {
                    if (hasFocus && inputLayout.isErrorEnabled) inputLayout.error = null
                }
                .skipWhile { hasFocus }
                .doOnEach { body() }
        }
        .subscribe { }
}
