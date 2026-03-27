#TITLE: Aralist (To Do list using Array List) 


** Final Java Programming Activity **
** Date: December 2025 **

---


## About the Project: 
** ARALIST
	- a console-based to-do list application built in Java using multiple functions but mainly focusing on ArrayList class.
Allows user to:
- Add tasks - we created a method for the adding of the task using arraylist to make the adding of the task more efficient and easier rather than arrays that requires a more complex way of doing so. In case 1, we also used different utilities such as LocalDateTime to determine if the date that the user will input is the currentdate or the date after the current date. 

- Delete tasks - same execution for case 1, its for the removal of the task. The user will input what task number he/she will delete from the tasklist. After inputting the required the task number. The task will be permanently removed from the same list from how we added it.

- Sort tasks according to date - Reorders the task list by due date and creation time, using Bubble Sort to compare and swap tasks, dates, and the time, then it will update ang print the original lists so the program will always uses the updated, sorted task list. 

- Find tasks - The program asks the user to enter a keyword, then scans the ArrayList to check if any task contains that keyword. If a match is found, it displays the task with its due date and creation time; otherwise, it reports that no task was found.


- Mark tasks as complete - The program shows all tasks along with their current status, then prompts the user to select a task number. Once chosen, the task’s status in the completed list is updated from pending to completed, and the updated task list is displayed.


--- 


## Built with: 
- Eclipse
- Vs Code

---


## Requirements:
- Java Development Kit (JDK) 8 or higher  
- Any Java IDE (e.g., Eclipse, VS code) or terminal  
- Basic understanding of Java syntax and structure  


---


## Steps to Run the Program 
1. Be sure to have Java Development Kit (JDK) installed (version 8 or higher). Open the terminal and Compile the program.
2. The program prompts the user to Enter their name
3. Interact with the Aralist menu options 
	(1) Add New Task
	(2) Delete Task
	(3) Sort Automatically By Date and Time
	(4) Search Task
	(5) Check/Complete Tasks
4. To add task, Input task name, then Task due date
5. To delete task, Input task number user wants to delete 
6. To sort task, simply input 3, the program automatically sort the list, the program then prints the updated version. 
7. To search task, simply input the task name, the program then searches it and prints it with its due date and creation time. 
8. To check and complete tasks, simply input the number of the task the user wants to complete and the program will delete it from the list. The program also takes note of overdue tasks not marked done, and automatically removes it from the list. 

For more examples, refer to the Documentation. 
	
--- 


## Contributors
** Dominique Jazz Doroteo - Integration Lead 
** Mach Julian Galope - Arrays Lead
** Gillian Princess Gamotia - Basics Lead

---


## Documentation
** Week 1:
	November 28, 2025: Conceptualization, Basic functions of the program 
	November 30, 2025: Development of Case 1 and Case 2
	December 03, 2025: Development of Case 3 & Re-evaluation on the functions of our programs 
	December 04, 2025: Development of Case 4
	December 05, 2025: Progress checking 
** Week 2: 
	December 07, 2025: Development of Case 5 & Revising parts of the Code
	December 08, 2025: Documentations Report	

--- 


## Code Development & Challenges

** Lack of Time
The main challenge the group faced was the lack of time to accomplish the code for the features they aimed to input during the conceptualization period. Thus they decided to remove features such as the calendar integration and pomodoro timer. 

** Redundancy of features 
Initial features such as sorting by priority and by subject though may seem useful, may seem redundant and not useful for the scope of this program.

** Errors such as Indexoutofbounds etc. 
During development, the group encountered typical programming issues such as IndexOutOfBoundsException and input validation errors. These challenges were resolved by adding error-handling mechanisms, validating user input, and refining the logic to ensure smoother program execution.

 

---
Contact: 

Dominique Doroteo - dominiquejazz.doroteo@ust.edu.ph
Mach Galope - mach.galope@ust.edu.ph
Gillian Gamotia - gillianprincess.gamotia@ust.edu.ph

