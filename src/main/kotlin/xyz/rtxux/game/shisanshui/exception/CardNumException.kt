package water.exception

class CardNumException(need: Int, now: Int) : Exception("CardNumException need ${need} now is ${now}")