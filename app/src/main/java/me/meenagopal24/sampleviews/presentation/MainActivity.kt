package me.meenagopal24.sampleviews.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import me.meenagopal24.sampleviews.databinding.ActivityMainBinding
import me.meenagopal24.sampleviews.presentation.animations.AnimationsBounce
import me.meenagopal24.sampleviews.presentation.animations.animate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            binding.view.animate(AnimationsBounce.InLeft){
                duration = 10000
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val padding = insets.getInsets(WindowInsetsCompat.Type.ime())
            view.updatePadding(bottom = padding.bottom, left = padding.left, right = padding.right, top = padding.top)
            insets
        }
    }
}
