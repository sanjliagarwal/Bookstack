<?php
$con=mysqli_connect("localhost","root","");
$sql="CREATE DATABASE register_usersfinal";
if (mysqli_query($con,$sql))
{
   echo "Database users created successfully";
}
else
{
echo "Error in creating database". mysqli_connect_error();
}
?>