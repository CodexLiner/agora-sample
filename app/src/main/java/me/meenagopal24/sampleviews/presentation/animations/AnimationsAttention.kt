package me.meenagopal24.sampleviews.presentation.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

object AnimationsAttention {
    private fun attention(getProperties: (View) -> List<Pair<String, FloatArray>>): Animation {
        return object : Animation() {
            override fun apply(
                animatorSet: AnimatorSet,
                view: View,
                modify: (List<ObjectAnimator>) -> List<ObjectAnimator>
            ) {
                val animators = getProperties(view).map { (property, values) ->
                    ObjectAnimator.ofFloat(view, property, *values)
                }
                animatorSet.playTogether(modify(animators))
            }
        }
    }

    val Bounce = attention { listOf("translationY" to floatArrayOf(0f, 0f, -30f, 0f, -15f, 0f, 0f)) }

    val Flash = attention { listOf("alpha" to floatArrayOf(1f, 0f, 1f, 0f, 1f)) }

    val Pulse = attention {
        listOf(
            "scaleY" to floatArrayOf(1f, 1.1f, 1f),
            "scaleX" to floatArrayOf(1f, 1.1f, 1f)
        )
    }

    val RubberBand = attention {
        listOf(
            "scaleX" to floatArrayOf(1f, 1.25f, 0.75f, 1.15f, 1f),
            "scaleY" to floatArrayOf(1f, 0.75f, 1.25f, 0.85f, 1f)
        )
    }

    val Shake = attention { listOf("translationX" to floatArrayOf(0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)) }

    val StandUp = attention { view ->
        val x = (view.width - view.paddingLeft - view.paddingRight) / 2f + view.paddingLeft
        val y = view.height - view.paddingBottom.toFloat()
        listOf(
            "pivotX" to floatArrayOf(x, x, x, x, x),
            "pivotY" to floatArrayOf(y, y, y, y, y),
            "rotationX" to floatArrayOf(55f, -30f, 15f, -15f, 0f)
        )
    }

    val Swing = attention { listOf("rotation" to floatArrayOf(0f, 10f, -10f, 6f, -6f, 3f, -3f, 0f)) }

    val Tada = attention {
        listOf(
            "scaleX" to floatArrayOf(1f, 0.9f, 0.9f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1f),
            "scaleY" to floatArrayOf(1f, 0.9f, 0.9f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1f),
            "rotation" to floatArrayOf(0f, -3f, -3f, 3f, -3f, 3f, -3f, 3f, -3f, 0f)
        )
    }

    val Wave = attention { view ->
        val x = (view.width - view.paddingLeft - view.paddingRight) / 2f + view.paddingLeft
        val y = view.height - view.paddingBottom.toFloat()
        listOf(
            "rotation" to floatArrayOf(12f, -12f, 3f, -3f, 0f),
            "pivotX" to floatArrayOf(x, x, x, x, x),
            "pivotY" to floatArrayOf(y, y, y, y, y)
        )
    }

    val Wobble = attention { view ->
        val width = view.width.toFloat()
        val one = width / 100f
        val x = 0f * one
        listOf(
            "translationX" to floatArrayOf(x, -25 * one, 20 * one, -15 * one, 10 * one, -5 * one, x, 0f),
            "rotation" to floatArrayOf(0f, -5f, 3f, -3f, 2f, -1f, 0f)
        )
    }
}
