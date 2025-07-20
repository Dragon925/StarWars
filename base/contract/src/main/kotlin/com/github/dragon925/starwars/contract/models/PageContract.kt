package com.github.dragon925.starwars.contract.models

interface PageContract<T : ModelContract> : ModelContract  {
    val count: Int
    val prev: Int?
    val next: Int?
    val results: List<T>
}