#Technical Design of the accounting application

In this app, we use the MVC model that is very convenient for data processing and create a large number of activities for implementing 
various functions. In the switch of the page, I chose to use fragment with Google's official BottomNavigation, this usage is the simplest.
In the chart function, I have used a chart component to call the data from the record, generate a pie chart according to the color I set
in order to display the user's income and expenditure intuitively. We are facing a big problem in the choice of database because our team 
members have all returned to China, there is a big network barrier in the use of firebase database, we have considered using a cloud data 
sharing platform in China, but we don’t know if it can be used abroad, so we chose to use the SQLite database, which caused our data to be
temporarily unable to share data. Next time we will improve the database model to make this application easier to use. At the same time, 
the next time may consider adding the positioning function of the map, so that every cost of the user can have accurate map positioning.
