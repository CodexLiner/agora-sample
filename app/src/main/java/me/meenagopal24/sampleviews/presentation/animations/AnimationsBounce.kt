package me.meenagopal24.sampleviews.presentation.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

object AnimationsBounce {
    private fun bounce(getProperties: (View) -> List<Pair<String, FloatArray>>): Animation {
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

    val In = bounce {
        listOf(
            "alpha" to floatArrayOf(0f, 1f, 1f, 1f),
            "scaleX" to floatArrayOf(0.3f, 1.05f, 0.9f, 1f),
            "scaleY" to floatArrayOf(0.3f, 1.05f, 0.9f, 1f)
        )
    }

    val InLeft = bounce { view ->
        val width = -view.width.toFloat()
        listOf(
            "translationX" to floatArrayOf(width, 30f, -10f, 0f),
            "alpha" to floatArrayOf(0f, 1f, 1f, 1f)
        )
    }

    val InRight = bounce { view ->
        val width = view.width.toFloat() + view.measuredWidth
        listOf(
            "translationX" to floatArrayOf(width, -30f, 10f, 0f),
            "alpha" to floatArrayOf(0f, 1f, 1f, 1f)
        )
    }

    val InUp = bounce { view ->
        val height = view.measuredHeight.toFloat()
        listOf(
            "translationY" to floatArrayOf(height, -30f, 10f, 0f),
            "alpha" to floatArrayOf(0f, 1f, 1f, 1f)
        )
    }

    val InDown = bounce { view ->
        val height = -view.height.toFloat()
        listOf(
            "translationY" to floatArrayOf(height, 30f, -10f, 0f),
            "alpha" to floatArrayOf(0f, 1f, 1f, 1f)
        )
    }
}
