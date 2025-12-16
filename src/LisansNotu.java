public class LisansNotu extends NotKaydi {

    public LisansNotu(String dersAdi, int vizeNotu, int finalNotu, double akts) throws GecersizVeriException {
        super(dersAdi, vizeNotu, finalNotu, akts);
    }

    @Override
    public double gpaPuanıHesapla(double yuzlukOrtalama) {
        if (yuzlukOrtalama >= 90) return 4.0;
        if (yuzlukOrtalama >= 80) return 3.5;
        if (yuzlukOrtalama >= 70) return 3.0;
        if (yuzlukOrtalama >= 60) return 2.5;
        return 0.0;
    }

    public double gpaPuanıGetir() {
        return gpaPuanıHesapla(yuzlukOrtalamaHesapla());
    }
}