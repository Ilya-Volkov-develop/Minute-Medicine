package com.example.minutemedicine.view.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.minutemedicine.databinding.FragmentProfileBinding

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val KEY_IMAGE = "personImage"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init() {
        binding.icon.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }
        loadIconAndName()
    }

    private fun loadIconAndName() {
        val pref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val prefPersonImage = pref.getString(KEY_IMAGE, null)
        if (prefPersonImage != null) {
            val uri = Uri.parse(prefPersonImage)
            try {
                Glide.with(this).load(uri).into(binding.icon)
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        if (resultCode == Activity.RESULT_OK) {
            val selectedImage = imageReturnedIntent?.data
            val editor = requireActivity().getPreferences(Context.MODE_PRIVATE)
            editor.edit().putString(KEY_IMAGE, selectedImage.toString()).apply()
            Glide.with(this).load(selectedImage).into(binding.icon)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}