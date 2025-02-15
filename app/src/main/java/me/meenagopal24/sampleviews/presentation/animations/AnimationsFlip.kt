package me.meenagopal24.sampleviews.presentation.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

object AnimationsFlip {
    private fun flip(getProperties: (View) -> List<Pair<String, FloatArray>>): Animation {
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

    val InX = flip {
        listOf(
            "alpha" to floatArrayOf(0.25f, 0.5f, 0.75f, 1f),
            "rotationX" to floatArrayOf(90f, -15f, 15f, 0f)
        )
    }

    val InY = flip {
        listOf(
            "alpha" to floatArrayOf(0.25f, 0.5f, 0.75f, 1f),
            "rotationY" to floatArrayOf(90f, -15f, 15f, 0f)
        )
    }


    val OutX = flip {
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "rotationX" to floatArrayOf(0f, 90f)
        )
    }

    val OutY = flip {
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "rotationY" to floatArrayOf(0f, 90f)
        )
    }
}
