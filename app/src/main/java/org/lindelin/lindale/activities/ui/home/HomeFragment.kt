package org.lindelin.lindale.activities.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_home.*
import org.lindelin.lindale.R
import org.lindelin.lindale.models.Profile
import org.lindelin.lindale.supports.loadHtmlString

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = activity?.run {
            ViewModelProviders.of(this)[HomeViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        homeViewModel.getProfile().observe(this, Observer {
            updateUI(it)
        })

        homeViewModel.getUserPhoto().observe(this, Observer {
            profileImageView.setImageBitmap(it)
        })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun updateUI(profile: Profile) {
//        val encodedHtml = Base64.encodeToString(profile.activity.toByteArray(), Base64.NO_PADDING)
//        activityWebView.settings.javaScriptEnabled = true
//        activityWebView.loadData(encodedHtml, "text/html", "base64")
        activityWebView.loadHtmlString(profile.activity)
        projectCountText.text = profile.status.projectCount.toString()
        taskCountText.text = profile.status.unfinishedTaskCount.toString()
        todoCountText.text = profile.status.unfinishedTodoCount.toString()
    }
}