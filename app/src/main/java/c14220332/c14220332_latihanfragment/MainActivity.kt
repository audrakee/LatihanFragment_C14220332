package c14220332.c14220332_latihanfragment

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private var finalScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGame = findViewById<Button>(R.id.btnGame)
        val btnScore = findViewById<Button>(R.id.btnScore)
        val btnSettings = findViewById<Button>(R.id.btnSettings)

        // Set onClickListener untuk tombol navigasi
        btnGame.setOnClickListener {
            resetGameAndReturnToGameFragment()
        }
        btnSettings.setOnClickListener {
            loadFragment(SettingsFragment(), SETTINGS_FRAGMENT_TAG)
        }
        btnScore.setOnClickListener {
            val scoreFragment = ScoreFragment().apply {
                arguments = Bundle().apply { putInt(FINAL_SCORE_KEY, finalScore) }
            }
            loadFragment(scoreFragment, SCORE_FRAGMENT_TAG)
        }
    }

    // Reset skor akhir dan reload GameFragment
    private fun resetGameAndReturnToGameFragment() {
        finalScore = 0
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE) // Kosongkan back stack
        loadFragment(GameFragment(), GAME_FRAGMENT_TAG)
    }

    // Memperbarui skor akhir setelah permainan selesai
    fun updateFinalScore(score: Int) {
        finalScore = score
    }

    // Fungsi untuk memuat fragment dengan tag untuk identifikasi
    private fun loadFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        // Constants untuk identifikasi fragment dan argumen
        private const val GAME_FRAGMENT_TAG = "GameFragment"
        private const val SCORE_FRAGMENT_TAG = "ScoreFragment"
        private const val SETTINGS_FRAGMENT_TAG = "SettingsFragment"
        const val FINAL_SCORE_KEY = "finalScore"
    }
}
