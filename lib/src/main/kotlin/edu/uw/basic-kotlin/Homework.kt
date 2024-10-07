package edu.uw.basickotlin

// write a "whenFn" that takes an arg of type "Any" and returns a String
fun whenFn(arg: Any): String {
    return when (arg) {
        "Hello" -> "world"
        "Bonjour" -> "Say what?"
        "Howdy" -> "Say what?"
        is String -> "I don't understand"
        0 -> "zero"
        1 -> "one"
        in 2..10 -> "low number"
        is Int -> "a number"
        else -> "I don't understand"
    }
}

// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add(lhs: Int, rhs: Int): Int = lhs + rhs

// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub(lhs: Int, rhs: Int): Int = lhs - rhs

// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp(lhs: Int, rhs: Int, op: (Int, Int) -> Int) = op(lhs, rhs)

// write a class "Person" with first name, last name and age
class Person(val firstName: String, val lastName: String, val age: Int) {
    // Constructor
    val debugString: String
        get() = "[Person firstName:$firstName lastName:$lastName age:$age]"
}

// write a class "Money"
class Money(amount: Int, currency: String) {
    // Validate amount and currency
    val amount: Int = if (amount >= 0) amount else throw IllegalArgumentException("Amount cannot be negative")
    val currency: String = if (currency in listOf("USD", "EUR", "CAN", "GBP")) currency else throw IllegalArgumentException("Invalid currency")

    // Conversion method
    fun convert(toCurrency: String): Money {
        if (toCurrency == this.currency) return this

        // conversion rates
        val rates = mapOf(
            "USD" to mapOf("GBP" to 0.5, "EUR" to 1.5, "CAN" to 1.25),
            "GBP" to mapOf("USD" to 2.0, "EUR" to 3.0, "CAN" to 2.5),
            "EUR" to mapOf("USD" to 2.0/3.0, "GBP" to 1.0/3.0, "CAN" to 5.0/6.0),
            "CAN" to mapOf("USD" to 0.8, "GBP" to 0.4, "EUR" to 1.2)
        )

        // Perform conversion
        val rate = rates[this.currency]?.get(toCurrency) ?: throw IllegalArgumentException("Unsupported conversion")
        return Money((this.amount * rate).toInt(), toCurrency)
    }

    // Overload + operator
    operator fun plus(other: Money): Money {
        val convertedOther = other.convert(this.currency)
        return Money(this.amount + convertedOther.amount, this.currency)
    }

    // formatted string representation
    override fun toString(): String = "$amount $currency"
}