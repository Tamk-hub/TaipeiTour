package com.example.taipeitour.view

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.taipeitour.R
import com.example.taipeitour.databinding.FragmentAttractionDetailBinding



class AttractionDetailFragment : Fragment() {

    private var _binding: FragmentAttractionDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAttractionDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar.setupWithNavController(findNavController(), appBarConfiguration)


        val textViewName = binding.attTitle
        val textViewIntroduction = binding.attIntroduction
        val viewPager: ViewPager2 = binding.attImage
        val openTimeTextView = binding.openTime
        val addressTextView = binding.address
        val telTextView = binding.tel
        val urlTextView = binding.url


        val attractionName = arguments?.getString("attractionName")
        val attractionIntroduction = arguments?.getString("attractionIntroduction")
        val attractionImageList = arguments?.getStringArrayList("attractionImages")
        val attractionOpen_time = arguments?.getString("attractionOpen_time")
        val attractionAddress = arguments?.getString("attractionAddress")
        val attractionTel = arguments?.getString("attractionTel")
        val attractionUrl = arguments?.getString("attractionUrl")

//        Log.d(ContentValues.TAG, attractionImageList.toString())

        val imageAdapter = ImagePagerAdapter(attractionImageList?.toList() ?: emptyList())
        viewPager.adapter = imageAdapter


        // 更新其他 TextView 的資料
        textViewName.text = attractionName
        textViewIntroduction.text = attractionIntroduction
        openTimeTextView.text = attractionOpen_time
        addressTextView.text = attractionAddress
        telTextView.text = attractionTel
        urlTextView.text = attractionUrl

        // 設定 AppBar 的標題為景點名稱
        toolbar.title = attractionName

        Linkify.addLinks(binding.url, Linkify.WEB_URLS)
        binding.url.setOnClickListener{
            val url = binding.url.text.toString()
            openWebView(it.context, url)
        }
    }
    fun openWebView(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}