package com.rique.walkseller.ui.state

import com.rique.walkseller.domain.Seller

data class SellerBottomSheetState(
    val isOpenBottomSheet: Boolean = false,
    val sellers: List<Seller> = emptyList(),
    val onClickPositionSeller: (Seller) -> Unit = {},
    val navigateToSellerProducts: (Seller) -> Unit = {}
)
