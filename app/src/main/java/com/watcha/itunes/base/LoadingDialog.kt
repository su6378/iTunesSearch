package com.watcha.itunes.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.watcha.itunes.databinding.DialogLoadingBinding

class LoadingDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "LoadingDialogFragment"
    }

    private lateinit var binding: DialogLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogLoadingBinding.inflate(layoutInflater)

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(0.7f)

        isCancelable = false

        return binding.root
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val fragmentTransaction = manager.beginTransaction()
            fragmentTransaction.add(this, tag)
            fragmentTransaction.commitAllowingStateLoss()
        } catch (ignored: IllegalStateException) {
        }
    }
}
