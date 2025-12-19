import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Ogrenci extends Kullanici implements Raporlanabilir, Loglanabilir {
    private String ogrenciNo;
    private int girisYili;

    private List<NotKaydi> notListesi;

    public Ogrenci(String ogrenciNo, String ad, String soyad, int girisYili) {
        setAd(ad);
        setSoyad(soyad);
        try {
            setGirisYili(girisYili);
        } catch (GecersizVeriException e) {
            System.err.println("Yıl hatası: " + e.getMessage());
        }
        this.ogrenciNo = ogrenciNo;
        this.notListesi = new ArrayList<>();
    }

    @Override
    public String getKullaniciTuru() {
        return "Ogrenci";
    }

    @Override
    public void bilgiGoster() {
        System.out.println("Öğrenci: " + tamAdGetir());
    }

    public void notEkle(NotKaydi not) {
        this.notListesi.add(not);
    }

    private double ganoHesapla() {
        if (notListesi.isEmpty()) return 0.0;
        double toplamPuan = 0;
        double toplamAkts = 0;

        for (NotKaydi not : notListesi) {
            toplamPuan += not.getAkts() * not.gpaPuanıHesapla(not.yuzlukOrtalamaHesapla());
            toplamAkts += not.getAkts();
        }
        return (toplamAkts > 0) ? toplamPuan / toplamAkts : 0.0;
    }

    public double getGano() {
        return ganoHesapla();
    }

    public double getToplamAkts() {
        return notListesi.stream()
                .mapToDouble(NotKaydi::getAkts)
                .sum();
    }

    public List<NotKaydi> getNotlarByDersAdi(String dersAdi) {
        return notListesi.stream()
                .filter(n -> n.getDersAdi().equalsIgnoreCase(dersAdi))
                .collect(Collectors.toList());
    }

    @Override
    public String transkriptOlustur() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n          TRANSKRİPT BELGESİ           \n");
        sb.append("Öğrenci No : ").append(ogrenciNo).append("\n");
        sb.append("Ad Soyad   : ").append(getAd()).append(" ").append(getSoyad()).append("\n");
        sb.append("----------------------------------------\n");
        sb.append(String.format("%-20s %-10s %-10s %-5s\n", "Ders Adı", "Vize", "Final", "Harf"));

        if (notListesi.isEmpty()) {
            sb.append("    Henüz not girişi yapılmamış.\n");
        } else {
            for (NotKaydi not : notListesi) {
                sb.append(String.format("%-20s %-10d %-10d %-5s\n",
                        not.getDersAdi(),
                        not.getVizeNotu(),
                        not.getFinalNotu(),
                        not.harfNotuGetir()));
            }
        }

        double gano = getGano();
        sb.append(String.format("GENEL ORTALAMA (GANO): %.2f\n", gano));
        sb.append(String.format("TOPLAM AKTS          : %.2f\n", getToplamAkts()));
        sb.append("========================================\n");

        /* İŞ MANTIĞI: Sertifika Kontrolü */
        if (gano >= 3.00) {
            sb.append(" TEBRİKLER! SERTİFİKA HAKKEDİLDİ   \n");
        } else {
            sb.append(" SERTİFİKA HAKKEDİLMEDİ (Yetersiz GANO)  \n");
        }
        sb.append("========================================\n");

        return sb.toString();
    }

    @Override public String raporOlustur() { return transkriptOlustur(); }
    @Override public void raporaEkle(String icerik) { }
    @Override public boolean raporGonderildiMi() { return false; }

    /* Getter & Setter */
    public String getOgrenciNo() { return ogrenciNo; }
    public int getGirisYili() { return girisYili; }
    public List<NotKaydi> getNotListesi() { return notListesi; }

    public void setOgrenciNo(String ogrenciNo) {
        this.ogrenciNo = ogrenciNo;
    }

    public void setGirisYili(int yil) throws GecersizVeriException {
        if (yil < 2015 || yil > 2025) {
            throw new GecersizVeriException("Giriş yılı 2015-2025 aralığında olmalıdır.");
        }
        this.girisYili = yil;
    }

    @Override
    public void logKaydiOlustur(String mesaj) {
        LocalDateTime simdi = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String zamanDamgasi = simdi.format(format);
        System.out.println("[SİSTEM LOGU " + zamanDamgasi + "] " + getOgrenciNo() + ": " + mesaj);
    }
}