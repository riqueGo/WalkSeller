package com.rique.walkseller.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.ui.state.SellerBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellerBottomSheetViewModel @Inject constructor() : ViewModel() {

    private val _sellerBottomSheetState: MutableState<SellerBottomSheetState> = mutableStateOf(
        SellerBottomSheetState()
    )

    val sellerBottomSheetState: State<SellerBottomSheetState>
        get() = _sellerBottomSheetState

    fun setInitialData(sellers: List<Seller>, onClickPositionSeller: (Seller) -> Unit) {
        _sellerBottomSheetState.value = _sellerBottomSheetState.value.copy(
            sellers = sellers,
            onClickPositionSeller = onClickPositionSeller
        )
    }

    fun setIsOpenBottomSheet(isOpen: Boolean) {
        _sellerBottomSheetState.value =
            _sellerBottomSheetState.value.copy(isOpenBottomSheet = isOpen)
    }

    fun onClickSellerBottomSheet(seller: Seller) {
        setIsOpenBottomSheet(false)
        _sellerBottomSheetState.value.onClickPositionSeller(seller)
    }
}