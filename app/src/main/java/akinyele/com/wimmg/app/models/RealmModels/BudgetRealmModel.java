package akinyele.com.wimmg.app.models.RealmModels;

public class BudgetRealmModel {

    private String amount;
    private CategoryRealmModel category;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public CategoryRealmModel getCategory() {
        return category;
    }

    public void setCategory(CategoryRealmModel category) {
        this.category = category;
    }
}
