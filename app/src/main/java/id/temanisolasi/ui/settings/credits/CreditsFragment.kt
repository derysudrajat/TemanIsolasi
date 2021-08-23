package id.temanisolasi.ui.settings.credits

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.temanisolasi.data.model.Credits
import id.temanisolasi.databinding.FragmentCreditsBinding
import id.temanisolasi.utils.COLLECTION

class CreditsFragment : Fragment() {

    private var _binding: FragmentCreditsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Firebase.firestore.collection(COLLECTION.CREDITS)
            .orderBy("no", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) Log.d("TAG", "creditsError: ${error.message}")

                if (value != null && !value.isEmpty) {
                    value.toObjects(Credits::class.java).let {
                        if (it.isNotEmpty()) populateData(it) else populateData(listOf())
                    }
                }
            }
    }

    private fun populateData(it: List<Credits>) {
        binding.rvCredits.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = CreditsAdapter(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = CreditsFragment()
    }
}