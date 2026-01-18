package com.example.shop.utils

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import kotlin.math.min

class CustomProgress(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paintOutlinedCircle = Paint().apply {
        color = Color.RED
        strokeWidth = 1f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }
    private val paintInlinedCircle = Paint().apply {
        color = Color.RED
        strokeWidth = 1f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val paintPartCircle = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 10f
        isAntiAlias = true
    }

    private val paintText = Paint().apply {
        textSize = 40f
        textAlign = Paint.Align.CENTER
        color = Color.BLACK
        isAntiAlias = true
    }

    private var widthOut = 0
    private var heightOut = 0
    private var widthIn = 0

    var progress: Float = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate()
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        widthOut = w
        heightOut = h
        widthIn = w - 40
        paintText.textSize = (min(w, h) * 0.1f).coerceIn(20f, 60f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = min(width, height)

        setMeasuredDimension(size, size)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (widthOut == 0 || heightOut == 0) {
            onSizeChanged(width, height, 0, 0)
        }

        drawIndicator(canvas, progress, emptyColor = Color.GRAY, progressColor = Color.CYAN)
    }


    fun drawIndicator(
        canvas: Canvas,
        progress: Float,
        segmentCount: Int = 40,
        gap: Float = 10f,
        emptyColor: Int = Color.GRAY,
        progressColor: Int = Color.GREEN,
        textSize: Float = paintText.textSize, // Используем текущий размер текста
        textColor: Int = Color.BLACK,
    ) {
        if (widthOut == 0 || heightOut == 0) return

        val cx = widthOut / 2f
        val cy = heightOut / 2f
        val radiusOut = (widthOut - 10) / 2f

        val radiusIn = (widthIn - 10) / 2f

//        canvas.drawCircle(cx, cy, radiusOut, paintOutlinedCircle)
//        canvas.drawCircle(cx, cy, radiusIn, paintInlinedCircle)

        val field = RectF(
            cx - (radiusOut + radiusIn) / 2,
            cy - (radiusOut + radiusIn) / 2,
            cx + (radiusOut + radiusIn) / 2,
            cy + (radiusOut + radiusIn) / 2
        )

        val perPartSize = 360f / segmentCount

        val filledCircle = progress * segmentCount

        paintText.textSize = textSize
        paintText.color = textColor

        val percentText = "${((filledCircle / segmentCount) * 100).toInt()}%"

        val textBounds = Rect()
        paintText.getTextBounds(percentText, 0, percentText.length, textBounds)
        val textY = cy + (textBounds.height() / 2) - textBounds.bottom
        canvas.drawText(percentText, cx, textY, paintText)

        for (i in 0 until segmentCount) {
            val start = i * perPartSize
            val sweep = perPartSize - gap
            val p = paintPartCircle

            if (i < filledCircle) {
                p.color = progressColor
            } else {
                p.color = emptyColor
            }
            canvas.drawArc(field, start, sweep, false, p)
        }
    }

    private var animator: ValueAnimator? = null

    fun startCustomAnimation(
        durationAnim: Long = 3000,
        interpolatorAnim: Interpolator = AccelerateInterpolator()
    ) {
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = durationAnim
            interpolator = interpolatorAnim
            addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float
                this@CustomProgress.progress = animatedValue
            }
            start()
        }
    }


    fun stopCustomAnimation() {
        animator?.cancel()
        animator = null
    }


}


