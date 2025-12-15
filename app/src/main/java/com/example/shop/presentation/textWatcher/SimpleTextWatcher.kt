package com.example.shop.presentation.textWatcher

import android.text.Editable
import android.text.TextWatcher

open class SimpleTextWatcher(
    private val before: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
    private val on: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
    private val after: ((Editable?) -> Unit)? = null,

) : TextWatcher {
    override fun afterTextChanged(p0: Editable?) {
        after?.invoke(p0)
    }

    override fun beforeTextChanged(
        p0: CharSequence?,
        p1: Int,
        p2: Int,
        p3: Int
    ) {
        before?.invoke(p0, p1, p2, p3)
    }

    override fun onTextChanged(
        p0: CharSequence?,
        p1: Int,
        p2: Int,
        p3: Int
    ) {
        on?.invoke(p0, p1, p2, p3)
    }
}
