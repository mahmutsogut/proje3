import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;

public class Main {

    // Öğrenci kayıtları: Öğrenci No -> Ogrenci nesnesi (Collections: Map)
    private Map<String, Ogrenci> ogrenciKayitlari = new HashMap<>();
    // Ders kayıtları: (Collections: List)
    private List<Ders> dersListesi = new ArrayList<>();

    public static void main(String[] args) {
        Main sistem = new Main();
        sistem.baslat();
    }

    public void baslat() {
        Scanner sc = new Scanner(System.in);
        int secim;

        while (true) {
            menuGoster();
            System.out.print("Seçiminiz : ");

            if (sc.hasNextInt()) {
                secim = sc.nextInt();
                sc.nextLine();

                switch (secim) {
                    case 1: yeniOgrenciEkle(sc); break;
                    case 2: notlariDosyadanOkuVeIsle(); break;
                    case 3: transkriptGoster(sc); break;
                    case 4: ogrenciBilgileriniGoster(sc); break;
                    case 5: dersEkle(sc); break;
                    case 6: enBasariliOgrenciyiBul(); break;
                    case 7: dersleriListele(); break;
                    case 8: ogrenciListesiniDosyayaYaz(); break;
                    case 0:
                        System.out.println("Sistem Kapatılıyor...");
                        return;
                    default:
                        System.out.println("Hata! Geçersiz seçenek.");
                        break;
                }
            } else {
                sc.nextLine();
            }
        }
    }

    private void menuGoster() {
        System.out.println("1. Yeni Öğrenci Ekleme");
        System.out.println("2. Notları Dosyadan Oku ve İşle ");
        System.out.println("3. Transkript  Göster");
        System.out.println("4. Öğrenci Bilgilerini Göster ");
        System.out.println("5. Ders Ekleme");
        System.out.println("6. En Başarılı Öğrenciyi Bul ");
        System.out.println("7. Kayıtlı Dersleri Listele ");
        System.out.println("8. Kayıtlı Öğrenci Listesini Dosyaya Yaz ");
        System.out.println("0. Çıkış");
    }

    // İşlev 1: Yeni Öğrenci Ekleme
    private void yeniOgrenciEkle(Scanner sc) {
        System.out.print("Öğrenci No: ");
        String no = sc.nextLine();
        System.out.print("Ad: ");
        String ad = sc.nextLine();
        System.out.print("Soyad: ");
        String soyad = sc.nextLine();
        System.out.print("Giriş Yılı: ");
        int yil = sc.nextInt();
        sc.nextLine();

        try {
            // Öğrenci nesnesi oluşturulur, constructor'da hata kontrolü yapılır.
            Ogrenci yeniOgrenci = new Ogrenci(no, ad, soyad, yil);
            ogrenciKayitlari.put(no, yeniOgrenci);
            System.out.println(yeniOgrenci.tamAdGetir() + " sisteme eklendi.");
        } catch (GecersizVeriException e) {
            System.err.println("Ekleme başarısız: " + e.getMessage());
        }
    }

    // İşlev 2: Notları Dosyadan Oku ve İşle (I/O Read)
    private void notlariDosyadanOkuVeIsle() {
        String dosyaAdi = "not_kayitlari.txt";
        int basariliSayisi = 0;
        try {
            List<String> notSatirlari = Dosyaİslemleri.notDosyasiniOku(dosyaAdi); // Dosyadan okuma
            for (String satir : notSatirlari) {

                
                String[] veriler = satir.split(","); // Veri ayrıştırma
                if (veriler.length != 5) continue;
                String ogrenciNo = veriler[0].trim();
                String dersAdi = veriler[1].trim();
                int vize = Integer.parseInt(veriler[2].trim());
                int finalNotu = Integer.parseInt(veriler[3].trim());
                double akts = Double.parseDouble(veriler[4].trim());

                Ogrenci ogrenci = ogrenciKayitlari.get(ogrenciNo);
                if (ogrenci != null) {
                    // Alt sınıf constructor'ı ile LisansNotu nesnesi oluşturulur
                    LisansNotu yeniNot = new LisansNotu(dersAdi, vize, finalNotu, akts);
                    ogrenci.notEkle(yeniNot);
                    basariliSayisi++;
                }
            }
            System.out.println("\n✅ Dosyadan not okuma tamamlandı. Toplam " + basariliSayisi + " not başarıyla işlendi.");
        } catch (FileNotFoundException e) {
            System.err.println("Hata: " + dosyaAdi + " dosyası bulunamadı.");
        } catch (NumberFormatException e) {
            System.err.println("Hata: Dosya içeriğinde sayısal olmayan bir değer var.");
        } catch (GecersizVeriException e) { // Özel Exception yakalama
            System.err.println("Hata: Geçersiz not/AKTS değeri bulundu: " + e.getMessage());
        }
    }

    // İşlev 3: Transkript (GANO) Göster
    private void transkriptGoster(Scanner sc) {
        System.out.print("Transkripti görüntülenecek öğrenci no: ");
        String no = sc.nextLine();
        Ogrenci ogrenci = ogrenciKayitlari.get(no);

        if (ogrenci != null) {
            System.out.println(ogrenci.transkriptOlustur()); // Interface metodu çağrılır
        } else {
            System.out.println("Hata! Öğrenci bulunamadı.");
        }
    }

    // İşlev 4: Öğrenci Bilgilerini Göster (Detaylı)
    private void ogrenciBilgileriniGoster(Scanner sc) {
        System.out.print("Bilgilerini görüntüleyeceğiniz öğrenci no: ");
        String no = sc.nextLine();
        Ogrenci ogrenci = ogrenciKayitlari.get(no);

        if (ogrenci != null) {
            System.out.println("Adı Soyadı: " + ogrenci.tamAdGetir());
            System.out.println("Öğrenci Numarası: " + ogrenci.getOgrenciNo());
            System.out.println("Giriş Yılı: " + ogrenci.getGirisYili());
            System.out.println("Kullanıcı Tipi: " + ogrenci.getKullaniciTuru());
        } else {
            System.out.println("Hata! Öğrenci bulunamadı.");
        }
    }

    // İşlev 5: Ders Ekleme
    private void dersEkle(Scanner sc) {
        System.out.print("Ders Kodu: ");
        String kod = sc.nextLine();
        System.out.print("Ders Adı: ");
        String ad = sc.nextLine();
        System.out.print("Ders Kredisi (1-10): ");
        int kredi = sc.nextInt();
        sc.nextLine();

        try {
            if (kredi < 1 || kredi > 10) {
                throw new GecersizVeriException("Kredi değeri 1-10 aralığında olmalıdır.");
            }
            Ders yeniDers = new Ders(kod, ad, kredi);
            dersListesi.add(yeniDers); // Listeye ekleme
            System.out.println(yeniDers.getAd() + " dersi başarıyla eklendi.");
        } catch (GecersizVeriException e) {
            System.err.println("Ders ekleme hatası: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Hata! Kredi sayısal olmalıdır.");
            sc.nextLine();
        }
    }

    // İşlev 6: En Başarılı Öğrenciyi Bul (Karşılaştırma)
    private void enBasariliOgrenciyiBul() {
        System.out.println("\n--- EN BAŞARILI ÖĞRENCİ ---");
        Ogrenci enIyiOgrenci = null;
        double enYuksekGano = -1.0;

        for (Ogrenci ogrenci : ogrenciKayitlari.values()) {
            double gano = ogrenci.getGano(); // Public GANO metodu çağrılır

            if (gano > enYuksekGano) { // Karşılaştırma operatörü
                enYuksekGano = gano;
                enIyiOgrenci = ogrenci;
            }
        }

        if (enIyiOgrenci != null && enYuksekGano != 0.0) {
            System.out.println("Öğrenci: " + enIyiOgrenci.tamAdGetir() + " (GANO: " + String.format("%.2f", enYuksekGano) + ")");
        } else {
            System.out.println("Not kaydı olan öğrenci bulunmamaktadır.");
        }
    }

    // İşlev 7: Kayıtlı Dersleri Listele (Döngü)
    private void dersleriListele() {
        System.out.println("\n--- KAYITLI DERSLER ---");
        if (dersListesi.isEmpty()) {
            System.out.println("Sistemde kayıtlı ders bulunmamaktadır.");
            return;
        }
        for (Ders ders : dersListesi) { // Döngü
            System.out.println("-> " + ders.getAd() + " (" + ders.getDersKodu() + ") - AKTS Kredi: " + ders.getKredi());
        }
    }

    // İşlev 8: Ders Bazında Başarı Oranı (Filtreleme ve Sayma)
    private void dersBasindaBasariOrani(Scanner sc) {
        System.out.print("İstatistik istenecek ders adını girin: ");
        String dersAdi = sc.nextLine().trim();
        int toplamOgrenci = 0;
        int gecenOgrenci = 0;

        for (Ogrenci ogrenci : ogrenciKayitlari.values()) {
            List<LisansNotu> notlar = ogrenci.getNotlarByDersAdi(dersAdi); // Öğrenci sınıfındaki filtreleme metodu
            for (LisansNotu not : notlar) {
                toplamOgrenci++;
                if (not.durumGetir().equals("GEÇTİ")) { // Koşullu Sayım
                    gecenOgrenci++;
                }
            }
        }
        if (toplamOgrenci > 0) {
            double basariOrani = ((double) gecenOgrenci / toplamOgrenci) * 100;
            System.out.printf("%s dersinde başarı oranı: %d/%d (%%%s) öğrenci geçti.\n",
                    dersAdi, gecenOgrenci, toplamOgrenci, String.format("%.2f", basariOrani));
        } else {
            System.out.println("Bu derse ait not kaydı bulunmamaktadır.");
        }
    }

    // İşlev 9: En Çok AKTS Toplayan Öğrenciyi Bul (Toplamsal Hesaplama)
    private void enCokAktsToplayanBul() {
        System.out.println("\n--- EN ÇOK AKTS TOPLAYAN ÖĞRENCİ ---");
        Ogrenci enAktsliOgrenci = null;
        double maxAkts = -1.0;

        for (Ogrenci ogrenci : ogrenciKayitlari.values()) {
            double toplamAkts = ogrenci.getToplanAkts(); // Öğrenci sınıfındaki toplama metodu
            if (toplamAkts > maxAkts) {
                maxAkts = toplamAkts;
                enAktsliOgrenci = ogrenci;
            }
        }
        if (enAktsliOgrenci != null && maxAkts > 0) {
            System.out.println("En çok AKTS toplayan öğrenci: " + enAktsliOgrenci.tamAdGetir() + " (Toplam AKTS: " + String.format("%.1f", maxAkts) + ")");
        } else {
            System.out.println("Kayıtlı notu olan öğrenci bulunmamaktadır.");
        }
    }

    // İşlev 10: Kayıtlı Öğrenci Listesini Dosyaya Yaz (I/O Write)
    private void ogrenciListesiniDosyayaYaz() {
        String dosyaAdi = "ogrenci_raporu.csv";
        try {
            // DosyaIslemleri sınıfındaki static metot çağrılır
            Dosyaİslemleri.ogrenciListesiniYaz(dosyaAdi, ogrenciKayitlari);
            System.out.println("\n✅ Öğrenci listesi, GANO bilgileriyle birlikte " + dosyaAdi + " dosyasına başarıyla yazıldı.");
        } catch (FileNotFoundException e) {
            System.err.println("Hata: Dosyaya yazma işlemi başarısız oldu. Dizini kontrol edin.");
        }
    }
}