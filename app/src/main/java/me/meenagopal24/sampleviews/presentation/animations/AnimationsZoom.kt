package me.meenagopal24.sampleviews.presentation.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup

object AnimationsZoom {
    private fun zoom(getProperties: (View) -> List<Pair<String, FloatArray>>): Animation {
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

    val In = zoom { _ ->
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "scaleX" to floatArrayOf(0.45f, 1f),
            "scaleY" to floatArrayOf(0.45f, 1f)
        )
    }
    val InDown = zoom { view ->
        val distance = view.top + view.height
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "scaleX" to floatArrayOf(0.1f, 0.475f, 1f),
            "scaleY" to floatArrayOf(0.1f, 0.475f, 1f),
            "translationY" to floatArrayOf(-distance.toFloat(), 0f)
        )
    }
    val InLeft = zoom { view ->
        val distance = view.left + view.width
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "scaleX" to floatArrayOf(0.1f, 0.475f, 1f),
            "scaleY" to floatArrayOf(0.1f, 0.475f, 1f),
            "translationX" to floatArrayOf(-distance.toFloat(), 0f)
        )
    }
    val InRight = zoom { view ->
        val distance = (view.parent as ViewGroup).width - view.left
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "scaleX" to floatArrayOf(0.1f, 0.475f, 1f),
            "scaleY" to floatArrayOf(0.1f, 0.475f, 1f),
            "translationX" to floatArrayOf(distance.toFloat(), 0f)
        )
    }
    val InUp = zoom { view ->
        val distance = (view.parent as ViewGroup).height - view.top
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "scaleX" to floatArrayOf(0.1f, 0.475f, 1f),
            "scaleY" to floatArrayOf(0.1f, 0.475f, 1f),
            "translationY" to floatArrayOf(distance.toFloat(), 0f)
        )
    }
    val Out = zoom { _ ->
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "scaleX" to floatArrayOf(1f, 0.45f),
            "scaleY" to floatArrayOf(1f, 0.45f)
        )
    }
    val OutDown = zoom { view ->
        val distance = (view.parent as ViewGroup).height - view.top
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "scaleX" to floatArrayOf(1f, 0.475f, 0.1f),
            "scaleY" to floatArrayOf(1f, 0.475f, 0.1f),
            "translationY" to floatArrayOf(0f, distance.toFloat())
        )
    }
    val OutLeft = zoom { view ->
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "scaleX" to floatArrayOf(1f, 0.475f, 0.1f),
            "scaleY" to floatArrayOf(1f, 0.475f, 0.1f),
            "translationX" to floatArrayOf(0f, -view.right.toFloat())
        )
    }
    val OutRight = zoom { view ->
        val distance = (view.parent as ViewGroup).width - view.left
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "scaleX" to floatArrayOf(1f, 0.475f, 0.1f),
            "scaleY" to floatArrayOf(1f, 0.475f, 0.1f),
            "translationX" to floatArrayOf(0f, distance.toFloat())
        )
    }
    val OutUp = zoom { view ->
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "scaleX" to floatArrayOf(1f, 0.475f, 0.1f),
            "scaleY" to floatArrayOf(1f, 0.475f, 0.1f),
            "translationY" to floatArrayOf(0f, -view.bottom.toFloat())
        )
    }
}
