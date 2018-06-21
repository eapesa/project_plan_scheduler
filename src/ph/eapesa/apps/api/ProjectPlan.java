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

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getPlanDuration() { return totalDurationInDays; }

    public Date getAssumedStartDate() { return assumedStartDate; }

    public Date getExpectedEndDate() { return expectedEndDate; }

    public void addNewTask(Task newTask) {
        taskList.add(newTask);
        updatePlan();
    }

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