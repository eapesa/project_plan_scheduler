package ph.eapesa.apps.api;

import ph.eapesa.apps.helper.DateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Task {

    private String name;
    private Date startDate, endDate;
    private Date originalStartDate, originalEndDate;
    private long durationInMs;
    private ArrayList dependencies;

    public Task(String name, String startDate, String endDate) throws ParseException, IllegalArgumentException {
        this.name = name;

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.startDate = dateFormat.parse(startDate);
        this.endDate = dateFormat.parse(endDate);

        if (this.startDate.compareTo(this.endDate) == 1) {
            throw new IllegalArgumentException("End date can't be less than the start date.");
        }

        long now = System.currentTimeMillis();
        if (this.startDate.getTime() < now || this.endDate.getTime() < now) {
            throw new IllegalArgumentException("Start date or end date can't be any day prior today.");
        }

        this.originalStartDate = this.startDate;
        this.originalEndDate = this.endDate;
        this.durationInMs = this.endDate.getTime() - this.startDate.getTime();

        dependencies = new ArrayList();
    }

    // ************************************************************
    // Public attributes and methods
    // ************************************************************

    /**
     * Flags used in the `getDate(int flag)` method.
     */
    public static final int STARTFLAG = 0;
    public static final int ENDFLAG = 1;

    /**
     * Returns the assigned name of Task
     *
     * @return current name
     */
    public String getName() { return name; }

    /**
     * Returns the assigned date of the task. Parameter `flag` will indicate if what's being asked is the start date
     * or end date.
     *
     * @param flag indicates which date (`startDate`, `endDate`) to return
     *
     * @return `startDate` or `endDate` of the task
     */
    public Date getDate(int flag) {
        if (flag == STARTFLAG) {
            return startDate;
        } else {
            return endDate;
        }
    }

    /**
     * Updates the date of the task with `flag` indicating which date to update. Also updates `durationInMs`.
     *
     * @param flag indicates which date (`startDate`, `endDate`) to update
     * @param dateString is a date in the format of `DD-MM-YYYY` (e.g. 20-06-2018)
     */
    public void setDate(int flag, String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (flag == STARTFLAG) {
            startDate = dateFormat.parse(dateString);
        } else {
            endDate = dateFormat.parse(dateString);
        }
        durationInMs = endDate.getTime() - startDate.getTime();
    }

    /**
     * Returns the duration of the task from the given `startDate` and `endDate`.
     *
     * @return current duration of the task in ms.
     */
    public long getDuration() { return durationInMs; }

    /**
     * Adds task dependency which may update the `startDate` and `endDate` of the task
     *
     * @param newTask
     */
    public void addDependency(Task newTask) {
        dependencies.add(newTask);

        Collections.sort(dependencies, new TaskEndDateComparator());
        Task latestDependency = (Task) dependencies.get(dependencies.size() - 1);

        // Start is day after latest dependency ends
        int daysDuration = (int) (this.durationInMs / DateUtil.DAYS_IN_MSECS);
        startDate = DateUtil.addDays(latestDependency.getDate(ENDFLAG), 1);

        // End is start date plus duration given the original start and end date
        endDate = DateUtil.addDays(startDate, daysDuration);
    }

    /**
     * Removes all task dependencies.
     */
    public void removeDependencies() {
        dependencies.clear();
        startDate = originalStartDate;
        endDate = originalEndDate;
    }

    /**
     * The following are comparators used to sort list of tasks given their start or end dates
     */
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