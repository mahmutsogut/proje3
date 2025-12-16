import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;   // <-- Bu satır hatayı çözdü
import java.util.Scanner;

public class Dosyaİslemleri {

    // Okuma (I/O Read) metodu
    public static List<String> notDosyasiniOku(String dosyaYolu) throws FileNotFoundException {
        List<String> satirlar = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(dosyaYolu))) {
            while (scanner.hasNextLine()) {
                satirlar.add(scanner.nextLine());
            }
        }
        return satirlar;
    }

    // Yazma (I/O Write) metodu. Map kullandığı için import edilmeliydi.
    public static void ogrenciListesiniYaz(String dosyaYolu, Map<String, Ogrenci> ogrenciKayitlari) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(dosyaYolu)) {
            writer.println("OGR_NO,AD,SOYAD,GIRIS_YILI,GANO");
            for (Ogrenci ogrenci : ogrenciKayitlari.values()) {
                writer.printf("%s,%s,%s,%d,%.2f\n",
                        ogrenci.getOgrenciNo(),
                        ogrenci.getAd(),
                        ogrenci.getSoyad(),
                        ogrenci.getGirisYili(),
                        ogrenci.getGano()
                );
            }
        }
    }

    // Dosya kontrol ve oluşturma yardımcı metodu
    public static void dosyaVarliğiniKontrolEtVeOlustur(String dosyaYolu) {
        File dosya = new File(dosyaYolu);
        if (!dosya.exists()) {
            try {
                if (dosya.createNewFile()) {
                    System.out.println("-> Bilgi: Gerekli dosya (" + dosyaYolu + ") otomatik olarak oluşturuldu.");
                }
            } catch (IOException e) {
                System.err.println("Hata: Dosya oluşturulamadı. Dizin izinlerini kontrol edin.");
            }
        }
    }
}