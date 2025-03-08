package calculator
import camp.nextstep.edu.missionutils.Console
const val ZERO: Int = 0

fun main() {
    print("덧셈할 문자열을 입력해 주세요.")
    val inputNumber = Console.readLine()

    if (inputNumber.isBlank()) {
        getResult(ZERO)
        return
    }

    val numberList = mutableListOf<Int>()
    val customSignResult =
        if (inputNumber.startsWith("//")) {
            getCustomSign(inputNumber)
        } else null

    if (customSignResult.isNullOrBlank()) {
        val splitList = basicSplit(inputNumber)
        var intList = stringToInt(splitList)
        numberList.addAll(defineInt(intList))

    } else {
        if (!inputNumber.contains("\\n")) {
            incorrectSign()
        }
        val splitList = customSplit(inputNumber, customSignResult)
        val intList = stringToInt(splitList)
        numberList.addAll(defineInt(intList))
    }

    val result = sum(numberList)
    getResult(result)
}

fun basicSplit(input: String): List<String> {
    return input.split(',', ':')
}

fun customSplit(input: String, sign: String): List<String> {
    if (!input.contains(sign)) {
        incorrectSign()
    }
    val spliter = input.substringAfter("\\n")
    return spliter.split(sign)
}

fun stringToInt(input: List<String>): List<Int> {
    var numList = mutableListOf<Int>()
    for (i in input) {
        var num = i.toInt()
        numList.add(num)
    }
    return numList
}

fun defineInt(numList: List<Int>): List<Int> {
    for (i in numList) {
        if (i <= 0) {
            inputNotPositive()
        }
    }
    return numList
}

fun getCustomSign(input: String): String? {
    val regex = Regex("^//(.+)\\\\n")
    val matchResult = regex.find(input)
    if (matchResult == null) {
        undefineSign()
    }
    return matchResult?.groupValues?.get(1) // "//"와 "\n" 사이의 문자열 그대로 반환
}

fun sum(numList: List<Int>): Int {
    var result: Int = 0
    for (i in numList) {
        result += i
    }
    return result
}

fun getResult(result: Int) {
    print("결과 : ${result}")
}

fun inputNotPositive() {
    throw IllegalArgumentException("입력값이 양수가 아닙니다. ")
}

fun incorrectSign() {
    throw IllegalArgumentException("구분자와 양수가 아닌 문자가 입력되었습니다.")
}

fun undefineSign() {
    throw IllegalArgumentException("//와\n사이에 커스텀 구분자를 정의하지 않았습니다.")
}

fun invalidinput() {
    throw IllegalArgumentException("수식이 제대로 정의되지 않았습니다.")
}
