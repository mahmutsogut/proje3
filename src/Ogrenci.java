import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Stream API kullanımı için

public class Ogrenci extends Kullanici implements Raporlanabilir {
    private String ogrenciNo;
    private int girisYili;
    private List<LisansNotu> notListesi; // Collections Framework

    // --- CONSTRUCTOR ---
    public Ogrenci(String ogrenciNo, String ad, String soyad, int girisYili) {
        setAd(ad); // Üst sınıfın kontrollü setter'ı
        setSoyad(soyad); // Üst sınıfın setter'ı
        setGirisYili(girisYili); // Kendi kontrollü setter'ı
        this.ogrenciNo = ogrenciNo;
        this.notListesi = new ArrayList<>();
    }

    // --- KALITIM VE ABSTRACT METOTLAR (Override) ---
    @Override
    public String getKullaniciTuru() { return "Ogrenci"; }

    @Override
    public void bilgiGoster() { /* Basitleştirilmiş */ }

    // --- GANO VE AKTS MANTIK METOTLARI ---

    public void notEkle(LisansNotu not) { this.notListesi.add(not); }

    // AKTS Ağırlıklı GANO hesaplama (Private metot)
    private double ganoHesapla() {
        if (notListesi.isEmpty()) return 0.0;
        double toplamPuan = 0;
        double toplamAkts = 0;
        for (LisansNotu not : notListesi) {
            toplamPuan += not.getAkts() * not.gpaPuanıGetir();
            toplamAkts += not.getAkts();
        }
        return (toplamAkts > 0) ? toplamPuan / toplamAkts : 0.0;
    }

    // GANO'yu dışarıya açan public metot (Getter olarak kullanılır)
    public double getGano() {
        return ganoHesapla();
    }

    // Toplam AKTS hesaplayan metot
    public double getToplanAkts() {
        return notListesi.stream()
                .mapToDouble(NotKaydi::getAkts)
                .sum();
    }

    // Ders adına göre notları getiren filtreleme metodu
    public List<LisansNotu> getNotlarByDersAdi(String dersAdi) {
        return notListesi.stream()
                .filter(n -> n.getDersAdi().equalsIgnoreCase(dersAdi))
                .collect(Collectors.toList());
    }

    // --- RAPORLANABİLİR INTERFACE METOTLARI ---
    @Override
    public String transkriptOlustur() { /* ... GANO ve not listesi çıktısı ... */ return ""; }
    @Override public String raporOlustur() { return transkriptOlustur(); }
    @Override public void raporaEkle(String icerik) { /* ... */ }
    @Override public boolean raporGonderildiMi() { return false; }


    // --- GETTER METOTLARI ---
    public String getOgrenciNo() { return ogrenciNo; }
    public int getGirisYili() { return girisYili; }
    public List<LisansNotu> getNotListesi() { return notListesi; }

    // --- SETTER METOTLARI (Kontrollü) ---
    public void setOgrenciNo(String ogrenciNo) {
        this.ogrenciNo = ogrenciNo;
    }

    public void setGirisYili(int yil) throws GecersizVeriException {
        if (yil < 2000 || yil > 2025) {
            throw new GecersizVeriException("Giriş yılı 2000-2025 aralığında olmalıdır.");
        }
        this.girisYili = yil;
    }
}