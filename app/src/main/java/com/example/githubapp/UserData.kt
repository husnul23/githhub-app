package com.example.githubapp

object  UserData {
    private val userNames = arrayOf(R.array.username)
    private val fullNames = arrayOf(R.array.name)
    private val avatarImages = arrayOf(R.drawable.user1, R.drawable.user2,
        R.drawable.user3, R.drawable.user4, R.drawable.user5, R.drawable.user6,
        R.drawable.user7, R.drawable.user8, R.drawable.user8, R.drawable.user9,
        R.drawable.user10)
    private val userCompanies = arrayOf(R.array.company)
    private val userFollowers = arrayOf(R.array.followers)
    private val userFollowings = arrayOf(R.array.following)
    private val userLocation = arrayOf(R.array.location)
    private val userRepository = arrayOf(R.array.repository)

    val listData: ArrayList<Github>
    get() {
        val list = arrayListOf<Github>()

        for (position in userNames.indices) {
            val github = Github()
            github.username = userNames[position].toString()
            github.name = fullNames[position].toString()
            github.avatar = avatarImages[position]
            github.company = userCompanies[position].toString()
            github.followers = userFollowers[position].toString()
            github.followings = userFollowings[position].toString()
            github.location = userLocation[position].toString()
            github.repository = userRepository[position].toString()
            list.add(github)
        }
        return list
    }

}