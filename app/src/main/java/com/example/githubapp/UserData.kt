package com.example.githubapp

object  UserData {
    private val userNames = arrayOf("JakeWharton", "amitshekariitbhu",
    "romainguy", "chrisbanes", "tipsy", "ravi8x", "jasoet",
    "budioktavian", "handisantika", "sidiqpermana")


    private val fullNames = arrayOf("Jake Wharton", "Amit Shekhar", "Romain Guy",
    "Chris Banes", "David", "Ravi Tamada", "Deny Prasetyo", "Budi Oktavian", "Hendi Santika",
    "Sidiq Permana")

    private val avatarImages = arrayOf(R.drawable.user1, R.drawable.user2,
        R.drawable.user3, R.drawable.user4, R.drawable.user5, R.drawable.user6,
        R.drawable.user7, R.drawable.user8, R.drawable.user9, R.drawable.user10)

    private val userCompanies = arrayOf("Google, Inc.", "MindOrksOpenSource", "Google",
    "Google working on @android", "Working Group Two", "AndroidHive | Droid5", "gojek-engineering",
    "KotlinID", "JVMDeveloperID @KotlinID @IDDevOps", "Nusantara Beta Studio")

    private val userFollowers = arrayOf("56995", "5153", "7972", "14725", "788", "18628",
    "277", "178", "428", "465")

    private val userFollowings = arrayOf("12", "2", "0", "1", "0", "3",
        "39", "23", "61", "10")

    private val userLocation = arrayOf("Pittsburgh, PA, USA", "New Delhi, India", "California",
    "Sydney, Australia", "Trondheim, Norway", "India", "Kotagede, Yogyakarta, Indonesia",
    "Jakarta, Indonesia", "Bojongsoang - Bandung Jawa Barat", "Jakarta Indonesia")

    private val userRepository = arrayOf("102", "37", "9", "30", "56", "28", "44",
    "110", "1064", "65")

    val listData: ArrayList<Github>
    get() {
        val list = arrayListOf<Github>()

        for (position in userNames.indices) {
            val github = Github()
            github.username = userNames[position]
            github.name = fullNames[position]
            github.avatar = avatarImages[position]
            github.company = userCompanies[position]
            github.followers = userFollowers[position]
            github.followings = userFollowings[position]
            github.location = userLocation[position]
            github.repository = userRepository[position]
            list.add(github)
        }
        return list
    }

}