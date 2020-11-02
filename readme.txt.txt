Name : Shaik Imtiyaz Aaresh
UTA ID: 1001832769

Programming language used : Java

Implementation:

-Priority queue is used for sorting the fringe
-Arraylist is used to read the input from input file and each single from input file is read into Arraylist.
-Closed set is implemented to keep track of nodes that are already visited.If the node is present in closed set we will not pass the graph anymore, else parse the graph.


How to run:
compile: java find_route.java

with heuristic file :
java find_route input_filename origin_city destination_city heuristic_filename

without heuristic file :
java find_route input_filename origin_city destination_city
