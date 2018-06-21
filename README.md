Project Plan Scheduler
----------------------

## INSTRUCTIONS

1. We need to calculate calendar schedules for project plans
2. Each project plan consists of tasks. Every task has a certain duration.
3. A task can depend on zero or more other tasks. If a task depends on some other tasks, it can only be started after these tasks are completed
4. So, for a set of tasks (with durations and dependencies), the solution for the challenge should generate a schedule, i.e. assign Start and End Dates for every task
5. It is ok to have a console app

## ENVIRONMENT

- Guaranteed to work on Java 9
- Guaranteed to work on Mac OSX but expected to work in Linux Environment thru script if without IDE
- For IDE, Intellij IDEA is used

## INSTALLATION

NOTE: All of the instructions below are guaranteed on Mac OSX environment

1. Clone this app, preferrably via Terminal:
```
$> git clone https://github.com/eapesa/project_plan_scheduler.git
```

2. Go to root directory of the app:
```
$> cd project_plan_scheduler
```

*** Run app via Terminal ***

- Open terminal (preferred iTerm2 for Mac OSX)

- Locate path of application
```
$> cd PATH/TO/APPLICATION/project_plan_scheduler
```

- Execute `run.sh`. That should compile the app and create binary in a dir named `out` and run the application located in that dir.
```
$> ./run.sh
```

NOTE: If above does not work, please execute the command below then repeat the above command:
```
$> chmod +x run.sh
```

*** Run app via Intellij IDEA ***

- Open Intellij and click `Open`

- Locate the application

- Build the project via navigating the following in menu bar:
```
Build > Build Project
```

- Click `Terminal` on the tabs below to show the terminal much like to iTerm2

- Run the application via navigating the following in menu bar:
```
Run > Run... > Click Main on the choices
```

## CONSOLE HOW TO's
