package com.example.minutemedicine.view.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.minutemedicine.R
import com.example.minutemedicine.databinding.FragmentMainBinding
import com.example.minutemedicine.repository.RepositoryHistoryImpl


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val repositoryHistoryImpl: RepositoryHistoryImpl by lazy { RepositoryHistoryImpl() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = MainViewPagerAdapter(requireActivity().supportFragmentManager)
        initBottomNavigation()


//        val bottomNavigationView = binding.bottomNavigationView
//        val menuView: BottomNavigationMenuView = bottomNavigationView.getChildAt(0) as BottomNavigationMenuView
//
//        for (i in 0 until menuView.childCount) {
//
//            if (i == 2) {
//
//                val iconView: View = menuView.getChildAt(i)
//                    .findViewById(com.google.android.material.R.id.icon)
//                val layoutParams: ViewGroup.LayoutParams = iconView.getLayoutParams();
//                val displayMetrics: DisplayMetrics = getResources().getDisplayMetrics();
//
//                layoutParams.height =
//                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38f, displayMetrics)
//                        .toInt()
//
//                layoutParams.width =
//                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38f, displayMetrics)
//                        .toInt()
//                iconView.setLayoutParams(layoutParams);
//            }
//        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.itemIconTintList = null
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNavigationView.menu.findItem(R.id.page_1).isChecked = true
                    1 -> binding.bottomNavigationView.menu.findItem(R.id.page_2).isChecked = true
                    2 -> binding.bottomNavigationView.menu.findItem(R.id.page_3).isChecked = true
                    3 -> binding.bottomNavigationView.menu.findItem(R.id.page_4).isChecked = true
                    4 -> binding.bottomNavigationView.menu.findItem(R.id.page_5).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.page_2 -> {
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.page_3 -> {
                    binding.viewPager.currentItem = 2
                    true
                }
                R.id.page_4 -> {
                    binding.viewPager.currentItem = 3
                    true
                }
                R.id.page_5 -> {
                    binding.viewPager.currentItem = 4
                    true
                }
                else -> true
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}