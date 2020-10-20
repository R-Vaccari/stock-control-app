package application.entities;

import application.entities.enums.Category;
import application.entities.enums.Size;

import java.io.Serializable;
import java.util.Objects;

public class StockItem implements Serializable {

    private String id;
    private String name;
    private int quantity;
    private Category category;
    private Size size;

    public StockItem() {
    }

    public StockItem(String id, String name, Integer quantity, Category category, Size size) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.size = size;
    }

    public String getId() { return id; }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Size getSize() {
        return size;
    }
    public void setSize(Size size) {
        this.size = size;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockItem stockItem = (StockItem) o;
        return id.equals(stockItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
