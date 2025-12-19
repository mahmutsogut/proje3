import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Dosyaİslemleri {

    public static List<String> notDosyasiniOku(String dosya) throws FileNotFoundException {
        List<String> satirlar = new ArrayList<>();

        /* Try-with-resources yapısı: Scanner işi bitince otomatik kapanır */
        try (Scanner scanner = new Scanner(new File(dosya))) {
            while (scanner.hasNextLine()) {
                satirlar.add(scanner.nextLine());
            }
        }
        return satirlar;
    }

    public static void ogrenciListesiniYaz(String dosya, Map<String, Ogrenci> ogrenciKayitlari) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(dosya)) {
            // Okuma yaparken hata almamak için başlık satırını basit tutalım
            writer.println("NO,AD,SOYAD,YIL");
            for (Ogrenci ogrenci : ogrenciKayitlari.values()) {
                writer.println(
                        ogrenci.getOgrenciNo() + "," +
                                ogrenci.getAd() + "," +
                                ogrenci.getSoyad() + "," +
                                ogrenci.getGirisYili()
                );
            }
        }
    }

    public static void dosyaVarmi(String dosya) {
        File kontrolDosya = new File(dosya);
        if (!kontrolDosya.exists()) {
            try {
                kontrolDosya.createNewFile();
            } catch (IOException e) {
                System.err.println("Dosya oluşturma hatası.");
            }
        }
    }
}