public interface Raporlanabilir {
    String transkriptOlustur();
    void raporaEkle(String icerik);
    boolean raporGonderildiMi();
    String raporOlustur();
}