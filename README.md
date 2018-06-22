Project Plan Scheduler
----------------------

* [Objectives](https://github.com/eapesa/project_plan_scheduler#instructions)
* [Limitations and Assumptions](https://github.com/eapesa/project_plan_scheduler#limitations-and-assumptions)
* [Environment](https://github.com/eapesa/project_plan_scheduler#environment)
* [Installation](https://github.com/eapesa/project_plan_scheduler#installation)
* [API](https://github.com/eapesa/project_plan_scheduler#api)
* [About the Console App](https://github.com/eapesa/project_plan_scheduler#about-the-console-app)

## Objectives

1. We need to calculate calendar schedules for project plans
2. Each project plan consists of tasks. Every task has a certain duration.
3. A task can depend on zero or more other tasks. If a task depends on some other tasks, it can only be started after these tasks are completed
4. So, for a set of tasks (with durations and dependencies), the solution for the challenge should generate a schedule, i.e. assign Start and End Dates for every task
5. It is ok to have a console app


## Limitations and Assumptions

1. Time is not considered. When creating task, *only the specific date is asked*. Also, when a task is set with starting and end dates, the *whole day for the dates is covered*.
    - Example, a task with start date of 21-06-2018 and end date of *22-06-2018* will consume two days to finish.

2. Calculation of duration *includes weekends in the count*. This could be improved later on.
    - A task with start date of 22-06-2018 and end date of 25-06-2018 will have 4 days of duration instead of 2.

3. A task start and end date will vary depending on its dependencies. If its dependencies are set to be done later then the *start and end dates of the task will be moved the day after its latest dependency ends*.
    - TaskA has a start date of 01-07-2018 and end date of 02-07-2018 and it has a dependency, TaskB, with start date of 05-07-2018 and end date of 06-07-2018. TaskA's start date and end date will change to 07-07-2018 and 08-07-2018 respectively.

4. If the earliest task set to start and finish has dependencies that will finish later than itself then the dates for the said task will change, specifically start date, and duration may vary.
    - Using example in #3, at first glance, duration should be 6 days but it will be reduced to 4 days. This is due to TaskA's starting date that will be changed to 07-070-2018.

5. Idle days are currently counted. To further improve in the future.
    - Using #3 as an example, if TaskB is not a dependency of TaskA, duration will be 6 days even if the active days is only a total of 4 days.

## Environment

- Guaranteed to work on Java 9
- Guaranteed to work on Mac OSX but expected to work in Linux Environment thru script if without IDE
- For IDE, Intellij IDEA is used

## Installation

NOTE: All of the instructions below are guaranteed on Mac OSX environment

1. Clone this app, preferrably via a terminal (I use iTerm2 for Mac OSX):
```
$> git clone https://github.com/eapesa/project_plan_scheduler.git
```

2. Go to root directory of the app:
```
$> cd project_plan_scheduler
```

** Run app via Terminal **

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

** Run app via Intellij IDEA **

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

## API

As of now, there are no separate documentation for the APIs but the codebase have comments describing attributes and methods.

## About the Console App

- The console app can currently cater to one project plan at a time and updates or edits on the task and/or project plan is not yet supported.

- You can create as many project plan as you want until you hit `n` in the following:
```
Create NEW PLAN? y/n n
THANK YOU for using the Project Scheduler Application!
```

- Total number of tasks is asked prior to defining them:
```
NUMBER of tasks? 4
```

- There are some validation checks on some inputs but it should notify the user and ask for a new input afterwards.

- Any comments and/or suggestions on how to improve the app are more than welcome!