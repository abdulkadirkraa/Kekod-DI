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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var analyticsAdapter: AnalyticsAdapter
    /*bu adapter'ı inject ederken private yaparsak hata verir. çünkü private injection'ları desteklemiyor.
    bunu böyle çalıştırınca da hata alırız çünkü: analyticsAdapter'ı inject edicez class'ına bakınca da ihtiyaç duyacağı şey de
    bir service. Ama analyticsAdapter'ın nasıl üretileceğiyle ilgili, üretilirken bu service'ı nasıl alacağımla ilgili bana bir
    bilgi vermedin diyor. Şimdi hilt bize field injection yapsın diyoruz ama inject edeceğimiz analyticsAdapter'ın hilt'siz olan
    onCreate'deki haliyle mi ya da farklı bir yöntemle mi nasıl oluşturulacağını hilt'e söylemedik. Hilt de o yüzden bunu neyle
    inject edicem söylemen lazım diyor. Sonrasında da bizim hilt'e birkaç bilgi daha vermemiz lazım.
    AnalyticsAdapter sınıfının içine gelip madem constructor'ında service'yi bekliyorsun ya hilt'e bu AnalyticsService'yi
    constructor'ıma inject ettim de diyor. O yüzden constructor injection kullanıyoruz.
    Artık hilt biliyor ki adapter sınıfını üretmeye çalışırken service'ye ihtiyacım var.
    Bunları yapıp run ettiğimizde de hata alırız, hala bir bilgi eksik. AnalyticsService'i nasıl inject edicem. Dolayısıyla bunu da
    söylemem lazım. Biz şimdiye kadar ya constructor injection ya da field injection kullanıyoruz dedik ama şimdi elimizde service
    var. Bu durumda service injection'ı nasıl yapıcaz?
    Interface'e constructor injection da uygulayamam içinde state de tutmazlar o yüzden field injection da yapamam. Bu durumda hilt
    bu service'ı kullanan bir tane implementation sınıfı yaz diyor. Interface'iniz neyse onun service impelantasyonu gibi düşünün.
    Bu impl class da interface'ini implemente edecek. Miras alıp override ettiğimiz fonksiyonda yapmamız gereken işlem neyse onu
    yaparız. Bunu yaptıktan sonra bu impl class kendi içerisinde de farklı injection'lara ihtiyaç duyacaksa onları da yazarız,
    yönetiriz.
    Sadece elimizde impl class vaarken de şöyle bir problemimiz var: Ben bu AnalyticsServiceImpl sınıfını bu AnalyticsService interface
    çağrıldığında kullanacağımı hilt'e nasıl söyleyebilirim?
    Class'larda injection yapmak çok basit ya constructor ya da field injection yapıyorsun.Hilt sadece senden şunu istiyor ister
    constructor'dan ister field'dan injection yapıyorsan bu inject etitğin şeyin de nasıl oluşturulacağını söyle bana diyor. Eğer bu
    bir class ise class'ın içinde constructor injection olmuyacaksa problem yok sadece adapter'ı vermen yeterlidir. Ama sen bunun
    içinde bir service'e ihtiyaç duyduğunu söyledin, bu bir sınıf değil bir interface.O zaman da interface'ler için hilt özel bir
    yapım var diyo o şablonda kullanman lazım. Bu interface'i implemente edeceğin bir sınıf yaz diyor. İlgili interface'i implemente
    edip override edilecek fonksiyonu yazıp yapmayı istediğimiz işlemi o fonksiyona yazıyoruz. Sonrasında da hilt,hiltmodule sınıfına
    ihtiyaç duyuyor. Bana bir tane abstract sınıf yaz diyor (ona genelde projelerde di diye package açılıp içine yazılır) bunun içine
    hilt module sınıfınızı yazmanız gerekiyor.
    Hilt bunu hiltmodule olarak kullanacağım sınıf olduğunu bana anlat diyor. Bunun için @Module diye annotation veriyor bize. Bu
    annotation'u kullandığımızda hilt bunun dependency'leri yönettiğimiz bir sınıf olduğunu bilecek. Ve bunu yaparken bir bilgiye
    daha ihtiyacı var. Siz module sınıfı yazdığınızda hilt bu içerisine yazacağımız dependency'leri de hangi lifecycle aralığında
    yöneteceğimizi de ona söylememizi istiyor. Bunun için de @InstallIn annotation'unu yazıyoruz. Bu @InstallIn'ın içerisine de
    android sınıflarına karşılık gelen hilt component class isimlerini yazıyoruz. Bu şu demek: ben bu module'in içerisindeki dependency
    leri sadece activity sınıflarında kullanıcaz diye sınırlama yapmış oluyoruz (çünkü ActivityComponent::class verdik).
    Dolayısıyla biz bir interface'i dependency olarka kullanmak istiyorsak (AnalyticsAdapter içerisinde bir interface'imiz var),
    interface'i inject edeceğimizi dependency olarak kullanacağımızı söyledik. Hilt de bize o zaman bu interface'i kullanan bir
    implementasyon sınıfı yaz dedi içerisinde fonksiyonu override ettik ve fonksiyon yaptırmamız gereken iş neyse onla ilgili kodu yazdık.
    Sonra bize diyecek ki module sınıfını aç. Bu module sınıfını şundan dolayı kullanacaksın diyecek :
    İki temel amacımız var constructor inject edemediğimiz yapılar var ya bunlardan biri işte interface'ler. Dolayısıyla interface'leri
    bu module'ler yardımıyla inject edeceğiz. Bir de değiştiremeyeceğimiz sınıflar olacak mesela retrofit sınıfı bize kütüphaneden
    verilen bir sınıf, retrofit'in instance'ını oluşturucaz biz. Onu da yine bu module'ler yardımıyla oluşturucaz biz.
    Hiç değiştiremediğimiz, modifiye edemediğimiz sınıflara da constructor injection yapamayız interface'lerin de zaten bir constructor'ı
    olmadığı için onlara da constructor injection yapamıyoruz. Dolayısıyla constructor injection yapamdığınız sınıflar için de module
    sınıfı oluşturuyorsunuz abstract class diyip @Module annotation'ununu vererek. Sonra da buna @InstallIn diyerek hangi hilt
    component'i ile beraber kullanılacağını söylüyorsunuz bu sayede bunun kullanımını scoope'unu sınırlıyorsunuz. Şu componentlerle
    beraber sadece activity'lerde kullanıcaz diye söylüyoruz.
    Sonrasında module abstract class'ın da son bir işlemimiz kalıyor bu interface service'ın bu impl sınıfıyla beraber kullandılığını
    hilt'e söylememiz lazım. Bunu da genel isimlendirme mantığından dolayı binds isimli bir fonsiyon yazarız. Biz neyi binds etmek
    istiyoruz service interface'imizi bind + interface ismi isimlendirme standartı bu. Bunun geri dönüşünü hangi service'ı bind etmek
    istiyorsanız onu veriyorsunuz. Sonrasında da bu service geri döndürebilmek için hangi impl sınıfını kullandıysanız bunu da
    parametre olarak veriyorsunuz. Yani bu module sınıfının fonksiyonu impl sınıfını parametre olarak aldı bu impl sınfın implemente
    ettiği interface'i bize geri verecek. Bunları yaptıktan sonra bir de fonksiyona hilt için yazıldığı için @Binds annotation'ununu
    yazıyoruz.

    Şimdi hilt şöyle çalışacak arka planda kodlar generate ediliyor dedik ya arka planda kodları generate ederken bakıcak ki sen bir
    module sınıfı yazmışsın demek ki bu module sınıfının içerisinde dependency olarak bir şeyler koydun constructor olarak inject
    edilemeyen. O zaman içerisine binds ile etiketlenmşi fonksiyonlara bir bakıcak bu binds ile etiketlenmiş fonksiyon AnaylyticsService
    veriyor içerisinde de AnalyticsService'ın implementation'ınını bekliyor. O zaman hilt diyecek ki ben bu service'in kendisini
    alabilmem için bu impl'ın nesnesini verirsem service'ı bana geri döndürecek bunun generate'ini yazmam lazım. Arka planda bu
    fonksiyonun generate edilmiş halini oluşturacak. İçerisinde AnalyticsServiceImpl sınıfının nesnesini oluşturucak vercek. O nesnesini
    oluşturduğumuz sınıf da AnalyticsService olarak geri döndürülüp bize verilecek.
    Aslında o binds fonksiyonun arka planda yaptığı şey de return analyticsServiceImpl nesnesini geri döndürmek çünkü superType'ı
    AnalyticsService interface'idir.


    ** AndroidEntryPoint'leri verdiğinizde bunun miras aldığı sınıfların da bu annotation'a sahip olması lazım. Bunun bir istisnası
    var eğer sınıf abstract ise AndroidEntryPoint annotation'u ihtiyacı olmuyor.
    */

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
//        analyticsAdapter = AnalyticsAdapter(object : AnalyticsService{
//            override fun analyticsMethods() {
//                Log.i("AnalyticsAdapter","AnalyticsAdapter run")
//            }
//        })
        /*
        Peki hilt bu işlemi nasıl yapıyor?
        @Inject annotaion'u ile.
         */
        analyticsAdapter.service.analyticsMethods()
    }
}