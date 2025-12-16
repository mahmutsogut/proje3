public abstract class Kullanici {
    private String ad;
    private String soyad;
    private int id;
    private String ePosta;

    // --- ABSTRACT METOTLAR ---
    public abstract String getKullaniciTuru();
    public abstract void bilgiGoster();

    // --- CONCRETE METOT ---
    public String tamAdGetir() {
        return this.ad + " " + this.soyad;
    }

    // --- GETTER METOTLARI ---
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }
    public int getId() { return id; }
    public String getePosta() { return ePosta; }

    // --- SETTER METOTLARI (Kontrollü) ---
    public void setAd(String ad) throws GecersizVeriException {
        if (ad == null || ad.trim().isEmpty()) {
            throw new GecersizVeriException("Ad alanı boş bırakılamaz.");
        }
        this.ad = ad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public void setId(int id) throws GecersizVeriException {
        if (id <= 0) {
            throw new GecersizVeriException("ID sıfırdan büyük olmalıdır.");
        }
        this.id = id;
    }

    public void setePosta(String ePosta) {
        this.ePosta = ePosta;
    }
}