package com.deliveryhero.graphqlplayground.domain

data class LaunchData(
    val cursor: String,
    val hasMore: Boolean,
    val launches: List<LaunchItem>
)

data class LaunchItem(
    val id: String,
    val site: String?,
    val booked: Boolean,
    val missionName: String? = "",
    val missionPatch: String? = ""
)
