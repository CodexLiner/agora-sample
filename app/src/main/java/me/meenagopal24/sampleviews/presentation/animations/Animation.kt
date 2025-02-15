package me.meenagopal24.sampleviews.presentation.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

abstract class Animation {
    abstract fun apply(animatorSet: AnimatorSet, view: View ,  modify: (List<ObjectAnimator>) -> List<ObjectAnimator>)
}
