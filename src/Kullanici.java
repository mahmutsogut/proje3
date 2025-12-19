public abstract class Kullanici {


    private String ad;
    private String soyad;
    private int id;

    public abstract String getKullaniciTuru();
    public abstract void bilgiGoster();

    public String tamAdGetir() {
        return this.ad + " " + this.soyad;
    }

    /* Getter ve Setter Metotları */
    public String getAd() {
        return ad;
    }
    public String getSoyad() {
        return soyad;
    }

    public void setAd(String ad) throws GecersizVeriException {
        if (ad == null || ad.trim().isEmpty()) {
            throw new GecersizVeriException("Ad alanı boş bırakılamaz.");
        }
        this.ad = ad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }
}