package com.vironit.krasnovskaya_l23_p3.ui

import android.Manifest
import android.app.WallpaperManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vironit.data.retrofit.model.UnsplashPhoto
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.common.util.ImageUtils
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentDetailsBinding
import com.vironit.krasnovskaya_l23_p3.databinding.UserInfoViewBinding
import com.vironit.krasnovskaya_l23_p3.viewmodel.PhotoDetailsViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: PhotoDetailsViewModel
    private var unsplashPhoto: UnsplashPhoto? = null
    private lateinit var wallpaperManager: WallpaperManager

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)
        viewModel = ViewModelProvider(this).get(PhotoDetailsViewModel::class.java)
        wallpaperManager = WallpaperManager.getInstance(requireContext())
        setUI()
        setObserver()
        val isOnline = ImageUtils.isOnline(requireContext())
        viewModel.getPhoto(requireContext(), args.photoId, isOnline)
        onClickListener()
    }

    private fun onClickListener() {
        binding.share.setOnClickListener { checkPermission() }
        binding.settings.setOnClickListener {
            setSettingsVisible(true)
        }
        binding.ll1.setOnClickListener {
            setAsWallpaper()
        }
        binding.ll2.setOnClickListener {
            setAsLockScreen()
        }
        binding.ll3.setOnClickListener {
            unsplashPhoto?.let { it1 -> viewModel.savePhoto(requireContext(), it1) }
            Toast.makeText(requireContext(), "наверное все хорошо", Toast.LENGTH_SHORT).show()
            setSettingsVisible(false)
        }
        binding.resize.setOnClickListener {
            resize()
        }
        binding.info.setOnClickListener {
            showInfo()
        }
    }

    private fun setObserver() {
        viewModel.unsplashPhoto.observe(viewLifecycleOwner, { photo ->
            showDetail(photo)
            unsplashPhoto = photo
        })
        viewModel.requestError.observe(viewLifecycleOwner, { requestError ->
            Toast.makeText(requireContext(), requestError, Toast.LENGTH_SHORT).show()
        })
        viewModel.showProgress.observe(viewLifecycleOwner, { showProgress ->
            binding.progressBar.visibility = if (showProgress) View.VISIBLE else View.GONE
        })
    }

    private fun showDetail(photo: UnsplashPhoto) {
        ImageUtils.buildGradle(
            requireContext(),
            photo.urlUnsplash?.regular.toString(),
            binding.ivPhoto
        )
        (activity as AppCompatActivity).supportActionBar?.title = photo.description
    }

    private fun setUI() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_bar).isVisible = false
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                Color.WHITE
            )
        )
    }

    private fun checkPermission() {
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

    private fun showInfo() {
        if(unsplashPhoto != null) {
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
        }else{
            Toast.makeText(requireContext(), "Wait while image is loaded", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setInfo(layoutBinding: UserInfoViewBinding) {
        layoutBinding.userName.text = unsplashPhoto?.unsplashUser?.name
        layoutBinding.inst.text = unsplashPhoto?.unsplashUser?.inst
        layoutBinding.tw.text = unsplashPhoto?.unsplashUser?.twitter
        layoutBinding.userNick.text = unsplashPhoto?.unsplashUser?.twitter

        ImageUtils.buildGradle(
            requireContext(),
            unsplashPhoto?.unsplashUser?.unsplashProfileImage?.medium.toString(),
            layoutBinding.userImage
        )
        layoutBinding.imgDescription.text = unsplashPhoto?.description
        val jud: Date = SimpleDateFormat("yyyy-MM-dd").parse(unsplashPhoto?.date)
        val date: String =
            DateFormat.getDateInstance(SimpleDateFormat.LONG, Locale("ru")).format(jud)
        layoutBinding.date.text = date
        layoutBinding.color.text = unsplashPhoto?.color
        layoutBinding.size.text = "Px: ${unsplashPhoto?.width} x ${unsplashPhoto?.height}"
    }

    private fun shareContent() {
        val bitmap = (binding.ivPhoto.drawable as BitmapDrawable).bitmap

        val pathOfBmp = MediaStore.Images.Media.insertImage(
            requireActivity().contentResolver,
            bitmap,
            "${unsplashPhoto?.description}",
            null
        )
        val bmpUri = Uri.parse(pathOfBmp)
        val intent = Intent(Intent.ACTION_SEND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, bmpUri)
        startActivity(Intent.createChooser(intent, "Share image via"))
    }

    private fun setSettingsVisible(visible: Boolean) {
        binding.ll1.isVisible = visible
        binding.ll2.isVisible = visible
        binding.ll3.isVisible = visible
    }

    private fun setAsWallpaper() {
        try {
            val bitmap = (binding.ivPhoto.drawable as BitmapDrawable).bitmap
            wallpaperManager.setBitmap(bitmap)
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
        setSettingsVisible(false)
    }

    private fun setAsLockScreen() {
        try {
            val bitmap = (binding.ivPhoto.drawable as BitmapDrawable).bitmap
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
            }
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
        setSettingsVisible(false)
    }

    private fun resize() {
        binding.resize.setImageResource(R.drawable.ic_fullscreen)
        binding.resize.tag = R.drawable.ic_fullscreen
        binding.resize.setOnClickListener {
            if (binding.resize.tag == R.drawable.ic_fullscreen) {
                binding.constraintLayout2.isVisible = false
                binding.resize.setImageResource(R.drawable.ic_fullscreen_exit)
                binding.resize.tag = R.drawable.ic_fullscreen_exit
                requireActivity().window.statusBarColor = Color.parseColor("#ff000000")
                (activity as AppCompatActivity).supportActionBar?.hide()
            } else {
                binding.constraintLayout2.isVisible = true
                binding.resize.setImageResource(R.drawable.ic_fullscreen)
                binding.resize.tag = R.drawable.ic_fullscreen
                requireActivity().window.statusBarColor = Color.TRANSPARENT
                (activity as AppCompatActivity).supportActionBar?.show()
            }
        }
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
            shareContent()
        }
    }
}