# CaptureTheKing

Inspiration
Our inspiration for making this project was our love and interest for chess. We love playing chess but wanted to make a twist in the game. The actual game of chess focuses on cornering your opponent's king whereas our game focuses on eliminating the king completely.

What it does
This game takes user inputs (location of the piece and square you want the piece to go to) and moves if the move is valid. The game is over when one player runs out of time or takes the opponent’s king.

How we built it
In order to build this project, we used Java and Visual Studio Code to write our program. We used the class Point2D which was an easier way to work with the coordinates the pieces were on instead of their location in the array. We used the Scanner class to implement a user input. We used the Math and ArrayList class for organization and calculation in certain methods. The timer was created by using System.currentTimeMillis() before and after the user is asked for an input and subtracted from their total time.

Challenges we ran into
There were many obstacles faced while building this game. For example, checking the legality of every move was difficult because there were many factors that needed to be taken into consideration. Another issue we’ve encountered was creating “en passent”, which is a special type of pawn capture that only occurs under specific conditions, so we needed to check the last move using an arrayList. We’ve also encountered an issue regarding the castling mechanism in the game. There were also many factors to castle which made it more difficult to write into code.

Accomplishments that we're proud of
We are proud of making all the pieces move accurately, creating a working timer and determining a winner based on two conditions: running out of time and capturing the opponent’s king. We are also proud of coding niche moves such as castling (long or short) and “en passent”.

What we learned
We’ve learned so much throughout this entire experience. We’ve learned how to convert chess input notations to array coordinates using the Point2D class and conversion methods. We’ve also become more comfortable traversing and manipulating through a 2D array. We’ve also recently learned how to create a timer using System.currentTimeMillis(). In general, we’ve also learned how to make something beautiful, like the Valentine’s Day themed chess board, solely using the console.

What's next for Capture the King - Valentine's Day Edition
For the future of this game, we plan to incorporate standard algebraic chess notation which is the standard method of notating chess moves. (Ex. g1f3 would become Nf3, N stands for Knight and f3 is the location you move it to.)
