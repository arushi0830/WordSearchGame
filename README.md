# WordSearchGame

This is a Word seach game where you have to search words in a m*m matrix. It is java and springboot based project. Inputs: grid size and list of words by the user

Endpoints:  http://localhost:8080/wordsearchgame/health; 
http://localhost:8080/wordsearchgame/getgrid , with sampe request body: {
    "gridSize":"3",
    "words": "one,two,three"
}

and request header: 
content-type:application/json 

will give u matrix: 
 U  o  t 
 E  n  w 
 D  e  o 
 
 
 Have fun time solving the word search !
