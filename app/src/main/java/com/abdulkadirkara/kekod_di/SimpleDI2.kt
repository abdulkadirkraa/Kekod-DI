package com.abdulkadirkara.kekod_di

interface Engine2 {
    fun start()
}

class GasEnginee2(): Engine2{
    override fun start() {
        println("gas engine")
    }
}

class ElectricEnginee2() : Engine2{
    override fun start() {
        println("electric engine")
    }
}

class HybridEngine2() : Engine2 {
    override fun start() {
        println("hybrid engine")
    }
}

class Car2(private val engineType: EngineTpe){
    private val gasEngine = GasEnginee2()
    private val electricEngine = ElectricEnginee2()
    private val hybridEngine = HybridEngine2()

    fun start(){
        when(engineType){
            EngineTpe.GAS -> gasEngine.start()
            EngineTpe.HYBRID -> hybridEngine.start()
            EngineTpe.ELECTRIC -> electricEngine.start()
        }
    }
}

enum class EngineTpe{
    GAS,ELECTRIC,HYBRID
}

fun main(){
    val car1= Car2(EngineTpe.GAS)
    car1.start()

    val car2= Car2(EngineTpe.HYBRID)
    car2.start()

    val car3= Car2(EngineTpe.ELECTRIC)
    car3.start()
}

/*
Burdaki problemde de biz tüm bu gas,electric,hybrid engine sınıflarına bağımlı haldeyiz. Yarın bir gün yeni bir engine type daha
geldiğinde nesneini oluşturmak,start fonksiyona koymak,enum class'a type'a ekliyoruz, kullanırken implemantasyonunu yapıyoruz.
Dependency inversion losely cohesion için gas,electric,hybrid engine'e biz sıkı sıkıya bağımlıyız tightly couple.
Birinci prensip bize bu bağımlılıkları burdan uzaklaştır diyor. Çünkü bunlara tightly bağımlı olduğumuz sürece start'ın yapacağı şey
engine'ı start etmekken oluşturduğumuz nesnelerin bağımlılıkları yüzünden type'a göre bir business logic yazmasına sebep olduk.
Dolayısıyla burdaki cohesion'u yavaş yavaş düşürüyorsun. Çünkü yarın bir gün bu bağımlı olunan sınıfları bir şeyleri değişebilir ve
ekstra helper fonksiyonlar yazarak şişirmene class'ı neden olabilir. Car sınıfının bunlardan haberdar olmasına ihtiyacımız yok.
Biz istiyoruz ki car sınıfı bir arabanın üretilip başlatıldığı yer olsun. Ama ilgili motorların nasıl üretildiğiyle ilgili bir bilgiye
sahip olmasına gerek bile yok,olmamalı. Zaten bu kısım da dependency inversion dediğimiz şey.

Inversion of controlde de diyorduk ki; bu sınıfların üretilmesi veya fonksiyonlarını harici arayüz üzerinden yönetmek diyoruz.

Peki bunu biraz daha doğru yönetmek isteseydik nasıl yapıcaktık? SımpleDI3'te o da
 */

