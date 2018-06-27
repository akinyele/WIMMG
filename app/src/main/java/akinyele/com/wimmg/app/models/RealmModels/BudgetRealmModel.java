package akinyele.com.wimmg.app.models.RealmModels;

import java.util.UUID;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BudgetRealmModel extends RealmObject {

    @PrimaryKey
    private String categoryName;
    private CategoryRealmModel category;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CategoryRealmModel getCategory() {
        return category;
    }

    public void setCategory(CategoryRealmModel category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
