package com.rahmad.moodvies.presentation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rahmad.moodvies.presentation.detail.about.DetailAboutFragment
import com.rahmad.moodvies.presentation.detail.review.DetailReviewFragment

class SectionDetailPagerAdapter(activity: AppCompatActivity, private val movieId: Int) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DetailAboutFragment.newInstance(movieId)
            1 -> fragment = DetailReviewFragment.newInstance(movieId)
        }
        return fragment as Fragment
    }

    override fun getItemCount() = 2
}