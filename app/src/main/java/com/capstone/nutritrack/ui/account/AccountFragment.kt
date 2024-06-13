package com.capstone.nutritrack.ui.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.capstone.nutritrack.databinding.FragmentAccountBinding
import com.capstone.nutritrack.ViewModelFactory
import com.capstone.nutritrack.ui.setgoals.SetGoalsActiivity
import com.capstone.nutritrack.ui.welcome.WelcomeActivity

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Handle click to navigate to SetGoalsActivity
        binding.setGoalsLayout.setOnClickListener {
            startActivityForResult(Intent(activity, SetGoalsActiivity::class.java), SET_GOALS_REQUEST_CODE)
        }

        // Handle logout button click
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        // Observe session changes
        viewModel.getSession().observe(viewLifecycleOwner, { user ->
            // Update other UI elements with user data as needed
        })

        return root
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Logout")
            setMessage("Are you sure you want to logout?")
            setPositiveButton("Yes") { _, _ ->
                viewModel.logout()
                navigateToWelcome()
            }
            setNegativeButton("No", null)
        }.create().show()
    }

    private fun navigateToWelcome() {
        val intent = Intent(requireContext(), WelcomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Finish current activity (MainActivity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SET_GOALS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val bmiCategory = data?.getStringExtra(KEY_BMI_CATEGORY)
            binding.textBmi.text = bmiCategory
        }
    }

    companion object {
        private const val SET_GOALS_REQUEST_CODE = 100
        private const val KEY_BMI_CATEGORY = "bmi_category"
    }
}
