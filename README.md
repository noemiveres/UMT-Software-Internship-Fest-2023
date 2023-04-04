# UMT-Software-Internship-Fest-2023
The following problem was solved:
A password is considered strong if below conditions are all met: 
1. It has at least 6 characters and at most 20 characters. 
2. It must contain at least one lowercase letter, at least one uppercase letter, and at least one 
digit. 
3. It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." 
is strong, assuming other conditions are met). 
Write an algorithm that takes a string s as input, and return the MINIMUM change required to 
make s a strong password. If s is already strong, return 0. 
Insertion, deletion or replace of any one character are all considered as one change.

<p>In order to solve this problem, I first counted how many specific characters (lowercase, uppercase, digit)
must be added to the password, as this requires change anyways. Then, I split the problem into 3 cases, 
one when the password is too short, then just right and finally too long. </p>
<p>In the first case I had to consider the characters to be added and how many specific characters
are needed. I did not have to care about groups, as all the cases are solved by the first 2.</p>
<p>In the second case, we did not have to add or delete any characters, except adding the specific ones.
Groups could be treated by replacing their 3rd, 6th, ... values, by this way they would be destroyed.</p>
<p>The third case was more of a challenge, as here we had to make sure that we delete the characters
which would help us most. For this, mod%3 was used to differentiate among favourable cases.</p>

The solution can be found in src/main/java.

