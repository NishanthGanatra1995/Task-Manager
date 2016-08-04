# Task-Manager

This is an interesting program that has a task manager that attempts to manage multiple threads, while keeping a check on the process of each one of its child processes.

This has the following advantages:
  - When a thread is interrupted, the task manager will know of this and can report back to the user.
  - When starting a new thread you can know how busy you program is by asking the manager how many threads are currently working.
  - You can 'force quit' (inerrupt) a thread from the task manager.
  - You know the progress of all of the threads running on the main thread.
  - If the progress manager class is started correctly then starting a new thread does not hang the GUI.
  - Aids in readability, extensibility and user feedback.
  
This project also has a few notable features:
  - ProgressBars as part of javaFx are animated: for example, if progressBar.setProgress(0), then progressBar.setProgress(100) are called, then the movement of the progress is animated from left to right.
  - JavaFx styling
  - File serialization of an object that contains an image, making use of the progress manager to prevent the program hanging.

