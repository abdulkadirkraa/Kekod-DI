package com.abdulkadirkara.kekod_di

class Enginee {
    fun start(){
        println("start engine")
    }
}

class Car(){
    private val enginee: Enginee = Enginee()

    fun start(){
        enginee.start()
    }
}

fun main(){
    val car = Car()
    car.start()
}

/*
Car class'ının içindeki Enginee() class'ına dependency diyoruz yani bağımlılık. Car class'ı Enginee class'ına bağımlı.
Bunun bize yarattığı problem olarak farklı farklı motor tiplerini de car sınıfına implemente edecek olsaydık. Enginee'i interface yapıp
onu implemente eden alt class'lar oluştururduk hibrit,elektrikli,gaz enginee olarak yapardık. onu da SİmpleDI2'de yapıyorum.
 */