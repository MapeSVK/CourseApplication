# CourseApplication
Android App

INTRODUCTION:
Android app providing option to rate courses at the university to give a feedback to the teacher. 
After the last button (send rating button) is clicked, a new object - rating is created based on the seek bar values, comment and an average. 
The system saves the new rating to a database and the user is able to send an email with an average rating as a message.

UI DESIGN:
The app has a user-friendly design with seek bars, bigger buttons, nice colors, landscape mode option, etc...

CODE DESIGN:
The app containes 2 activities which are connected with an intent. The app has one entity - Rating, which implements Parcelable for intent usage. 

DATABASE CONNECTION:
The app is connected to a Firebase database and each rating (row) has its own unique key. The app also retrieves data so therefore user is able to see an average made from all of the ratings from a database, and also total amount of rating that were made.

VALIDATION:
UX of this app is a big plus as user just simply drags seek bars and chooses the value. But this is an advantage from the user perspective. From the aspect of programmer, seek bars help us to validate user input and therefore not that much validation is needed. 
The only validation in this app is "the comment validation". The comment section must contain at least 100 characters, otherwise the toast is displayed with the message. 
