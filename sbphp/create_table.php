<?php
<<<<<<< HEAD
$con=mysqli_connect("localhost","root","","register_usersfinal");
=======
$con=mysqli_connect("localhost","root","","register_users");
>>>>>>> 48af31befd35dce394d068c8fe546fe03e70be38
$sql="CREATE TABLE register(Name VARCHAR(30),Password VARCHAR(10),Conpassword VARCHAR(10),Email VARCHAR(30),Cny VARCHAR(30),Phone CHAR(10))";
if (mysqli_query($con,$sql))
{
   echo "Table have been created successfully";
}
?>