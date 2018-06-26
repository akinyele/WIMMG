package akinyele.com.wimmg.app.models.RealmModels;

import io.realm.RealmModel;
import io.realm.RealmObject;

public class BudgetRealmModel extends RealmObject {

    private double amount;
    private CategoryRealmModel category;

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
}
