package ctrl_it.com.jm.wimmg.fragments.trackerFragment.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ctrl_it.com.jm.wimmg.R;
import ctrl_it.com.jm.wimmg.ext.Const;

/**
 * Created by akiny
 * on 5/24/2018.
 */
public class AddItemDialogView extends FrameLayout implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.edit_text_name)
    EditText mNameEditText;

    @BindView(R.id.edit_text_cost)
    EditText mCostEditText;

    @BindView(R.id.text_time_bought)
    TextView mTimeBoughtText;

    @BindView(R.id.text_date_bought)
    TextView mDateBoughtText;


    Calendar mCalendar = Calendar.getInstance();

    public AddItemDialogView(@NonNull Context context) {
        super(context);
        inflate(getContext(), R.layout.dialog_add_tracked_item, this);
        ButterKnife.bind(this);

        setUpViews();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateString = getDateString(year, month, dayOfMonth);
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


    @OnClick(R.id.text_time_bought)
    public void showTimeSelectionDialog() {
        new TimePickerDialog(getContext(), this, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
    }


    //==============================================================================================
    //          Helpers
    //==============================================================================================
    private String getDateString(int year, int month, int dayOfMonth) {
        String monthString = Const.month[month];
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
    //      Getters
    //==============================================================================================
    public String getTimeOfDay() {
        return mDateBoughtText.getText().toString();
    }

    public String getDateBought() {
        return mDateBoughtText.getText().toString();
    }

    public String getCost() {
        return mCostEditText.getText().toString();
    }

    public String getName() {
        return mNameEditText.getText().toString();
    }

    public String getCategory() {
        return "";
    }
}
