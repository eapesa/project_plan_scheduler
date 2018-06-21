package ph.eapesa.apps.console;

import ph.eapesa.apps.api.ProjectPlan;
import ph.eapesa.apps.api.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Scanner;

public class Console {
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_NUM = 1;
    private static final int TYPE_DATE = 2;
    private static final int TYPE_BOOL = 3;

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    private static boolean isDate(String dateString) {
        try {
            new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private static String setParameter(Scanner io, String label, int type) {
        System.out.println("");
        String param = null;
        while (true) {
            System.out.printf(label);
            param = io.nextLine();

            if (param.equals("\\n") || param.equals("")) {
                System.out.println(Error.INVALID_PARAMS + "\n");
            } else {
                if (type == TYPE_TEXT) break;
                else if (type == TYPE_NUM && isNumeric(param)) break;
                else if (type == TYPE_DATE && isDate(param)) break;
                else if (type == TYPE_BOOL && (param.toLowerCase().equals("y") || param.toLowerCase().equals("n"))) break;
                else System.out.println(Error.INVALID_PARAMS + "\n");
            }
        }
        return param;
    }

    public void display() {
        System.out.println("===================================");
        System.out.println("Welcome to Project Plan Scheduler!");
        System.out.println("===================================");

        Scanner io = new Scanner(System.in);
        while (true) {
            String planName = setParameter(io, Query.GET_PLAN_NAME, TYPE_TEXT);
            ProjectPlan plan = new ProjectPlan(planName);

            HashMap<String, Task> taskMap = new HashMap<>();
            int taskCount = Integer.parseInt(setParameter(io, Query.GET_TASK_COUNT, TYPE_NUM));
            while (taskCount > 0) {
                System.out.println("\n===Describe your tasks.===");
                String taskName = setParameter(io, Query.GET_TASK_NAME, TYPE_TEXT);
                String taskStartDate = setParameter(io, Query.GET_TASK_START_DATE, TYPE_DATE);
                String taskEndDate = setParameter(io, Query.GET_TASK_END_DATE, TYPE_DATE);

                Task task;
                try {
                    task = new Task(taskName, taskStartDate, taskEndDate);
                    taskMap.put(taskName, task);
                } catch (ParseException e) {
                    System.out.println(Error.ENCOUNTERED_EXCEPTION + e.toString());
                    continue;
                }

                System.out.printf("\n" + Query.GET_DEPS);
                String deps = io.nextLine();
                if (!deps.equals("") || !deps.equals("\\n")) {
                    String[] depsArray = deps.split(",");
                    for (String d : depsArray) {
                        if (d.equals(taskName)) {
                            System.out.println(Error.SELF_AS_DEPENDENCY);
                            continue;
                        }

                        Task taskDep = (Task) taskMap.get(d);
                        if (taskDep != null) {
                            task.addDependency(taskDep);
                        }
                    }
                }

                plan.addNewTask(task);
                taskCount--;
            }

            System.out.printf("\n=====PROJECT PLAN (" + planName + ") DETAILS:=====\n");
            System.out.println("Assumed STARTING DATE of project: " + plan.getAssumedStartDate());
            System.out.println("Expected ENDING DATE of project: " + plan.getExpectedEndDate());
            System.out.println("DURATION of project (in days): " + plan.getPlanDuration());

            String newPlanBool = setParameter(io, Query.NEW_PLAN, TYPE_BOOL);
            if (newPlanBool.equals("y")) {
                continue;
            } else {
                System.out.println("THANK YOU for using the Project Scheduler Application!");
                break;
            }
        }
    }
}
