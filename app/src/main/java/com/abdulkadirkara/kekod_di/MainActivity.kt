package com.abdulkadirkara.kekod_di

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abdulkadirkara.kekod_di.analytics.AnalyticsAdapter
import com.abdulkadirkara.kekod_di.analytics.AnalyticsService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var analyticsAdapter: AnalyticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        /*
        Burdaki problemde biz analytcisAdapter'ı içerde oluşturduk nesnesini ve yapacağı işi de buraya yazdık.
        Şimdi bu kod niye yanlış veya nasıl yazılmalıydıyı düşündüğümüzde main activity içerisindeki onCreate fonksiyonunun görevi ne?
        Şu an bu koda bakarak activity açılır açılmaz adapter nesnesini oluşturuyoruz ve bir service donksiyonu çağrımından sorumlu.
        Yani buraya onCreate'in olmayan bir iş yükledik dolayısıyla cohesion'ununu düşürmüş olduk.
        Peki bu niye tehlikeli?
        Ben bu adapter'ın fonksiyonunu tüm activity'lerde kullanacaksam tüm activity'lere yazmamız gerekiyor ve yarın bir gün bu
        adapter sınıfı değişecek belirli şeyleriyle veya direk farklı bir kütüphaneye geçilecek. Böyle bir durumda adapter'ın
        kullanıldığı ilgili bütün yerlerde bu değişikliği yapmam gerekecek.
        Dolayısıyla hilt'i kullanma seebimiz bu. Hilt bize madem böyle adapter ile kullanım yapacaksan adapter'ın nesne oluşturma
        işlemini burda yapma bunu ben sana vereyim diyor.
         */
        analyticsAdapter = AnalyticsAdapter(object : AnalyticsService{
            override fun analyticsMethods() {
                Log.i("AnalyticsAdapter","AnalyticsAdapter run")
            }
        })
        analyticsAdapter.service.analyticsMethods()
    }
}