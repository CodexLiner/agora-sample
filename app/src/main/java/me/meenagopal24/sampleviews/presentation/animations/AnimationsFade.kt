package me.meenagopal24.sampleviews.presentation.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View


object AnimationsFade {
    private fun fade(getProperties: (View) -> List<Pair<String, FloatArray>> = { listOf() }) =
        object : Animation() {
            override fun apply(
                animatorSet: AnimatorSet,
                view: View,
                modify: (List<ObjectAnimator>) -> List<ObjectAnimator>
            ) {
                val animators = getProperties(view).map { (property, values) ->
                    ObjectAnimator.ofFloat(
                        view,
                        property,
                        *values
                    )
                }
                animatorSet.playTogether(modify(animators))
            }
        }

    val In = fade { listOf("alpha" to floatArrayOf(0f, 1f)) }
    val InLeft = fade { view ->
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "translationX" to floatArrayOf((-view.width / 4).toFloat(), 0f)
        )
    }
    val InRight = fade { view ->
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "translationX" to floatArrayOf((view.width / 4).toFloat(), 0f)
        )
    }
    val InUp = fade { view ->
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "translationY" to floatArrayOf((view.height / 4).toFloat(), 0f)
        )
    }
    val InDown = fade { view ->
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "translationY" to floatArrayOf((-view.height / 4).toFloat(), 0f) // Fixed
        )
    }
    val Out = fade {
        listOf("alpha" to floatArrayOf(1f, 0f))
    }
    val OutLeft = fade { view ->
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "translationX" to floatArrayOf(0f, (-view.width / 4).toFloat())
        )
    }
    val OutRight = fade { view ->
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "translationX" to floatArrayOf(0f, (view.width / 4).toFloat())
        )
    }
    val OutUp = fade { view ->
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "translationY" to floatArrayOf(0f, (-view.height / 4).toFloat())
        )
    }
    val OutDown = fade { view ->
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "translationY" to floatArrayOf(0f, (view.height / 4).toFloat())
        )
    }
}
