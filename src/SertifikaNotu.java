public class SertifikaNotu extends NotKaydi {

    public SertifikaNotu(String dersAdi, int vizeNotu, int finalNotu, double akts) throws GecersizVeriException {
        super(dersAdi, vizeNotu, finalNotu, akts);
    }

    @Override
    public double gpaPuanıHesapla(double yuzlukOrtalama) {
        if (yuzlukOrtalama >= 80) {
            return 4.0;
        }
        return 0.0;
    }

    @Override
    public String harfNotuGetir() {
        double gpa = gpaPuanıHesapla(yuzlukOrtalamaHesapla());

        if (gpa >= 3.5) {
            return "SERTİFİKA HAKEDİLDİ !!";
        }
        else {
            return "SERTİFİKA HAKEDİLMEDİ !!";
        }
    }
}