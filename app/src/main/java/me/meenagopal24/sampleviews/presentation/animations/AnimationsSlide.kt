package me.meenagopal24.sampleviews.presentation.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup

object AnimationsSlide {
    private fun slide(getProperties: (View) -> List<Pair<String, FloatArray>>): Animation {
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

    val InDown = slide { view ->
        val distance = view.top + view.height
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "translationY" to floatArrayOf(-distance.toFloat(), 0f)
        )
    }
    val InLeft = slide { view ->
        val distance = (view.parent as ViewGroup).width - view.left
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "translationX" to floatArrayOf(-distance.toFloat(), 0f)
        )
    }
    val InRight = slide { view ->
        val distance = (view.parent as ViewGroup).width - view.left
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "translationX" to floatArrayOf(distance.toFloat(), 0f)
        )
    }
    val InUp = slide { view ->
        val distance = (view.parent as ViewGroup).height - view.top
        listOf(
            "alpha" to floatArrayOf(0f, 1f),
            "translationY" to floatArrayOf(distance.toFloat(), 0f)
        )
    }
    val OutDown = slide { view ->
        val distance = (view.parent as ViewGroup).height - view.top
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "translationY" to floatArrayOf(0f, distance.toFloat())
        )
    }
    val OutLeft = slide { view ->
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "translationX" to floatArrayOf(0f, -view.right.toFloat())
        )
    }
    val OutRight = slide { view ->
        val distance = (view.parent as ViewGroup).width - view.left
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "translationX" to floatArrayOf(0f, distance.toFloat())
        )
    }
    val OutUp = slide { view ->
        listOf(
            "alpha" to floatArrayOf(1f, 0f),
            "translationY" to floatArrayOf(0f, -view.bottom.toFloat())
        )
    }
}
