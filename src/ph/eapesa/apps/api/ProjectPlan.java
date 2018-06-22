package ph.eapesa.apps.api;

import ph.eapesa.apps.helper.DateUtil;

import java.util.*;

public class ProjectPlan {

    private String name;
    private Date assumedStartDate, expectedEndDate;
    private int totalDurationInDays;
    private ArrayList<Task> taskList;

    public ProjectPlan(String name) {
        this.name = name;
        assumedStartDate = new Date();
        expectedEndDate = new Date();
        totalDurationInDays = 0;
        taskList = new ArrayList<>();
    }

    // ************************************************************
    // Public attributes and methods
    // ************************************************************

    /**
     * Returns the assigned name of the project plan
     *
     * @return current name
     */
    public String getName() { return name; }

    /**
     * Updates the assigned name of the project plan
     *
     * @param name will be the new name of the plan
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the total duration of the plan IN DAYS
     *
     * @return plan duration in days
     */
    public int getPlanDuration() { return totalDurationInDays; }

    /**
     * Returns the assumed starting date of the plan
     *
     * @return a `Date` that represents the currently assumed starting date of the plan
     */
    public Date getAssumedStartDate() { return assumedStartDate; }

    /**
     * Returns the expected ending date of the plan
     *
     * @return a `Date` that represents the currently expected ending date of the plan
     */
    public Date getExpectedEndDate() { return expectedEndDate; }

    /**
     * Adds new task in the plan
     *
     * NOTE: possible duplicates are not yet handled
     */
    public void addNewTask(Task newTask) {
        taskList.add(newTask);
        updatePlan();
    }

    /**
     * Removes a task in the plan given the name of the task
     *
     * @param name of the task to be removed in the list
     */
    public void removeTask(String name) {
        Iterator iter = taskList.iterator();
        while (iter.hasNext()) {
            Task task = (Task) iter.next();
            if (task.getName() == name) {
                iter.remove();
            }
        }
        updatePlan();
    }

    // ************************************************************
    // Private attributes and methods
    // ************************************************************

    private void updatePlan() {
        boolean init = true;
        Date earliestStart = new Date();
        Date latestEnd = new Date();
        Iterator iter = taskList.iterator();
        while (iter.hasNext()) {
            Task task = (Task) iter.next();
            if (init || task.getDate(Task.STARTFLAG).compareTo(earliestStart) == -1) {
                earliestStart = task.getDate(Task.STARTFLAG);
                init = false;
            }
            if (init || task.getDate(Task.ENDFLAG).compareTo(latestEnd) == 1) {
                latestEnd = task.getDate(Task.ENDFLAG);
                init = false;
            }
        }
        long expectedEndDateTs = latestEnd.getTime();
        long assumedStartDateTs = earliestStart.getTime();
        // Math does not include the "first day" does adding 1 is necessary
        totalDurationInDays = (int) ((expectedEndDateTs - assumedStartDateTs) / DateUtil.DAYS_IN_MSECS) + 1;
        expectedEndDate = latestEnd;
        assumedStartDate = earliestStart;
    }

}