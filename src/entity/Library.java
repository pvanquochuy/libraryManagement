package entity;

import java.util.List;

public interface Library<T extends Item> {
    void addItem(T item);
    void removeItem(String id);
    void updateItem(String Id, T newItem);
    List<T> searchItem(String keyWord);
    void borrowItem(String id);
    void returnItem(String id);
    List<T> getAllItems();
    interface ItemCreator<T> {
        T createItemFromString(String line);
    }


}
