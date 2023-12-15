package  com.capstone.nusart.ui_page.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.nusart.data.DataRepository
import com.capstone.nusart.data.api.response.ListArt

class HomeViewModel(
    private val repository: DataRepository
) : ViewModel() {
    fun getStory(token: String): LiveData<PagingData<ListArt>> =
        repository.getArt(token).cachedIn(viewModelScope)
}