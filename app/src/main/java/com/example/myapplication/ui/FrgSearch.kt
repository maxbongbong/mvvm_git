package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.basic.BasicFragment
import com.example.myapplication.data.User
import com.example.myapplication.databinding.FrgFindBinding
import com.example.myapplication.extension.onClick
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.ui.adapter.UserItemAdapter
import com.example.myapplication.ui.dialog.PopupAlert
import com.example.myapplication.util.CommonUtils
import com.example.myapplication.util.NetworkUtils
import com.example.myapplication.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.frg_find.*


class FrgSearch : BasicFragment() {

    private var mView: View? = null
    private lateinit var binding: FrgFindBinding
    private lateinit var userAdapter: UserItemAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var networkPopUp: PopupAlert
    lateinit var factory: MainViewModel.Factory

    companion object {
        fun newInstance(): FrgSearch {
            return FrgSearch()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FrgFindBinding.inflate(inflater, container, false)
        mView = binding.root

        factory = MainViewModel.Factory(UserRepository())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.viewModel?.data?.observe(viewLifecycleOwner) {
            initRecyclerView(it)
        }

        context?.let {
            networkPopUp = NetworkUtils.popUpAlert(it)
        }

        val connection = NetworkUtils(requireActivity().applicationContext)
        connection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                if (networkPopUp.isShowing) {
                    networkPopUp.dismiss()
                    showToastMessage(getString(R.string.network_connect))
                }
            } else {
                if (networkPopUp.isShowing) networkPopUp.dismiss()
                networkPopUp.show()
            }
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivMagnifier.onClick {
            binding.etSearch.run {
                clickMethod(this)
            }
        }
    }

    private fun clickMethod(editText: EditText) {
        editText.run {
            if (this.text.isNullOrEmpty()) {
                Toast.makeText(context, getString(R.string.input_user_name), Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (NetworkUtils.isNetworkAvailable(context)) {
                    binding.viewModel?.requestRepository(this.text.toString())
                    text.clear()
                    CommonUtils().hideKeyboard(activity as ActMain)
                } else {
                    if (networkPopUp.isShowing) networkPopUp.dismiss()
                    networkPopUp.show()
                }
            }
        }
    }

    private fun initRecyclerView(list: List<User>) {
        context?.let {
            val adapterState = ::userAdapter.isInitialized

            if (!adapterState) {
                userAdapter = UserItemAdapter(it).apply {
                    this.selectItem = object : UserItemAdapter.SelectItem {
                        override fun selectItem(position: Int, type: String) {
                            when (type) {
                                "select" -> {
                                    val url = getUser(position).htmlUrl
                                    if (url.isNullOrEmpty()) {
                                        showToastMessage(getString(R.string.empty_url))
                                    } else {
                                        CommonUtils().openUrl(
                                            url, (activity as ActMain)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                rvUser.run {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(it)
                    adapter = userAdapter
                }
            }

            if (list.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvUser.visibility = View.INVISIBLE
            } else {
                binding.tvEmpty.visibility = View.INVISIBLE
                binding.rvUser.visibility = View.VISIBLE
            }

            userAdapter.updateUserList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }
}