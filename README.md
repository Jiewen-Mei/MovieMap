# MovieMap
This Program is not complete, and i am stuck in some logical error badly, so i will explain the idea of this program and fuctions of each class; and I will point out what is the failure and what it succeeds.
Main idea abou this program:-- 1,retrieve genres substring, tittles and release year line by line;
                               2, use genres as keys, movieLists as values for the HashMap; one movieList is consist of movie objects that are 
                                  classified as such a genre; if this movie has more than one genre, will will be added to all genres it fits;
                                  hence, overall, the total # of movie objects will be larger than the actual data;  the movie objects should 
                                  should be added to the right list in descending order(which makes further outputs more earies to be retrieved
                               3, with the storing stratgeis, it will be easy to calculate the # of movies for each genres/ for each genre in               year and group=spliting based on average; it's convenient to get the data for each genres in the recent 5 years;
                               
what is succeful:  ---it can retrieves all data needed from input files( release year, tittles, genres' string array are succeful; it handles all the exceptions in the input files including formats with unexpected commas, paratheses, and the ones without release years, and no genres listed; for no release year, it is assigned to be 0, for no genres listed, these exceprions are corrected in a string[] exceptions;
                      
                   ----it can categorizes all genres withought duplicates;
                   ----the algorithmn of inserting movies Objects in descending order(lattest to oldest) for movieList is  successful;
what are the failures: ---counting are bad; output is much smaller than actual data; even much smaller than waht it is ecpected; so counting for whole failed; since counting for recent years are using the same/similar algotithmn, so it faield too. it also causes counting for each genre ain each year failed; as far as what I complete, it seems no compliration error but runtime error kills me.

------------------------------------------------------------------------------------------------------------------
the followung is the layout for the program:

Movie Class:  with data fields of tittle, release year and Movie Object next, which is used to create a single ended linkedList;
              it simply has methods to get data fields only;
              
MovieList Class:  with data field Movie first, which is used to connect the list;
                  with isEmpty() method to check if there is no start point for the list;
                  with insert() method to add more Movie Objects and sort them in descending order as well;
                  with total() method to count all Movies the list holds ( one whole list is all movies with the same genre)
                  with restrictCount methods which accepts an asisgned value representing "CURRENT YEAR", to work as filter to get datas that fall in the range;
                  with countByYear () method, with algorithm like this(what I expected)
                                                      first Movie objects year will be added to the int[][] in the first column, and a counter to count all Movies with the same year; once it hits the different year, counter will be stored and the filter will be set as the new release year(the one it hits), and counter is reset; this process is repeated until no more Movie Objects;
                                                      
MovieMap class:  with readIn methods that can deal with all data in the input file and it can succefully retrieves genres, release years and titles, expcetions are handled;
                 with put methods to create keys with genres, to create values for keys by adding Movies to Movielist that is held by this key(genre)
                 with average, recentAvg, sortByAvg, sortByRestrictAvg are methods that reply on counting methods from Movie list; these methods are to calculate the average adn split up data in two groups( above average and below average| one is about the whole data, another only use the recent 5 year data). Since counting methods failed, these mthods failed; 
                 trend method is to print out the # of movies for each genre in each year; it fails as counting methods fails
                
                                                    
