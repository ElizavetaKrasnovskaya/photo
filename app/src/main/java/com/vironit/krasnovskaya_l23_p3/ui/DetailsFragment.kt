package com.vironit.krasnovskaya_l23_p3.ui

import android.Manifest
import android.app.WallpaperManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vironit.domain.retrofit.model.UnsplashPhoto
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.common.base.BaseFragment
import com.vironit.krasnovskaya_l23_p3.common.util.ImageUtils
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentDetailsBinding
import com.vironit.krasnovskaya_l23_p3.databinding.UserInfoViewBinding
import com.vironit.krasnovskaya_l23_p3.viewmodel.PhotoDetailsViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: PhotoDetailsViewModel by viewModels()
    private var unsplashPhoto: UnsplashPhoto? = null
    private lateinit var wallpaperManager: WallpaperManager
    private lateinit var infoAboutAuthorLayout: View

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)
        wallpaperManager = WallpaperManager.getInstance(requireContext())
        setUI()
        setObserver()
        val isOnline = ImageUtils.isOnline(requireContext())
        viewModel.getPhoto(args.photoId, isOnline)
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
            unsplashPhoto?.let { it1 -> viewModel.savePhoto(it1) }
            setSettingsVisible(false)
        }
        binding.resize.setOnClickListener {
            resize()
        }
        binding.info.setOnClickListener {
            showInfo()
        }
        binding.back.setOnClickListener {
            hideUserInfo()
        }
    }

    private fun setObserver() {
        viewModel.unsplashPhoto.observe(viewLifecycleOwner, { photo ->
            showDetail(photo)
            unsplashPhoto = photo
        })
        viewModel.requestError.observe(viewLifecycleOwner, { requestError ->
            toast(requestError)
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
        binding.description.text = photo.description
    }

    private fun setUI() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_black_back)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.resize.setImageResource(R.drawable.ic_fullscreen)
        binding.resize.tag = R.drawable.ic_fullscreen
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            requireActivity().onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
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
        if (unsplashPhoto != null) {
            infoAboutAuthorLayout = LayoutInflater.from(requireContext())
                .inflate(R.layout.user_info_view, binding.mainConstraint, false)
            val layoutBinding = UserInfoViewBinding.bind(infoAboutAuthorLayout)
            setInfo(layoutBinding)
            binding.prevContent.addView(infoAboutAuthorLayout)
            binding.prevContent.background = resources.getDrawable(R.drawable.rounded)
            (activity as AppCompatActivity).supportActionBar?.hide()
            binding.constraintLayout2.isVisible = false
            binding.resize.isVisible = false
            binding.back.isVisible = true
        } else {
            toast("Wait while image is loaded")
        }
    }

    private fun hideUserInfo() {
        binding.prevContent.removeView(infoAboutAuthorLayout)
        (activity as AppCompatActivity).supportActionBar?.show()
        binding.constraintLayout2.isVisible = true
        binding.resize.isVisible = true
        binding.back.isVisible = false
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
        layoutBinding.portfolio.setOnClickListener {
            if(unsplashPhoto?.unsplashUser?.portfolio.isNullOrEmpty()){
                toast("No portfolio")
            }else {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(unsplashPhoto?.unsplashUser?.portfolio))
                startActivity(intent)
            }
        }
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
            toast("Wallpaper set")
        } catch (e: Exception) {
            toast("Error setting Wallpaper")
        }
        setSettingsVisible(false)
    }

    private fun setAsLockScreen() {
        try {
            val bitmap = (binding.ivPhoto.drawable as BitmapDrawable).bitmap
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
            }
            toast("Lock screen set")
        } catch (e: Exception) {
            toast("Error setting Lock screen")
        }
        setSettingsVisible(false)
    }

    private fun resize() {
        if (binding.resize.tag == R.drawable.ic_fullscreen) {
            binding.constraintLayout2.isVisible = false
            binding.resize.setImageResource(R.drawable.ic_fullscreen_exit)
            binding.resize.tag = R.drawable.ic_fullscreen_exit
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        } else if (binding.resize.tag == R.drawable.ic_fullscreen_exit) {
            binding.constraintLayout2.isVisible = true
            binding.resize.setImageResource(R.drawable.ic_fullscreen)
            binding.resize.tag = R.drawable.ic_fullscreen
            requireActivity().window.clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            requireActivity().window.statusBarColor = Color.WHITE
            (requireActivity() as AppCompatActivity).supportActionBar?.show()
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