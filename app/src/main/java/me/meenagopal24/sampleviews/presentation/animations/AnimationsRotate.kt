package me.meenagopal24.sampleviews.presentation.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

object AnimationsRotate {
    private fun rotate(getProperties: (View) -> List<Pair<String, FloatArray>>): Animation {
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

    val In = rotate {
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "rotation" to floatArrayOf(-200f, 0f)
        )
    }
    val InDownLeft = rotate { view ->
        val x = view.paddingLeft.toFloat()
        val y = (view.height - view.paddingBottom).toFloat()
        listOf(
            "rotation" to floatArrayOf(-90f, 0f),
            "alpha" to floatArrayOf(0f, 1f),
            "pivotX" to floatArrayOf(x, x),
            "pivotY" to floatArrayOf(y, y)
        )
    }
    val InDownRight = rotate { view ->
        val x = (view.width - view.paddingRight).toFloat()
        val y = (view.height - view.paddingBottom).toFloat()
        listOf(
            "rotation" to floatArrayOf(90f, 0f),
            "alpha" to floatArrayOf(0f, 1f),
            "pivotX" to floatArrayOf(x, x),
            "pivotY" to floatArrayOf(y, y)
        )
    }
    val InUpLeft = rotate { view ->
        val x = view.paddingLeft.toFloat()
        val y = (view.height - view.paddingBottom).toFloat()
        listOf(
            "rotation" to floatArrayOf(90f, 0f),
            "alpha" to floatArrayOf(0f, 1f),
            "pivotX" to floatArrayOf(x, x),
            "pivotY" to floatArrayOf(y, y)
        )
    }
    val InUpRight = rotate { view ->
        val x = (view.width - view.paddingRight).toFloat()
        val y = (view.height - view.paddingBottom).toFloat()
        listOf(
            "rotation" to floatArrayOf(-90f, 0f),
            "alpha" to floatArrayOf(0f, 1f),
            "pivotX" to floatArrayOf(x, x),
            "pivotY" to floatArrayOf(y, y)
        )
    }
    val Out = rotate {
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "rotation" to floatArrayOf(0f, 200f)
        )
    }
    val OutDownLeft = rotate { view ->
        val x = view.paddingLeft.toFloat()
        val y = (view.height - view.paddingBottom).toFloat()
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "rotation" to floatArrayOf(0f, 90f),
            "pivotX" to floatArrayOf(x, x),
            "pivotY" to floatArrayOf(y, y)
        )
    }
    val OutDownRight = rotate { view ->
        val x = (view.width - view.paddingRight).toFloat()
        val y = (view.height - view.paddingBottom).toFloat()
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "rotation" to floatArrayOf(0f, -90f),
            "pivotX" to floatArrayOf(x, x),
            "pivotY" to floatArrayOf(y, y)
        )
    }
    val OutUpLeft = rotate { view ->
        val x = view.paddingLeft.toFloat()
        val y = (view.height - view.paddingBottom).toFloat()
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "rotation" to floatArrayOf(0f, -90f),
            "pivotX" to floatArrayOf(x, x),
            "pivotY" to floatArrayOf(y, y)
        )
    }
    val OutUpRight = rotate { view ->
        val x = (view.width - view.paddingRight).toFloat()
        val y = (view.height - view.paddingBottom).toFloat()
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "rotation" to floatArrayOf(0f, 90f),
            "pivotX" to floatArrayOf(x, x),
            "pivotY" to floatArrayOf(y, y)
        )
    }
}