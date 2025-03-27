package praktikum.orders;

public class Order {
    private String[] colors;

    public Order(String[] colors) { // Конструктор принимает массив строк
        this.colors = colors;
    }

    public String[] getColors() {
        return colors;
    }
}