package com.example.movietonight;
import android.graphics.Color;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import java.text.SimpleDateFormat;
import java.util.HashSet;

public class DateDecorator implements DayViewDecorator {
    private HashSet<String> dates;

    public DateDecorator(HashSet dates){
        this.dates=dates;
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        String decoDay=dateFormat.format(day.getDate());
        if(dates.contains(decoDay)){
            return true;

        }
        else{
            return false;
        }
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(10,Color.rgb(41,120,210)));
    }
}
