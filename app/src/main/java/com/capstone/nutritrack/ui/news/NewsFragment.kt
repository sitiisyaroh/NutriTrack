package com.capstone.nutritrack.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.nutritrack.databinding.FragmentNewsBinding
import com.capstone.nutritrack.dummy.news.DummyNewsData
import com.capstone.nutritrack.ui.news.detail.DetailNewsActivity

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi RecyclerView dan Adapter
        val adapter = NewsAdapter(requireContext(), DummyNewsData.newsList) { news ->
            val intent = Intent(requireContext(), DetailNewsActivity::class.java).apply {
                // Kirim data berita ke DetailNewsActivity
                putExtra("NEWS_TITLE", news.title)
                putExtra("NEWS_AUTHOR", news.author)
                putExtra("NEWS_DESCRIPTION", news.description)
                putExtra("NEWS_IMAGE_URL", news.image) // Kirim URL gambar
            }
            startActivity(intent)
        }

        // Set layout manager dan adapter untuk RecyclerView
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}