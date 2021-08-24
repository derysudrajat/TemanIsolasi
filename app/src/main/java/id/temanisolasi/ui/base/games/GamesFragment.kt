package id.temanisolasi.ui.base.games

import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.squareup.seismic.ShakeDetector
import id.temanisolasi.R
import id.temanisolasi.databinding.FragmentGameBinding
import id.temanisolasi.utils.Helpers.hideView
import id.temanisolasi.utils.Helpers.showView
import org.koin.androidx.viewmodel.ext.android.viewModel

class GamesFragment : Fragment(), ShakeDetector.Listener, GamesListener {

    private lateinit var mediaPlayer: MediaPlayer
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val model: GamesViewModel by viewModel()
    private var onAnimated = false
    private var isHaveRef = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpShake()
        model.getFavoriteGames()

        with(binding) {
            animationView.setOnClickListener {
                randomGames()
                playAnimation()
            }

            animationView.addAnimatorUpdateListener {
                if (it.animatedFraction >= 0.6f) {
                    onAnimated = false
                    animationView.pauseAnimation()
                    animationView.isEnabled = false
                    controlResult(isHide = false)
                }
            }

            btnBack.setOnClickListener {
                animationView.frame = 0
                animationView.pauseAnimation()
                animationView.isEnabled = true
                controlResult(true)
                controlMain(false)
            }

            btnTryAgain.setOnClickListener {
                stopSound()
                randomGames()
                playAnimation()
            }

            model.favGames.observe(viewLifecycleOwner) {
                rvGames.apply {
                    itemAnimator = DefaultItemAnimator()
                    adapter = GamesAdapter(this@GamesFragment, it)
                }
            }
        }
    }

    fun setUpShake() {
        val sensorManager =
            requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.start(sensorManager)
    }

    private fun playAnimation() = with(binding) {
        onAnimated = true
        animationView.playAnimation()
        playSound()
        animationView.isEnabled = false
        controlMain(true)
        controlResult(false)
        controlResult(isHide = true, isInvisible = true)
    }

    fun playSound() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.open_box)
        mediaPlayer.start()
    }

    fun stopSound() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    fun controlResult(isHide: Boolean, isInvisible: Boolean? = false) = with(binding) {
        if (!isHide) {
            if (isHaveRef) btnAction.showView() else btnAction.hideView()
        } else btnAction.hideView(isHaveRef)

        listOf(ivQuiz, tvQuiz, btnBack, btnTryAgain)
            .forEach { if (isHide) it.hideView(isInvisible) else it.showView() }
    }

    fun controlMain(isHide: Boolean) = with(binding) {
        listOf(tvTitle, tvDesc, tvPopular, rvGames)
            .forEach { if (isHide) it.hideView() else it.showView() }
    }

    fun randomGames(fav: Int? = -1) = model.getRandomGames(fav) { games ->
        with(binding) {
            tvQuiz.text = games.games
            isHaveRef = games.type == 1
            if (isHaveRef) btnAction.apply {
                text = games.button
                setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(games.url)))
                }
            }
        }
    }

    override fun hearShake() {
        if (!onAnimated) {
            randomGames()
            playAnimation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSound()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = GamesFragment()
    }

    override fun onSelected(fav: Int) {
        randomGames(fav)
        playAnimation()
    }
}