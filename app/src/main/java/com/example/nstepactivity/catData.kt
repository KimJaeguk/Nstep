package com.example.nstepactivity

data class Cat(val name: String, val imageResId: Int, val description: String)

val catData = listOf(
    Cat("루나", R.drawable.runa, "루나는 터키쉬 앙고라입니다.\n성미가 급하고 재롱을 많이 부립니다."),
    Cat("마일로", R.drawable.mailo, "마일로는 귀여운 갈색고양이입니다."),
    Cat("올리버", R.drawable.oliver, "올리버는 귀여운 러시안블루입니다.\n 장난끼가 많고 개구장이입니다."),
    Cat("레오", R.drawable.reo, "레오는 아메리칸 숏헤어 입니다.\n 줄무늬가 귀엽습니다."),
    Cat("네로", R.drawable.nero, "네로는 검은 고양이입니다.\n 불행을 가져다 준다고 하지만 귀여운걸요."),
    Cat("치즈", R.drawable.cheeze, "치즈는 코리안 숏헤어입니다.\n 말이 많고 활발한 성격을 가지고 있습니다.")
)