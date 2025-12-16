public class SeminerNotu extends NotKaydi {

    public SeminerNotu(String dersAdi, int vizeNotu, int finalNotu, double akts) throws GecersizVeriException {
        super(dersAdi, vizeNotu, finalNotu, akts);
    }

    @Override
    public double gpaPuanıHesapla(double yuzlukOrtalama) {
        // Farklı polimorfik davranış (Seminer notu 70 üzeri ise 4.0)
        if (yuzlukOrtalama >= 70) {
            return 4.0;
        }
        return 0.0;
    }

    public double gpaPuanıGetir() {
        return gpaPuanıHesapla(yuzlukOrtalamaHesapla());
    }
}