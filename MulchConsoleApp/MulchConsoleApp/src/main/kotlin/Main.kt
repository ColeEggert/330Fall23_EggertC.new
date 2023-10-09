import kotlin.math.ceil

data class PlantingBedDimensions(val length: Double, val width: Double, val depth: Double)

interface MulchPricer {
    fun calculatePrice(cubicYards: Int): Double
}

class CubicYardMulchPricer : MulchPricer {
    override fun calculatePrice(cubicYards: Int): Double {
        val price: Double
        if (cubicYards <= 3) {
            price = 33.50 * cubicYards
        } else if (cubicYards < 10 && cubicYards > 3) {
            price = 31.50 * cubicYards
        } else {
            price = 29.50 * cubicYards
        }
        return price
    }
}

class CubicFootMulchPricer : MulchPricer {
    override fun calculatePrice(cubicYards: Int): Double {
        val cubicFeet = cubicYards * 27
        val price: Double
        if (cubicFeet <= 5 * 2) {
            price = 3.97 * cubicFeet.toDouble()
        } else if (cubicFeet < 10 * 2 && cubicFeet > 5 * 2) {
            price = 3.47 * cubicFeet.toDouble()
        } else {
            price = 2.97 * cubicFeet.toDouble()
        }
        return price
    }
}

class MulchOrder(private val bedDimensionsList: MutableList<PlantingBedDimensions>) {
    private var pricer: MulchPricer? = null
    private var isBulkPurchase: Boolean = true

    fun addPlantingBedDimensions(dimensions: PlantingBedDimensions) {
        bedDimensionsList.add(dimensions)
    }

    fun getTotalYards(): Int {
        val totalCubicYards = ceil(bedDimensionsList.sumOf { (it.length / 3) * (it.width / 3) * (it.depth / 36) }).toInt()
        return totalCubicYards
    }

    fun getTotalFeet(): Int {
        return getTotalYards() * 27
    }

    fun setPurchasePreference(isBulkPurchase: Boolean) {
        this.isBulkPurchase = isBulkPurchase
        pricer = if (isBulkPurchase) {
            CubicYardMulchPricer()
        } else {
            CubicFootMulchPricer()
        }
    }

    fun calculateTotalPrice(): Double {
        return pricer?.calculatePrice(getTotalYards()) ?: 0.0
    }

    fun printOrderDetails() {
        println("Order Details:")
        println("--------------")
        for ((index, dimensions) in bedDimensionsList.withIndex()) {
            println("Planting Bed $index:")
            println("Dimensions: Length ${dimensions.length} ft x Width ${dimensions.width} ft x Height ${dimensions.depth} in")
            println("------------------------------------------------------------")
        }
        println("Total Cubic Yards: ${getTotalYards()}")
        println("Total Cubic Feet: ${getTotalFeet()}")
        println("Total Price: $${calculateTotalPrice()}")
        println("-------------------------------")
    }
}

fun createMulchOrder(): MulchOrder {
    val order = MulchOrder(mutableListOf())

    order.addPlantingBedDimensions(PlantingBedDimensions(30.0, 10.0, 5.0))
    order.addPlantingBedDimensions(PlantingBedDimensions(43.0, 14.0, 4.0))

    order.setPurchasePreference(true)

    return order
}

fun main() {
    val order = createMulchOrder()
    order.printOrderDetails()
}


