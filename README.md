# KodoKode
Android prototype app that teaches children the basics of coding in Python. For Orbital CP2106 (Summer 2019).

#### Proposed level of achievement
Gemini

## Motivation
Since the inception of computers, there has been a growing dependency on computers, both on an individual and societal level. This trend is set to continue as technology is invested into the various fields of occupation and studies to bring about automation and economic growth.

Since coding is at the heart of all IT infrastructure, there is a need to educate children on the basic coding concepts in an attempt to kickstart their learning in coding and prepare for the workplace in later life.  

## Aim
With the high smartphone ownership among young children, our group hopes to create an application that young children can use to learn the fundamentals of coding and pique their interest in this area. We also hope that it will be easy to use, appealing to them and entertaining and fun such that it is something they will come back to regularly.

## User Stories

- As a young child (user), I want to be able to navigate the application easily.
- As a young child (user), I want to learn the basics of coding in a manageable (bit by bit) and fun way.
- As a young child (user), I want to be able to compete with my friends and other people who are using the application and see our rankings.
- As an administrator, I want users to be tested on their knowledge without feeling that it is tedious or boring.
- As an administrator, I want to build up the users’ confidence in basic coding through this application.

## Tech Stack
- Toolkit: Android Studio
- SDK: Android Pie
- Programming language: Java
- Database: SQLite

## Project Scope 
The Android application will allow our target users, who are young children aged 9 to 12, to learn the basics of coding in Python and compete with friends or other users.
 
It will contain features such as: 
### 1. Learning section
- includes short chapters for users to learn the basics of coding and Python
- gain points from completing chapters

### 2. Assessment section
- short quizzes of 5 questions each
- accumulate score for each quiz that they do
- gain points from attempting/completing quizzes proportionate to their scores

### 3. Individual user profile
- to keep track of one's learning progress:
  - the chapters they have completed
  - number of quizzes they have completed
  - points obtained (from completing chapters and attempting quizzes)
  - ranking in leaderboard system

### 4. Ranking/leaderboard system
- ranked based on total amount of points user has accumulated
- ranked amongst amongst all users in database

## User Testing: Cognitive Walkthrough

### Task: Creating an account 
#### Task Analysis:
1. Click 'create new account' upon opening up the application
2. Input chosen username, email and password (twice)
3. Click on create account button

| Walkthrough | |
| ----------- | --- |
| 1. Will the user try and achieve the right outcome? | Yes, if user does not have an account, he will sign up for one. Otherwise he will log into his existing account. |
| 2. Will the user notice that the correct action is available to them? | Yes, even if user has clicked on sign in when he does not have an account, he is able to navigate to the 'create account' page via the 'no account yet? create one' link or press on the up arrow to go back to the starting page and vice versa. |
| 3. Will the user associate the correct action with the outcome they expect to achieve? | Yes. The 'create account' button is clear to obtain the outcome they expect to achieve. |
| 4. If the correct action is performed; will the user see that progress is being made towards their intended outcome? | Yes, a progress bar is made visible once the user clicks on 'create account' button and toast message appears when user has successfully signed up for an account |

### Task: Completing a chapter
#### Task Analysis:
1. On the Home activity page, click on 'Learn'
2. Choose a chapter from the list
3. Go through the entire chapter to the end
4. Click on 'Click to complete chapter'

| Walkthrough | |
| ----------- | --- |
| 1. Will the user try and achieve the right outcome? | Probably so as there are only 2 options on the home activity page, and it makes sense to learn before practising the quizzes. |
| 2. Will the user notice that the correct action is available to them? | Yes, the 'Learn' button is fairly visible and is placed above the 'practice' button. At the end of the chapter, the 'Click to complete chapter' button is also fairly obvious. |
| 3. Will the user associate the correct action with the outcome they expect to achieve? | Yes, the 'Learn' button is visible and what the user wants to achieve is clearly indicated on the button itself |
| 4. If the correct action is performed; will the user see that progress is being made towards their intended outcome? | Yes, when users click on 'Learn', the chapters available are numbered and it makes clear the sequence of the chapters they can learn. Page count at the bottom of each page of the chapter indicates the user's current progress in a chapter and the arrow buttons make it easy to view the next or previous pages. At the start and at the end of the chapter, the previous and next buttons respectively are greyed out to show that the user is unable to view the previous or next page of the chapter.  |

### Task: Attempt/completing a quiz
#### Task Analysis:
1. On the home activity page, click 'Practice'
2. Choose a quiz to attempt from the quiz list
3. Click on 'Start Quiz'
4. Go through the quiz until user reaches the end of the quiz and the button changes to 'Finish Quiz'
5. Click on 'Finish Quiz' and 'Okay' when the dialog popups

| Walkthrough | |
| ----------- | --- |
| 1. Will the user try and achieve the right outcome? | Probably so as there are only 2 options on the home activity page, and it makes sense to click on 'Practice' after going through a chapter as 'Practice' button is placed beneath the 'Learn' button. |
| 2. Will the user notice that the correct action is available to them? | After clicking on 'Practice', the quizzes are labelled with names and if user finds that he is unfamiliar with for instance, 'Variables and Types', he could click on the up button to go back to learn the chapter before attempting the quiz. |
| 3. Will the user associate the correct action with the outcome they expect to achieve? | Yes, 'Practice' button is fairly visible on the home activity page. So is the 'Start Quiz' button after user selects the quiz. |
| 4. If the correct action is performed; will the user see that progress is being made towards their intended outcome? | Yes, when user clicks on 'Check Answer', the correct/wrong answer is highlighted and a 'Good job/Try again' message indicates whether or not they got the question wrong. Button changes to 'Next' to move on to the question. At the last question, the button also changes to 'Finish Quiz' to tell the user that the quiz has ended. An alert dialog then pop ups to tell the user their score and the points they have obtained. |
