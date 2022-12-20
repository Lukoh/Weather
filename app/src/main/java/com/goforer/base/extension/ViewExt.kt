package com.goforer.base.extension

import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.RelativeSizeSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

const val RECYCLER_VIEW_CACHE_SIZE = 21

fun Window.setSystemBarTextDark() {
    val view = findViewById<View>(android.R.id.content)
    val flags = view.systemUiVisibility
    view.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}


fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }

    return this
}

/**
 * Hide the view. (visibility = View.INVISIBLE)
 */
fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }

    return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.gone(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }

    return this
}

fun View.upShow(duration: Long = 500L) {
    show()
    translationY = height.toFloat()
    animate()
        .setDuration(duration)
        .translationY(0f)
        .setListener(object : AnimatorListenerAdapter() {
        })
        .start()
}

fun TextView.setSpans(proportion: Float = 1.05F, spanMap: MutableMap<String, ClickableSpan>) {
    val tvt = text.toString()
    val ssb = SpannableStringBuilder(tvt)

    spanMap.forEach {
        val key = it.key
        val value = it.value

        if (tvt.contains(key, true)) {
            val start = tvt.indexOf(key, 0, true)
            val end = start + key.length

            ssb.setSpan(value, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            ssb.setSpan(
                RelativeSizeSpan(proportion),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    setText(ssb, TextView.BufferType.SPANNABLE)
    movementMethod = LinkMovementMethod.getInstance()
}

fun TextView.setTextUnderline() {
    val content = SpannableString(this.text.toString())

    content.setSpan(UnderlineSpan(), 0, content.length, 0)
    this.text = content
}

fun getHighlightSpanMap(
    textToHighlight: String,
    color: Int?,
    typeface: Typeface?,
    isUnderlineText: Boolean,
    bgColor: Int? = null,
    textSize: Float? = null,
    onClickListener: (textView: View) -> Unit
): MutableMap<String, ClickableSpan> {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(textView: View) {
            onClickListener(textView)
        }

        override fun updateDrawState(textPaint: TextPaint) {
            super.updateDrawState(textPaint)

            bgColor?.let {
                textPaint.textAlign = Paint.Align.LEFT
                textPaint.bgColor = it
            }

            textSize?.let {
                textPaint.textSize = it
            }

            color?.let {
                textPaint.color = it
            }

            typeface?.let {
                textPaint.typeface = it
            }

            textPaint.isUnderlineText = isUnderlineText
        }
    }

    return mutableMapOf(Pair(textToHighlight, clickableSpan))
}