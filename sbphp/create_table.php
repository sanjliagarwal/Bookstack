<?php
$con=mysqli_connect("localhost","root","","register_usersfinal");
$sql="CREATE TABLE register(Name VARCHAR(30),Password VARCHAR(10),Conpassword VARCHAR(10),Email VARCHAR(30),Cny VARCHAR(30),Phone CHAR(10))";
if (mysqli_query($con,$sql))
{
   echo "Table have been created successfully";
}
?>