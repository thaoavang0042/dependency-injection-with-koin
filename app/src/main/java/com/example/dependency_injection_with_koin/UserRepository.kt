package com.example.dependency_injection_with_koin

class UserRepository(private val api: GithubApi) {
    fun getAllUsers() = api.getUsers()
}