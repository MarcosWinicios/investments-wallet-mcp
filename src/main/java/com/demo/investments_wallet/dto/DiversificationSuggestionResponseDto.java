package com.demo.investments_wallet.dto;

import com.demo.investments_wallet.domain.types.AssetCategory;
import java.util.List;

public record DiversificationSuggestionResponseDto(
        String concentrationFindings,
        List<AssetCategory> underrepresentedCategories,
        List<String> suggestedAssets,
        String educationalRationale
) {
}
