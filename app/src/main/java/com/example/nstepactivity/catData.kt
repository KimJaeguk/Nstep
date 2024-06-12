package com.example.nstepactivity

data class Cat(val name: String, val imageResId: Int, val description: String)

val catData = listOf(
    Cat("루나", R.drawable.runa, "루나는 터키쉬 앙고라입니다.\n성미가 급하고 재롱을 많이 부립니다."),
    Cat("마일로", R.drawable.mailo, "마일로는 귀여운 갈색고양이입니다."),
    Cat("올리버", R.drawable.oliver, "올리버는 귀여운 러시안블루입니다.\n 장난끼가 많고 개구장이입니다."),
    Cat("레오", R.drawable.reo, "레오는 아메리칸 숏헤어 입니다.\n 줄무늬가 귀엽습니다."),
    Cat("네로", R.drawable.nero, "네로는 검은 고양이입니다.\n 불행을 가져다 준다고 하지만 귀여운걸요."),
    Cat("치즈", R.drawable.cheeze, "치즈는 코리안 숏헤어입니다.\n 말이 많고 활발한 성격을 가지고 있습니다."),
    Cat("섀도우", R.drawable.shadow, "섀도우는 어두운 색의 고양이로, 밤에 몰래 돌아다니는 것을 좋아합니다. 눈은 밝은 녹색으로 어둠 속에서도 빛납니다."),
    Cat("위스커스", R.drawable.wiskers, "위스커스는 길고 굵은 수염을 가진 고양이입니다. 매우 호기심이 많아 새로운 것들을 탐험하는 것을 좋아합니다."),
    Cat("에코", R.drawable.eco, "에코는 소리없이 움직이는 고양이로, 발소리 없이 조용히 돌아다니는 것을 좋아합니다."),
    Cat("제트", R.drawable.zet, "제트는 매우 빠르게 달리는 고양이입니다. 날렵한 몸매와 빠른 움직임으로 유명합니다."),
    Cat("스타라이트", R.drawable.star, "스타라이트는 별처럼 반짝이는 매력을 가진 고양이입니다. 밤에 산책하는 것을 좋아합니다.")
)