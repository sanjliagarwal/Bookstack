<?php
$con=mysqli_connect("localhost","root","","register_users");
$sql="CREATE TABLE addbook(Nameofbook VARCHAR(30) NOT NULL,Author VARCHAR(30) NOT NULL,Edition VARCHAR(10),Email VARCHAR(30) NOT NULL,Category VARCHAR(10) NOT NULL,FOREIGN KEY(Email) REFERENCES register(Email))";
if (mysqli_query($con,$sql))
{
   echo "Table have been created successfully";
}
?>