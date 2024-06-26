package com.example.planet.common.domain

/**
 * An interface that represents a use case.
 * It is a function that takes an input and returns an output.
 * Use cases are typically used to perform business logic or data manipulation.
 */
interface Usecase<Input, out Output> {
    operator fun invoke(params: Input): Output
}
