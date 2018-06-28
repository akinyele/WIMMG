package akinyele.com.wimmg.fragments.trackerFragment.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rtugeek.android.colorseekbar.ColorSeekBar;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.fragments.trackerFragment.adapter.CategoriesAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateCategoryDialog extends FrameLayout implements TextWatcher {

    @BindView(R.id.edit_text_category_name)
    EditText mCategoryEditText;
    @BindView(R.id.view_category_background)
    View mCategoryColorView;
    @BindView(R.id.text_category_name_letter)
    TextView mCategoryNameText;
    @BindView(R.id.colorSlider)
    ColorSeekBar mColorSeekBar;

    CategoriesAdapter categoriesAdapter;

    public CreateCategoryDialog(@NonNull Context context) {
        super(context);
        inflate(getContext(), R.layout.dialog_add_category, this);
        ButterKnife.bind(this);
        init();
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
            mCategoryNameText.setText(String.valueOf(charSequence.charAt(0)));
        } catch (Exception e) {
            mCategoryNameText.setText("");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //==============================================================================================
    //              SETUP
    //==============================================================================================
    public void init() {


        mCategoryEditText.addTextChangedListener(this);
        mCategoryNameText.setText("A");

        mColorSeekBar.setOnColorChangeListener((colorBarPosition, alphaBarPosition, color) -> {

            //mCategoryColorView.setBackgroundTintList(getContext().getColorStateList(color));
            mCategoryNameText.setTextColor(color);

        });

    }


    //==============================================================================================
    //              Helpers
    //==============================================================================================
    public CategoryRealmModel getCategory() {
        return categoriesAdapter.getSelectedCategory();
    }


}
