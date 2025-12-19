public abstract class NotKaydi {
    private String dersAdi;
    private int vizeNotu;
    private int finalNotu;
    private double akts;

    protected NotKaydi(String dersAdi, int vizeNotu, int finalNotu, double akts) throws GecersizVeriException {
        setDersAdi(dersAdi);
        setVizeNotu(vizeNotu);
        setFinalNotu(finalNotu);
        setAkts(akts);
    }

    public abstract double gpaPuanıHesapla(double yuzlukOrtalama);

    public double yuzlukOrtalamaHesapla() {
        return this.vizeNotu * 0.4 + this.finalNotu * 0.6;
    }

    public String harfNotuGetir() {
        double ortalama = yuzlukOrtalamaHesapla();

        if (ortalama >= 90) return "AA";
        if (ortalama >= 80) return "BA";
        if (ortalama >= 75) return "BB";
        if (ortalama >= 70) return "CB";
        if (ortalama >= 60) return "CC";
        if (ortalama >= 50) return "DC";
        if (ortalama >= 45) return "DD";
        if (ortalama >= 35) return "FD";

        return "FF";
    }

    public String getDersAdi() { return dersAdi; }
    public int getVizeNotu() { return vizeNotu; }
    public int getFinalNotu() { return finalNotu; }
    public double getAkts() { return akts; }

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