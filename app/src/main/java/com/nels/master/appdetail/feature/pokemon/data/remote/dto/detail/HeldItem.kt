package com.nels.master.appdetail.feature.pokemon.data.remote.dto.detail

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)