package com.vironit.krasnovskaya_l23_p3.ui

import android.Manifest
import android.app.WallpaperManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentDetailsBinding
import com.vironit.krasnovskaya_l23_p3.databinding.UserInfoViewBinding
import com.vironit.krasnovskaya_l23_p3.model.PhotoEntity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var photo: PhotoEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_bar).isVisible = false
        binding.apply {
            photo = args.photo
            (activity as AppCompatActivity).supportActionBar?.show()
            (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                    Color.WHITE
                )
            )
            (activity as AppCompatActivity).supportActionBar?.title = photo.description
            Glide.with(this@DetailsFragment)
                .asBitmap()
                .load(photo.urlEntity.regular)
                .error(R.drawable.ic_error)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        ivPhoto.setImageBitmap(resource)
                        binding.progressBar.isVisible = false
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        binding.progressBar.isVisible = false
                    }
                })
        }
        binding.share.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 1
                )
            } else {
                shareContent()
            }
        }
        binding.settings.setOnClickListener {
            binding.ll1.isVisible = true
            binding.ll2.isVisible = true
            binding.ll3.isVisible = true
        }
        binding.ll1.setOnClickListener {
            try {
                val bitmap = (binding.ivPhoto.drawable as BitmapDrawable).bitmap
                val myWallpaperManager = WallpaperManager
                    .getInstance(requireContext())
                myWallpaperManager.setBitmap(bitmap)
                Toast.makeText(
                    requireContext(), "Wallpaper set",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error setting Wallpaper", Toast.LENGTH_SHORT
                )
                    .show()
            }
            binding.ll1.isVisible = false
            binding.ll2.isVisible = false
            binding.ll3.isVisible = false
        }
        binding.ll2.setOnClickListener {
            try {
                val bitmap = (binding.ivPhoto.drawable as BitmapDrawable).bitmap
                val myWallpaperManager = WallpaperManager
                    .getInstance(requireContext())
                myWallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                Toast.makeText(
                    requireContext(), "Lock screen set",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error setting Lock screen", Toast.LENGTH_SHORT
                )
                    .show()
            }
            binding.ll1.isVisible = false
            binding.ll2.isVisible = false
            binding.ll3.isVisible = false
        }
        binding.resize.setImageResource(R.drawable.ic_fullscreen)
        binding.resize.tag = R.drawable.ic_fullscreen
        binding.resize.setOnClickListener {
            if (binding.resize.tag == R.drawable.ic_fullscreen) {
                binding.constraintLayout2.isVisible = false
                binding.resize.setImageResource(R.drawable.ic_fullscreen_exit)
                binding.resize.tag = R.drawable.ic_fullscreen_exit
                val decorView: View = requireActivity().window.decorView
                val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                decorView.systemUiVisibility = uiOptions
                requireActivity().window.statusBarColor = Color.parseColor("#80000000")
                (activity as AppCompatActivity).supportActionBar?.hide()
            } else {
                binding.constraintLayout2.isVisible = true
                binding.resize.setImageResource(R.drawable.ic_fullscreen)
                binding.resize.tag = R.drawable.ic_fullscreen
                val decorView: View = requireActivity().window.decorView
                val uiOptions = View.SYSTEM_UI_FLAG_VISIBLE
                decorView.systemUiVisibility = uiOptions
                requireActivity().window.statusBarColor = Color.TRANSPARENT
                (activity as AppCompatActivity).supportActionBar?.show()
            }
        }
        binding.info.setOnClickListener {
            val infoAboutAuthorLayout = LayoutInflater.from(requireContext())
                .inflate(R.layout.user_info_view, binding.mainConstraint, false)
            val layoutBinding = UserInfoViewBinding.bind(infoAboutAuthorLayout)
            setInfo(layoutBinding)
            binding.prevContent.addView(infoAboutAuthorLayout)
            binding.prevContent.background = resources.getDrawable(R.drawable.rounded)
            (activity as AppCompatActivity).supportActionBar?.hide()
            binding.constraintLayout2.isVisible = false
            binding.resize.isVisible = false
            binding.back.isVisible = true
        }
    }

    private fun setInfo(layoutBinding: UserInfoViewBinding) {
        layoutBinding.userName.text = photo.userEntity.name
        if (!photo.userEntity.inst.isNullOrBlank()) {
            layoutBinding.inst.text = photo.userEntity.inst
        } else {
            layoutBinding.inst.isVisible = false
            layoutBinding.instIcon.isVisible = false
        }
        if (!photo.userEntity.twitter.isNullOrBlank()) {
            layoutBinding.tw.text = photo.userEntity.twitter
            layoutBinding.userNick.text = photo.userEntity.twitter
        } else {
            layoutBinding.userNick.isVisible = false
            layoutBinding.tw.isVisible = false
            layoutBinding.twIcon.isVisible = false
        }
        Glide.with(this@DetailsFragment)
            .asBitmap()
            .load(photo.userEntity.profileImageEntity.medium)
            .error(R.drawable.ic_error)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    layoutBinding.userImage.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        layoutBinding.imgDescription.text = photo.description
        val jud: Date = SimpleDateFormat("yyyy-MM-dd").parse(photo.date)
        val date: String =
            DateFormat.getDateInstance(SimpleDateFormat.LONG, Locale("ru")).format(jud)
        layoutBinding.date.text = date
        layoutBinding.color.text = photo.color
        layoutBinding.size.text = "Px: ${photo.width} x ${photo.height}"
    }

    private fun shareContent() {
        val bitmap = (binding.ivPhoto.drawable as BitmapDrawable).bitmap

        val pathOfBmp = Images.Media.insertImage(
            requireActivity().contentResolver,
            bitmap,
            "${photo.description}",
            null
        )
        val bmpUri = Uri.parse(pathOfBmp)
        val intent = Intent(Intent.ACTION_SEND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, bmpUri)
        startActivity(Intent.createChooser(intent, "Share image via"))
    }

    private fun setWallpaper() {
        val bitmap = (binding.ivPhoto.drawable as BitmapDrawable).bitmap

        val pathOfBmp = Images.Media.insertImage(
            requireActivity().contentResolver,
            bitmap,
            "${photo.description}",
            null
        )
        val bmpUri = Uri.parse(pathOfBmp)
        val intent = Intent(Intent.ACTION_ATTACH_DATA)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setDataAndType(bmpUri, "image/*")
        intent.putExtra("mimeType", "image/*")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(intent, "Set as"))
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            shareContent()
        }
        if (requestCode == 2 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            setWallpaper()
        }
    }

}