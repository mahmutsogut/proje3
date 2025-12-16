public abstract class NotKaydi {
    private String dersAdi;
    private int vizeNotu;
    private int finalNotu;
    private double akts;

    // --- CONSTRUCTOR (Alt Sınıf Bağlantısı İçin Kritik) ---
    protected NotKaydi(String dersAdi, int vizeNotu, int finalNotu, double akts) throws GecersizVeriException {
        setDersAdi(dersAdi);
        setVizeNotu(vizeNotu);
        setFinalNotu(finalNotu);
        setAkts(akts);
    }

    // --- ABSTRACT METOT ---
    public abstract double gpaPuanıHesapla(double yuzlukOrtalama);

    // --- CONCRETE METOTLAR ---
    public double yuzlukOrtalamaHesapla() {
        return this.vizeNotu * 0.4 + this.finalNotu * 0.6;
    }

    public String harfNotuGetir() {
        double gpa = gpaPuanıHesapla(yuzlukOrtalamaHesapla());

        if (gpa == 4.0) return "AA";
        if (gpa >= 3.5) return "BA";
        if (gpa >= 3.0) return "BB";
        if (gpa >= 2.5) return "CB";
        if (gpa >= 2.0) return "CC";

        if (gpa == 0.0) return "FF";
        return "DC";
    }

    public String durumGetir() {
        return harfNotuGetir().equals("FF") ? "KALDI" : "GEÇTİ";
    }

    // --- GETTER METOTLARI ---
    public String getDersAdi() { return dersAdi; }
    public int getVizeNotu() { return vizeNotu; }
    public int getFinalNotu() { return finalNotu; }
    public double getAkts() { return akts; }

    // --- SETTER METOTLARI (Kontrollü) ---
    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public void setVizeNotu(int not) throws GecersizVeriException {
        if (not < 0 || not > 100) {
            throw new GecersizVeriException("Vize notu 0-100 aralığı dışında olamaz.");
        }
        this.vizeNotu = not;
    }

    public void setFinalNotu(int not) throws GecersizVeriException {
        if (not < 0 || not > 100) {
            throw new GecersizVeriException("Final notu 0-100 aralığı dışında olamaz.");
        }
        this.finalNotu = not;
    }

    public void setAkts(double akts) throws GecersizVeriException {
        if (akts < 1 || akts > 15) {
            throw new GecersizVeriException("AKTS değeri 1-15 aralığında olmalıdır.");
        }
        this.akts = akts;
    }
}