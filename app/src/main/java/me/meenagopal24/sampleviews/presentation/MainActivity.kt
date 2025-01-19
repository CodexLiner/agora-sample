package me.meenagopal24.sampleviews.presentation

import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import me.meenagopal24.sampleviews.databinding.ActivityMainBinding

private const val PERMISSION_REQ_ID_RECORD_AUDIO = 22
private const val PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1

private const val APP_ID = "383a933cb1fd4243ae96617c70ab8d32"
private const val TOKEN =
    "007eJxTYLj/SG5dZLCLytnfynmn0qK3Ja395+qWwb0jPPb23j9bZA4rMBhbGCdaGhsnJxmmpZgYmRgnplqamRmaJ5sbJCZZpBgbTV3Uk94QyMhwdvEmVkYGCATxuRlKUotLnDMS8/JScxgYALGlJCM="


private var mRtcEngine: RtcEngine? = null

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mRtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserMuteVideo(uid: Int, muted: Boolean) {
            super.onUserMuteVideo(uid, muted)
            runOnUiThread {
                binding.remoteVideoViewContainer.visibility = if (muted) View.GONE else View.VISIBLE
            }

        }

        override fun onUserJoined(uid: Int, elapsed: Int) = runOnUiThread { setupRemoteVideo(uid) }

        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) =
            runOnUiThread {
                Toast.makeText(this@MainActivity, "Joined channel: $channel with UID: $uid", Toast.LENGTH_SHORT).show()
            }

        override fun onError(err: Int) = runOnUiThread {
            Toast.makeText(this@MainActivity, "Error: $err", Toast.LENGTH_SHORT).show()
        }
    }

    private var isJoined = false
    private var isFrontCamera = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val padding = insets.getInsets(WindowInsetsCompat.Type.ime())
            view.updatePadding(bottom = padding.bottom, left = padding.left, right = padding.right, top = padding.top)
            insets
        }

        setContentView(binding.root)

        if (checkPermissions()) {
            setupUI()
        }
    }

    private fun setupUI() {
        binding.apply {
            joinCallButton.setOnClickListener { joinCall() }
            switchCameraButton.setOnClickListener { switchCamera() }
            endCallButton.setOnClickListener { endCall() }
            muteButton.setOnClickListener { muteLocalAudio(true) }
            unMuteButton.setOnClickListener { muteLocalAudio(false) }
            enableCameraButton.setOnClickListener { enableCamera(true) }
            disableCameraButton.setOnClickListener { enableCamera(false) }
            remoteVideoViewContainer.setOnClickListener { switchFrames() }
        }
    }

    private fun checkPermissions(): Boolean {
        return checkSelfPermission(android.Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) &&
               checkSelfPermission(android.Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)
    }

    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        return if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.CAMERA), requestCode
            )
            false
        } else true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupUI()
        } else {
            Toast.makeText(this@MainActivity, "Permission denied!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeAndJoinChannel() {
        runCatching {
            mRtcEngine = RtcEngine.create(baseContext, APP_ID, mRtcEventHandler).apply {
                enableVideo()
            }

            val localFrame = RtcEngine.CreateRendererView(baseContext)
            binding.localVideoViewContainer.addView(localFrame)
            mRtcEngine?.setupLocalVideo(VideoCanvas(localFrame, VideoCanvas.RENDER_MODE_FIT, 0))

            mRtcEngine?.joinChannel(TOKEN, binding.channelIdInput.text.toString().ifBlank { "testChannel" }, null, 0)
            isJoined = true
            updateUIForJoinedCall()
        }
    }

    private fun setupRemoteVideo(uid: Int) {
        val remoteFrame = RtcEngine.CreateRendererView(baseContext)
        remoteFrame.setZOrderMediaOverlay(true)
        binding.remoteVideoViewContainer.addView(remoteFrame)
        mRtcEngine?.setupRemoteVideo(VideoCanvas(remoteFrame, VideoCanvas.RENDER_MODE_FIT, uid))
        switchFrames()
    }

    private fun switchCamera() {
        if (isJoined) {
            mRtcEngine?.switchCamera()
            isFrontCamera = !isFrontCamera
        }
    }

    private fun enableCamera(enable: Boolean) {
        binding.localVideoViewContainer.visibility = if (enable) View.VISIBLE else View.GONE
        mRtcEngine?.muteLocalVideoStream(!enable)
        binding.enableCameraButton.visibility = if (enable) View.GONE else View.VISIBLE
        binding.disableCameraButton.visibility = if (enable) View.VISIBLE else View.GONE
    }

    private fun endCall() {
        if (isJoined) {
            mRtcEngine?.leaveChannel()
            isJoined = false
            updateUIForEndCall()
        }
    }

    private fun muteLocalAudio(mute: Boolean) {
        mRtcEngine?.muteLocalAudioStream(mute)
        binding.muteButton.visibility = if (mute) View.GONE else View.VISIBLE
        binding.unMuteButton.visibility = if (mute) View.VISIBLE else View.GONE
    }

    private fun joinCall() {
        if (!isJoined) {
            initializeAndJoinChannel()
        } else {
            Toast.makeText(this, "Already in a call", Toast.LENGTH_SHORT).show()
        }
    }

    private fun switchFrames() {
        val localParams = binding.localVideoViewContainer.layoutParams
        val remoteParams = binding.remoteVideoViewContainer.layoutParams

        binding.localVideoViewContainer.layoutParams = remoteParams
        binding.remoteVideoViewContainer.layoutParams = localParams

        binding.localVideoViewContainer.elevation = if (remoteParams.height > localParams.height) 1f else 0f
        binding.remoteVideoViewContainer.elevation = if (remoteParams.height > localParams.height) 0f else 1f

        binding.localVideoViewContainer.requestLayout()
        binding.remoteVideoViewContainer.requestLayout()
    }

    private fun updateUIForJoinedCall() {
        binding.apply {
            joinCallButton.visibility = View.GONE
            endCallButton.visibility = View.VISIBLE
            binding.channelIdInput.visibility = View.GONE
            verticalButtonContainer.visibility = View.VISIBLE
        }
    }

    private fun updateUIForEndCall() {
        binding.apply {
            binding.channelIdInput.visibility = View.VISIBLE
            joinCallButton.visibility = View.VISIBLE
            endCallButton.visibility = View.GONE
            localVideoViewContainer.removeAllViews()
            remoteVideoViewContainer.removeAllViews()
            verticalButtonContainer.visibility = View.INVISIBLE
        }
        Toast.makeText(this, "Call Ended!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine?.leaveChannel()
        RtcEngine.destroy()
        mRtcEngine = null
    }
}
