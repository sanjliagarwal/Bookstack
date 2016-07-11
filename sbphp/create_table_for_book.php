<?php
<<<<<<< HEAD
$con=mysqli_connect("localhost","root","","register_usersfinal");
$sql="CREATE TABLE addbook(Nameofbook VARCHAR(30) NOT NULL,Author VARCHAR(30) NOT NULL,Edition VARCHAR(10),Email VARCHAR(30) NOT NULL,Category VARCHAR(10) NOT NULL,id int AUTO_INCREMENT,FOREIGN KEY(Email) REFERENCES register(Email),PRIMARY KEY(id))";
=======
$con=mysqli_connect("localhost","root","","register_users");
$sql="CREATE TABLE addbook(Nameofbook VARCHAR(30) NOT NULL,Author VARCHAR(30) NOT NULL,Edition VARCHAR(10),Email VARCHAR(30) NOT NULL,Category VARCHAR(10) NOT NULL,FOREIGN KEY(Email) REFERENCES register(Email))";
>>>>>>> 48af31befd35dce394d068c8fe546fe03e70be38
if (mysqli_query($con,$sql))
{
   echo "Table have been created successfully";
}
<<<<<<< HEAD
else
{
	echo "error" . mysqli_connect_errno();
}
=======
>>>>>>> 48af31befd35dce394d068c8fe546fe03e70be38
?>