/*
    INSTRUCTIONS:
    1. We need to calculate calendar schedules for project plans
    2. Each project plan consists of tasks. Every task has a certain duration.
    3. A task can depend on zero or more other tasks. If a task depends on some other tasks, it can only be started after these tasks are completed
    4. So, for a set of tasks (with durations and dependencies), the solution for the challenge should generate a schedule, i.e. assign Start and End Dates for every task
    5. It is ok to have a console app
    6. The solution should be pushed to GitHub

    ASSUMPTIONS / EDGE CASES:
    1. Only dates will be considered. Specific time are ignored. E.g. 20180620-1100 & 20180621-0800 are already one day apart.
    2. Weekends are included in the dates but can be improved later on. So duration will count days of weekend and start and end dates can fall on weekends
    3. Start and end date of a task may vary depending on its dependencies. New start date and end date will override the old one if a dependency is added to a task.
        - start date will be assumed to be the next day after the end date of its dependencies
        - end date will be total duration of this task added to its start date
    4. If project plan have task A, B, C where B and C is a dependency of A and A has the earliest starting date, the project plan won't consider that date and move the earliest date to consider on task that can start immediately.
 */

package ph.eapesa.apps;

import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {
        ProjectPlan project = new ProjectPlan("test_plan");

        Task task1 = new Task("task1", "21-06-2018", "23-06-2018");
        project.addNewTask(task1);

        Task task2 = new Task("task2", "24-06-2018", "26-06-2018");
        project.addNewTask(task2);

        Task task3 = new Task("task3", "27-06-2018", "02-07-2018");
        project.addNewTask(task3);

        Task task4 = new Task("task4", "28-06-2018", "29-06-2018");
        project.addNewTask(task4);

        task1.addDependency(task2);
        task1.addDependency(task3);

        System.out.println("TASK1 STARTDATE > " + task1.getDate(Task.STARTFLAG) + " | ENDDATE > " + task1.getDate(Task.ENDFLAG));
        System.out.println("TASK2 STARTDATE > " + task2.getDate(Task.STARTFLAG) + " | ENDDATE > " + task2.getDate(Task.ENDFLAG));
        System.out.println("TASK3 STARTDATE > " + task3.getDate(Task.STARTFLAG) + " | ENDDATE > " + task3.getDate(Task.ENDFLAG));

        System.out.println("===============");

        System.out.println("TOTAL DAYS > " + project.getPlanDuration());
        System.out.println("GET ASSUMED START DATE > " + project.getAssumedStartDate());
        System.out.println("GET EXPECTED END DATE > " + project.getExpectedEndDate());

        project.removeTask("task1");
        project.addNewTask(task1);

        System.out.println("===============");

        System.out.println(task1.getDate(Task.STARTFLAG) + " || " + task1.getDate(Task.ENDFLAG));
        System.out.println("TOTAL DAYS > " + project.getPlanDuration());
        System.out.println("GET ASSUMED START DATE > " + project.getAssumedStartDate());
        System.out.println("GET EXPECTED END DATE > " + project.getExpectedEndDate());
    }
}
