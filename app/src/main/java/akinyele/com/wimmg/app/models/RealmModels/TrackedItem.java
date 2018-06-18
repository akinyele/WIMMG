package akinyele.com.wimmg.app.models.RealmModels;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by akiny on 5/23/2018.
 */
public class TrackedItem extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String name;
    private double cost;
    private int quantity;
    private CategoryRealmModel category;
    private String dateBought;
    private String timeOfDay;

    public TrackedItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CategoryRealmModel getCategory() {
        return category;
    }

    public void setCategory(CategoryRealmModel category) {
        this.category = category;
    }

    public String getDateBought() {
        return dateBought;
    }

    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }


    public static class Builder {

        private TrackedItem mTrackedItem;
        private String name;
        private double cost;
        private int quantity;
        private CategoryRealmModel category;
        private String dateBought; // dd/mm/yyyy
        private String timeOfDay;

        public Builder() {
            this.mTrackedItem = new TrackedItem();
        }

        public Builder name(String name) {
            mTrackedItem.setName(name);
            return this;
        }

        public Builder cost(double cost) {
            mTrackedItem.setCost(cost);
            return this;
        }

        public Builder quantity(int quantity) {
            mTrackedItem.setQuantity(quantity);
            return this;
        }

        public Builder category(CategoryRealmModel category) {
            mTrackedItem.setCategory(category);
            return this;
        }

        public Builder dateBought(String dateBought) {
            mTrackedItem.setDateBought(dateBought);
            return this;
        }

        public Builder timeOfDay(String timeOfDay) {
            mTrackedItem.setTimeOfDay(timeOfDay);
            return this;
        }

        public TrackedItem build() {
            return mTrackedItem;
        }

    }


}
