package com.example.webpos.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart implements Serializable {

    private List<Item> items = new ArrayList<>();
    private double total_cost = 0;

    public Item findItem(String productId){
        for(Item it: items){
            if(it.getProduct().getId().equals(productId)){
                return it;
            }
        }
        return null;
    }

    public boolean addItem(Item item) {
        Item temp = this.findItem(item.getProduct().getId());
        if(temp != null){
            total_cost += item.getQuantity() * item.getProduct().getPrice();
            temp.setQuantity(temp.getQuantity() + item.getQuantity());
            return true;
        }
        else{
            total_cost += item.getQuantity() * item.getProduct().getPrice();
            return items.add(item);
        }
    }

    public double getTotal() {
//        double total = 0;
//        for (int i = 0; i < items.size(); i++) {
//            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
//        }
//        return total;
        return total_cost;
    }

    //delete the item by productID
    public boolean deleteItem(String productId){
        Item temp = this.findItem(productId);
        if(temp != null){
            total_cost -= temp.getQuantity() * temp.getProduct().getPrice();
            return items.remove(temp);
        }
        else{
            return false;
        }
    }

    //modify the amount of the cart by productID
    public boolean modifyItem(String productId, int amount) {
        Item item = this.findItem(productId);
        if(item != null){
            total_cost += amount * item.getProduct().getPrice();
            item.setQuantity(item.getQuantity()+amount);
            if (item.getQuantity() <= 0){
                return items.remove(item);
            }
            return true;
        }
        else{
            return false;
        }
    }

    //clear the cart
    public void emptyCart(){
        if(items.size() > 0){
            total_cost = 0;
            items.clear();
        }
    }

}
