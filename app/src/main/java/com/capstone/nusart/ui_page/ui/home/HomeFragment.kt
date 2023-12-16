package  com.capstone.nusart.ui_page.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nusart.R
import com.capstone.nusart.ViewModelFactory
import com.capstone.nusart.data.api.response.ListArt
import com.capstone.nusart.databinding.FragmentHomeBinding
import com.capstone.nusart.preference_manager.LanguageManager
import com.capstone.nusart.preference_manager.UserManager
import com.capstone.nusart.ui_page.Favorite.FavoriteActivity
import com.capstone.nusart.ui_page.detail.DetailActivity
import com.capstone.nusart.ui_page.ui.home.category.CategoryAdapter
import com.capstone.nusart.ui_page.ui.home.category.CategoryModel
import com.capstone.nusart.ui_page.utils.hide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class HomeFragment : Fragment(), HomeAdapter.Listener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var ArtAdapter: HomeAdapter
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var preferences: UserManager
    private lateinit var factory: ViewModelFactory

    private val viewModel: HomeViewModel by viewModels {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        //setupCategoryRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLanguage()
        setupAction()
        setupProperty()
        setArtList()
    }

    private fun setupLanguage() {
        val language = LanguageManager.getLanguage(requireActivity())
        val config = resources.configuration
        config.setLocale(Locale(language))
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun setupAction() {
        binding.btnAddFavorite.setOnClickListener {
            startActivity(Intent(requireActivity(), FavoriteActivity::class.java))
        }
    }

    private fun setupProperty() {
        factory = ViewModelFactory.getInstance(requireActivity())
        preferences = UserManager(requireActivity())
    }

    private fun setArtList() {
        val token = preferences.getToken() ?: ""
        val userToken = "Bearer $token"

        ArtAdapter = HomeAdapter(this)
        binding.rvArtlibrary.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvArtlibrary.setHasFixedSize(true)
        binding.rvArtlibrary.adapter = ArtAdapter.withLoadStateFooter(
            footer = HomeStateAdapter {
                ArtAdapter.retry()
            }
        )

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val storyResponse = viewModel.getArt(userToken)
            withContext(Dispatchers.Main) {
                storyResponse.observe(viewLifecycleOwner) { storyData ->
                    ArtAdapter.submitData(lifecycle, storyData)
                    binding.homeprogressBar.hide()
                }
            }
        }
    }

    private fun setupCategoryRecyclerView() {
        val categoryList = createCategoryList() // Replace with your actual category data
        val adapter = CategoryAdapter(categoryList)
        categoryRecyclerView.adapter = adapter
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun createCategoryList(): List<CategoryModel> {
        // Replace this with your actual category data (name and image resource ID)
        return listOf(
            CategoryModel("All", R.drawable.all_category),
            CategoryModel("Abstrak", R.drawable.abstract_category),
            CategoryModel("Realisme", R.drawable.realisme_category)
        )
    }

    override fun onListener(story: ListArt) {
        startActivity(
            Intent(requireActivity(), DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_DATA, story)
        )
    }
}
