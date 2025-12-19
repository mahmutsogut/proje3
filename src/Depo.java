import java.util.ArrayList;
import java.util.List;

public class Depo<T> {
    private List<T> esyalar = new ArrayList<>();

    public void ekle(T nesne) {
        esyalar.add(nesne);
    }

    public T getir(int index) {
        return esyalar.get(index);
    }
}