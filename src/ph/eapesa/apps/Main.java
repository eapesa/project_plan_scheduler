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

import ph.eapesa.apps.console.Console;

import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {
        Console test = new Console();
        test.display();
    }
}
