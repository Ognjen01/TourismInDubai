package com.ognjenlazic.tourismindubai.domain

/**
 * Outcome of a data request.
 *
 * Loading data can succeed from the network, succeed from the cache, or fail outright.
 * Modelling that explicitly means callers cannot forget the cached case, and the UI can
 * tell the user it is looking at stale data instead of silently pretending otherwise.
 */
sealed interface DataResult<out T> {

    data class Success<out T>(
        val data: T,
        val origin: Origin
    ) : DataResult<T>

    data class Failure(
        val error: TopicsError
    ) : DataResult<Nothing>
}

enum class Origin {
    /** Freshly loaded from the network. */
    REMOTE,

    /** Served from the local cache because the network was unavailable. */
    CACHE
}

sealed interface TopicsError {
    /** The network failed and there was nothing usable in the cache. */
    data object NoDataAvailable : TopicsError

    /** Anything we did not anticipate. */
    data class Unexpected(val cause: Throwable) : TopicsError
}
