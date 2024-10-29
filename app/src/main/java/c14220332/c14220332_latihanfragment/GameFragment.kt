package c14220332.c14220332_latihanfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameFragment : Fragment() {

    private lateinit var tvScoreValue: TextView
    private lateinit var btnGiveUp: Button
    private lateinit var buttons: List<Button>

    // Constants untuk skor default dan nilai tambah/kurang
    private val INITIAL_SCORE = 50
    private val MATCH_SCORE = 10
    private val MISMATCH_PENALTY = 5

    private var score = INITIAL_SCORE
    private var lastSelectedValue: String? = null
    private var lastButton: Button? = null
    private var matchedPairs = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        // Initialize UI components
        tvScoreValue = view.findViewById(R.id.tvScoreValue).apply { var text = score.toString() }
        btnGiveUp = view.findViewById(R.id.btnGiveUp)
        btnGiveUp.setOnClickListener { navigateToResult() }

        buttons = listOf(
            view.findViewById(R.id.btn1),
            view.findViewById(R.id.btn2),
            view.findViewById(R.id.btn3),
            view.findViewById(R.id.btn4),
            view.findViewById(R.id.btn5),
            view.findViewById(R.id.btn6),
            view.findViewById(R.id.btn7),
            view.findViewById(R.id.btn8),
            view.findViewById(R.id.btn9)
        )

        initializeGame()
        return view
    }

    private fun initializeGame() {
        // Random angka 1-5 (dua kali untuk setiap angka)
        val numbers = (1..5).flatMap { listOf(it, it) }.shuffled()

        // Menyebarkan angka pada tombol grid dan mengatur ulang status tombol
        buttons.forEachIndexed { index, button ->
            button.apply {
                text = numbers[index].toString()
                isEnabled = true
                visibility = View.VISIBLE
                setOnClickListener { onNumberButtonClick(this) }
            }
        }

        // Reset game state
        score = INITIAL_SCORE
        matchedPairs = 0
        lastSelectedValue = null
        lastButton = null
        tvScoreValue.text = score.toString()
    }

    private fun onNumberButtonClick(button: Button) {
        val selectedValue = button.text.toString()

        // Cek jika tombol yang sama dipilih lagi
        if (button == lastButton) return

        if (lastSelectedValue == null) {
            // Jika angka pertama dipilih
            lastSelectedValue = selectedValue
            lastButton = button
            button.isEnabled = false
        } else {
            // Angka kedua dipilih, lakukan pengecekan
            if (selectedValue == lastSelectedValue) {
                score += MATCH_SCORE
                matchedPairs++
                button.isEnabled = false
                lastButton?.isEnabled = false
            } else {
                score -= MISMATCH_PENALTY
                lastButton?.isEnabled = true
            }

            // Update skor dan reset pilihan
            tvScoreValue.text = score.toString()
            lastSelectedValue = null
            lastButton = null
        }

        // Jika semua pasangan ditemukan, arahkan ke hasil
        if (matchedPairs == 4) {
            navigateToResult()
        }
    }

    private fun navigateToResult() {
        (activity as MainActivity).updateFinalScore(score)
        val resultFragment = ScoreFragment().apply {
            arguments = Bundle().apply { putInt("finalScore", score) }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, resultFragment)
            .addToBackStack(null)
            .commit()
    }
}
