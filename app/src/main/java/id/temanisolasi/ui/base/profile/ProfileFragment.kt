package id.temanisolasi.ui.base.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import coil.load
import coil.transform.CircleCropTransformation
import id.temanisolasi.R
import id.temanisolasi.data.model.Isolation
import id.temanisolasi.data.model.toList
import id.temanisolasi.databinding.DialogIsolationBinding
import id.temanisolasi.databinding.FragmentProfileBinding
import id.temanisolasi.ui.base.home.HomeViewModel
import id.temanisolasi.ui.startisolation.IsolationViewModel
import id.temanisolasi.utils.Helpers
import id.temanisolasi.utils.Helpers.encodeName
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(), ItemIsolationListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val homeModel: HomeViewModel by viewModel()
    private val isolationModel: IsolationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeModel.getUser()
        isolationModel.getDataIsolation()

        homeModel.user.observe(viewLifecycleOwner) {
            with(binding) {
                tvName.text = it.name
                ivAva.load(
                    it.img ?: Helpers.getPlaceHolder(it.name?.encodeName() ?: "")
                ) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            }
        }

        isolationModel.isolation.observe(viewLifecycleOwner) {
            binding.rvIsolation.apply {
                itemAnimator = DefaultItemAnimator()
                adapter = IsolationAdapter(this@ProfileFragment, it)
            }
        }

    }

    override fun onItemClick(isolation: Isolation) {
        val itemBinding = DialogIsolationBinding.bind(
            layoutInflater.inflate(R.layout.dialog_isolation, null)
        )
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(itemBinding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        itemBinding.btnClose.setOnClickListener { dialog.dismiss() }
        val listData = isolation.toList()
        itemBinding.rvItems.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = DialogIsolationAdapter(listData)
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}