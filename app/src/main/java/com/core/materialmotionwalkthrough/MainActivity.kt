package com.core.materialmotionwalkthrough

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.core.materialmotionwalkthrough.databinding.ActivityMainBinding
import com.google.android.material.transition.MaterialSharedAxis

private const val COUNT = 3

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var selected = MutableLiveData<Int>().apply {
        value = 0
    }
    private val titles by lazy {
        arrayListOf(
            getString(R.string.daily_reports),
            getString(R.string.stay_updated),
            getString(R.string.online_payment)
        )
    }
    private val bodies by lazy {
        arrayListOf(
            getString(R.string.daily_reports_details),
            getString(R.string.stay_updated_details),
            getString(R.string.onlinie_payment_details)
        )
    }
    private val images = arrayListOf(
        R.drawable.ic_report,
        R.drawable.ic_updated,
        R.drawable.ic_online_payments
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selected.value?.let {
            val fragment = WalkthroughFragment.newInstance(
                titles[it],
                bodies[it],
                images[it]
            )
            supportFragmentManager.commit {
                add(R.id.fragment_container,fragment,FRAGMENT_TAG)
            }
        }

        setDotsTabLayout()
        setClickListeners()
        setSelectedObserver()
    }

    private fun setDotsTabLayout() {
        repeat(COUNT){
            binding.tabLayout.addTab(binding.tabLayout.newTab())
        }
        binding.tabLayout.touchables.forEach{
            it.isEnabled = false
        }
    }

    private fun setClickListeners() {
        binding.backButton.setOnClickListener {
            selected.value?.let {
                selected.value =it - 1
            }
            selectFragment(forward = false)
        }
        binding.nextButton.setOnClickListener {
            selected.value?.let {
                selected.value = it + 1
            }
            selectFragment(forward = true)
        }
    }

    private fun selectFragment(forward: Boolean) {
        selected.value?.let {
            val previousFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
            previousFragment?.exitTransition = buildTransition(forward)
            val fragment = WalkthroughFragment.newInstance(
                titles[it],
                bodies[it],
                images[it]
            )
            fragment.enterTransition = buildTransition(forward)
            supportFragmentManager.commit {
                replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
            }
        }
    }

    private fun buildTransition(forward: Boolean) =
        MaterialSharedAxis(MaterialSharedAxis.X, forward).apply {
            duration = 500
        }

    private fun setSelectedObserver() {
        selected.observe(this, Observer {
            binding.nextButton.isEnabled = it < COUNT - 1
            binding.backButton.isEnabled = it > 0
            binding.tabLayout.apply {
                selectTab(getTabAt(it))
            }
        })
    }

    companion object {
        private const val FRAGMENT_TAG = "WALKTHROUGH_FRAGMENT"
    }
}