package service;

import entity.Book;
import entity.CD;
import entity.Item;
import entity.Library;
import exception.ItemAlreadyBorrowedException;
import exception.ItemIdAlreadyExistException;
import exception.ItemNotAvailableException;
import exception.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LibraryServiceManagement<T extends Item> implements Library<T>
{
    private List<T> itemList;

    public  LibraryServiceManagement(){
        itemList = new ArrayList<>();
    }

    @Override
    public void addItem(T item) {
        try{
            T tmp = findItemById(item.getId());
            if(tmp == null){
                itemList.add(item);
            }else{
                throw new ItemIdAlreadyExistException("Item with id + " + item.getId() + " already exist!");
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removeItem(String id) {
        try{
            T tmp = findItemById(id);
            if(tmp != null){
                itemList.remove(tmp);
                System.out.println("Deleted successfully!");
            }else{
                throw new ItemNotFoundException("Item with ID " + id + " not found!");
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void updateItem(String id, T newItem) {
        try{
            T tmp = findItemById(id);
            if(tmp != null){
                itemList.set(itemList.indexOf(tmp), newItem);
                System.out.println("Updated successfully!");

            }else{
                throw new ItemNotFoundException("Item with ID " + id + " not found!");
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<T> searchItem(String keyWord) {
        return itemList.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(keyWord.toLowerCase())
                        || item instanceof Book && ((Book) item).getAuthor().contains(keyWord)
                        || item instanceof CD && ((CD) item).getArtist().contains(keyWord))
                .collect(Collectors.toList());
    }

    @Override
    public void borrowItem(String id) {
        try{
            boolean found = false;
            T item = findItemById(id);
            if(item != null){
                found = true;
                if(item.isStatus()) throw new ItemAlreadyBorrowedException("Item with Id " + id + " is already borrowed!");
                else{
                    item.setStatus(true);
                    System.out.println("Browwed successfully!");
                }
            }
            if(!found) throw new ItemNotFoundException("Item with ID " + id + " not found!");

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void returnItem(String id) {
        try{
            boolean found = false;
            T item =findItemById(id);
            if(item != null){
                found = true;
                if(!item.isStatus()){
                    throw new ItemNotAvailableException("Item with id " + id + "is not borrowed");
                }else{
                    item.setStatus(false);
                }
            }
            if(!found) throw new ItemNotFoundException("Item with id " + id + " not found!");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<T> getAllItems() {
        return new ArrayList<>(itemList);
    }

    private T findItemById(String id){
        return itemList.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public CompletableFuture<Void> loadItemsFromFile(String fileName, ItemCreator<T> itemCreator ){

        return FileProcess.readFromFileAsync(fileName).thenAccept(lines -> {
            for(String line : lines) {
                T item = itemCreator.createItemFromString(line);
                if(item != null) {
                    addItem(item);
                }
            }
        });
    }
    public CompletableFuture<Void> saveItemsToFile(String filename) {
        List<String> lines = itemList.stream()
                .map(T::toString)
                .collect(Collectors.toList());
        return FileProcess.writeToFileAsync(filename, lines);
    }


}
