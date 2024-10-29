package c14220332.c14220332_latihanfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val ARG_USERNAME = "username" // Changed param names for clarity
private const val ARG_EMAIL = "email"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    private var username: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
            email = it.getString(ARG_EMAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param username User's username.
         * @param email User's email address.
         * @return A new instance of fragment SettingsFragment.
         */
        @JvmStatic
        fun newInstance(username: String, email: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                    putString(ARG_EMAIL, email)
                }
            }
    }
}
