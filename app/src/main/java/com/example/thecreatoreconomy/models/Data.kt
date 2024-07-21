package com.example.thecreatoreconomy.models

data class Data(
val recent_links: List<Link>,
val top_links: List<Link>,
val favourite_links: List<Link>,
val overall_url_chart: Map<String, Int>
)
