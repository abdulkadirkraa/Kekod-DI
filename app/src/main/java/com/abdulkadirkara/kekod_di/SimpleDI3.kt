package com.abdulkadirkara.kekod_di

interface Engine3 {
    fun start()
}

class GasEnginee3(): Engine3{
    override fun start() {
        println("gas engine")
    }
}

class ElectricEnginee3() : Engine3{
    override fun start() {
        println("electric engine")
    }
}

class HybridEngine3() : Engine3 {
    override fun start() {
        println("hybrid engine")
    }
}

class Car3(private val engine: Engine3){//constructor injection

    fun start(){
       engine.start()
    }
}

fun main(){
    val gasengine3 = GasEnginee3()
    val car1= Car3(gasengine3)
    car1.start()

    val hybridEngine3 = HybridEngine3()
    val car2= Car3(hybridEngine3)
    car2.start()

    val electricEnginee3 = ElectricEnginee3()
    val car3= Car3(electricEnginee3)
    car3.start()

    val plane3 = Plane3()
    plane3.setEngine(electricEnginee3)
    plane3.start()
}

class Plane3(){
    private lateinit var engine: Engine3//field injection
    //burdaki engine private olarak çalıştırırsak sorun olmaz ama private kaldırırsak ide hata göstermiyor fakat çalıştırınca hata veriyor neden?
    //private değişkenin arka planda set/get metotları yazılmıyordu. ama biz bunu private yapmazsak arka planda get ve set'i olucak e biz
    //class'ın içinde de set metodunu yazdığımızdan bu ikisi birbiriyle çakışıyor diye hata verir. samejvmsignature hatası verir.
    //metot adımızı burda değişsek mesela setEngine2 yazsak çalışırdı ama çirkin bir kod.

    fun setEngine(engine: Engine3){
        this.engine = engine
    }
    fun start(){
        engine.start()
    }
}

/*
Class'ın aldığı engine type yerine val engine: Engine yazıp superType'ı ile değiştirirsek main fonksiyondaki çağırımda, nesne oluşturmada
o parametre yerine dışardan verebiliriz.

Şu aki şekliyle car sınıfı ile Engine'den üretilen herhangi bir sınıf losely couple yani birbirine gevşek şekilde bağlı.
Bu bize car sınıfı bir motorun asla nasıl üretildiği ile alakalı bir bilgiye sahip değil.

Biz car değil uçak class'ı oluşturup yine  bu motorlara ihtiyacımız olduğunda SimpleDI2'dekini kulanıyor olsaydık tekrar eden kodlar boldu.
Ama SimpleDI3'deki gibi yaparsak
class Plane(private val engine: Engine3){
    fun start() = engine.start()
}
bu kadar olacaktı.

Bağımlılık olan engine'ı alıp başka yerden alacağımız şekilde bağımlılığı azaltmış olduk dışardan alıp class'ı içine inject ediyoruz.
 */
