package ph.eapesa.apps.api;

import ph.eapesa.apps.helper.DateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Task {

    public static final int STARTFLAG = 0;
    public static final int ENDFLAG = 1;

    private String name;
    private Date startDate, endDate;
    private Date originalStartDate, originalEndDate;
    private long durationInMs;
    private ArrayList dependencies;

    public Task(String name, String startDate, String endDate) throws ParseException {
        this.name = name;

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.startDate = dateFormat.parse(startDate);
        this.originalStartDate = this.startDate;
        this.endDate = dateFormat.parse(endDate);
        this.originalEndDate = this.endDate;
        this.durationInMs = this.endDate.getTime() - this.startDate.getTime();

        dependencies = new ArrayList();
    }

    public String getName() { return name; }

    public Date getDate(int flag) {
        if (flag == STARTFLAG) {
            return startDate;
        } else {
            return endDate;
        }
    }

    public void setDate(int flag, String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (flag == STARTFLAG) {
            startDate = dateFormat.parse(dateString);
        } else {
            endDate = dateFormat.parse(dateString);
        }
        durationInMs = endDate.getTime() - startDate.getTime();
    }

    public long getDuration(int flag) { return durationInMs; }

    public void addDependency(Task newTask) {
        dependencies.add(newTask);

        Collections.sort(dependencies, new TaskEndDateComparator());
        Task latestDependency = (Task) dependencies.get(dependencies.size() - 1);

        // Start is day after latest dependency ends
        int daysDuration = (int) (this.durationInMs / DateUtil.DAYS_IN_MSECS);
        startDate = DateUtil.addDays(latestDependency.getDate(ENDFLAG), 1);

        // End date is start date plus duration given the original start and end date
        endDate = DateUtil.addDays(startDate, daysDuration);
    }

    public void removeDependencies() {
        dependencies.clear();
        startDate = originalStartDate;
        endDate = originalEndDate;
    }

    public static class TaskStartDateComparator implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getDate(Task.STARTFLAG).compareTo(o2.getDate(Task.STARTFLAG));
        }
    }

    public static class TaskEndDateComparator implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getDate(Task.ENDFLAG).compareTo(o2.getDate(Task.ENDFLAG));
        }
    }

}