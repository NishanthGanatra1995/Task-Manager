# Task-Manager

This is an interesting program, having an internal task manager to manage multiple threads, while keeping a check on the process of each of its children.

This has many advantages (versus create a thread and starting it manually):
  - When a thread is interrupted or quits unexpectedly, the task manager will know of this and can report back to the user (and main thread).
  - When starting a new thread you can query the manager to get information about the number of currently working threads.
  - You can 'force quit' (interrupt) a thread from the task manager.
  - You know the progress of all of the threads running on the main thread.
  - If the progress manager class is started such that it does not hang the interface, then starting a new thread as a child of the manager will not hang the GUI.
  - Aids in readability, extensibility and user feedback.
  
This project also has a few notable features:
  - ProgressBars as part of javaFx are animated: for example, if progressBar.setProgress(0), then progressBar.setProgress(100) are called, then the movement of the progress is animated from left to right rather than snapping to 100% progress.
  - JavaFx styling of buttons.
  - File serialization of an object that contains an image, making use of the progress manager to prevent the program hanging.

