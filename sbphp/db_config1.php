<?php
$con=mysqli_connect("localhost","root","");
<<<<<<< HEAD
$sql="CREATE DATABASE register_usersfinal";
=======
$sql="CREATE DATABASE register_users";
>>>>>>> 48af31befd35dce394d068c8fe546fe03e70be38
if (mysqli_query($con,$sql))
{
   echo "Database users created successfully";
}
else
{
echo "Error in creating database". mysqli_connect_error();
}
?>