package me.meenagopal24.sampleviews.presentation.animations

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnPause
import androidx.core.animation.doOnRepeat
import androidx.core.animation.doOnResume
import androidx.core.animation.doOnStart

fun View.animate(
    animation: Animation,
    onStart: (Animator) -> Unit = {},
    onEnd: (Animator) -> Unit = {},
    onRepeat: (Animator) -> Unit = {},
    onCancel: (Animator) -> Unit = {},
    onPause: (Animator) -> Unit = {},
    onResume: (Animator) -> Unit = {},
    modifyAnimators: (List<ObjectAnimator>) -> List<ObjectAnimator> = { it },
    configureAnimatorSet: AnimatorSet.() -> Unit = { duration = 1000 }
) {
    val animatorSet = AnimatorSet().apply {
        animation.apply(this, this@animate, modifyAnimators)
        configureAnimatorSet()
        doOnStart(action = onStart)
        doOnEnd(action = onEnd)
        doOnRepeat(action = onRepeat)
        doOnCancel(action = onCancel)
        doOnPause(action = onPause)
        doOnResume(action = onResume)
    }

    animatorSet.start()
}
