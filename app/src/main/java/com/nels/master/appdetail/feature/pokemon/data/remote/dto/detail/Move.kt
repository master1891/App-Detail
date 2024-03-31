package com.nels.master.appdetail.feature.pokemon.data.remote.dto.detail

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)