# Learning Management System 
Learning Management System is a project which is focused on bringing online solution to the educational institutes.
## Project Description
I have developed this project to help students stay up to date with their learning. Due to COVID-19, many students have lost track of their learning as most of the educational institutions were not equipped for online, as COVID-19 came out of the blue. The Application is built in such a way that the learning mode can switched between offline and online by just pressing a toggle button. I have also included Attendance feature which gets updated automatically in online mode by calculating the amount of time a student has spent in the class. Whereas in offline mode, the assigned teacher will have the access to update the attendace in a stipulated time. 
## Student Application
This application is designed for the students. The students get authenticated when their data is uploaded to database. The login details can be obtained from the Admin.
The student can check his daily timetable displayed right at the top of the homescreen in the application. When the teaching mode is Online, the time slot turns blue, 
and when clicked, the student joins the online class. A student can check all his attendance details, number of days present and absent for each subject respectively. A student can check his courses and the couse materials which will be available in Google Drive. The application takes the user to google drive using WebView. Students can view the messages sent to them. They can check available quiz and attend them. If already attended they can view their scores. They can also check the announcements, their academic performance and available examinations.
## Tech
1. This project is built on MVVM Architecture.
2. Firebase Authentication is used to ease the user authentication.
3. Firebase Firestore is used to store the data. Firestore rules defined in asuch a way that access is granted by the type of account.
4. Firebase Cloud Messaging is used to send notifications when a message is sent.
5. Jitsi meet to be able to deliver classes online.
## Features
1. Add Students and Teachers
2. View the Students and Teachers
3. View Classes and their details like Proctor, Timetable, Courses, Course materials, Announcements, Exams, Quiz and Result. The Admin has Read, Write, Delete and Update permissions to these features.
4. Students can check their attendance and academic performance.
5. Set Class Monitor in Students section. 
6. Send Message to Students and Teachers.
7. Online Class in Jitsi Meet (Yet to include)


## External Libraries used:
1. Firebase
2. Circle Image View
3. Butterknife
4. Eventbus
5. Retrofit
6. Jitsi
