package akinyele.com.wimmg.fragments.trackerFragment.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import akinyele.com.wimmg.R;
import akinyele.com.wimmg.app.models.RealmModels.CategoryRealmModel;
import akinyele.com.wimmg.ext.Constants;
import akinyele.com.wimmg.fragments.trackerFragment.adapter.CustomSpinnerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by akiny
 * on 5/24/2018.
 */
public class AddItemDialogView extends FrameLayout implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.edit_text_name)
    AutoCompleteTextView mNameEditText;

    @BindView(R.id.edit_text_cost)
    AutoCompleteTextView mCostEditText;

    @BindView(R.id.text_time_bought)
    TextView mTimeBoughtText;

    @BindView(R.id.text_date_bought)
    TextView mDateBoughtText;

    @BindView(R.id.spinner_category)
    Spinner mCategorySpinner;

    @BindView(R.id.stepper_amount)
    StepperTouch mAmountStepper;

//    @BindView(R.id.fab_add_category)
//    FloatingActionButton addCategoryFab;


    Calendar mCalendar = Calendar.getInstance();
    private CustomSpinnerAdapter mSpinnerAdapter;

    public AddItemDialogView(@NonNull Context context) {
        super(context);
        inflate(getContext(), R.layout.dialog_add_tracked_item, this);
        ButterKnife.bind(this);

        setUpViews();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateString = getDateString(year, month, dayOfMonth);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDateBoughtText.setText(dateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String timeOfDay = getTimeString(hourOfDay, minute);
        mTimeBoughtText.setText(timeOfDay);
    }

    //==============================================================================================
    //          Initializer
    //==============================================================================================
    private void setUpViews() {

        String dateBoughtText = getDateString(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        mDateBoughtText.setText(dateBoughtText);

        String timeBoughtText = getTimeString(mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE));
        mTimeBoughtText.setText(timeBoughtText);

        mSpinnerAdapter = new CustomSpinnerAdapter(getContext());
        mCategorySpinner.setAdapter(mSpinnerAdapter);

        mAmountStepper.stepper.setMin(1);

    }


    //==============================================================================================
    //          Listeners
    //==============================================================================================
    @OnClick
    public void showDateSelectionDialog() {

        DatePickerDialog dialog = new DatePickerDialog(getContext());
        dialog.setOnDateSetListener(this);
        dialog.show();

    }

//    @OnClick(R.id.fab_add_category)
//    public void addCategoryDialog() {
//
//        DialogCreateCategory createCategoryView = new DialogCreateCategory(getContext());
//        //MaterialDialog materialDialog =
//        new MaterialDialog.Builder(getContext())
//                .customView(createCategoryView, true)
//                .title("Select Category")
//                .positiveText("Select")
//                .negativeText("Cancel")
//                .cancelable(false)
//                .autoDismiss(false)
//                .onPositive(
//                        (dialog, which) -> {
//                            CategoryRealmModel categoryRealmModel = createCategoryView.getCategory();
//                            addCategoryFab.setBackgroundDrawable(getContext().getDrawable(categoryRealmModel.getImage()));
//                            addCategoryFab.setBackgroundColor(getContext().getColor(categoryRealmModel.getColor()));
//                            dialog.dismiss();
//                        }
//                )
//                .onNegative(
//                        (dialog, which) -> dialog.dismiss()
//                )
//                .show();
//    }

    @OnClick(R.id.text_time_bought)
    public void showTimeSelectionDialog() {
        new TimePickerDialog(getContext(), this, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
    }


    //==============================================================================================
    //          Helpers
    //==============================================================================================
    private String getDateString(int year, int month, int dayOfMonth) {
        String monthString = Constants.month[month];
        return monthString + " " + dayOfMonth + ", " + year;
    }

    private String getTimeString(int hourOfDay, int minute) {

        String AM_PM = "AM";

        if (hourOfDay == 0) {
            hourOfDay = 12;
        } else if (hourOfDay == 12) {
            AM_PM = "PM";
            hourOfDay = 12;
        } else if (hourOfDay > 12) {
            AM_PM = "PM";
            hourOfDay = hourOfDay - 12;
        }

        return hourOfDay + ":" + minute + " " + AM_PM;
    }


    //==============================================================================================
    //          Getters
    //==============================================================================================
    public String getTimeOfDay() {
        return mDateBoughtText.getText().toString();
    }

    /**
     *
     * @return dd/mm/yyyy
     */
    public String getDateBought() {
        return mCalendar.get(Calendar.DAY_OF_MONTH) + "/" + mCalendar.get(Calendar.MONTH) + "/" + mCalendar.get(Calendar.YEAR);
    }

    public String getCost() {
        return mCostEditText.getText().toString();
    }

    public String getName() {
        return mNameEditText.getText().toString();
    }

    public int getAmount() {
        return mAmountStepper.stepper.getValue();
    }

    public CategoryRealmModel getCategory() {
        return (CategoryRealmModel) mSpinnerAdapter.getItem(mCategorySpinner.getSelectedItemPosition());
    }
}
