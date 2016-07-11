<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
<<<<<<< HEAD
$con=mysqli_connect("localhost","root","","register_usersfinal");
if (mysqli_connect_errno($con))
{
	
$con1=mysqli_connect("localhost","root","");
$sql="CREATE DATABASE register_usersfinal";
$q=(mysqli_query($con1,$sql));
if (!$q)
{
   echo "Database not created properly";
}

   
}
$response = array();
$checkingtable="SELECT * FROM register";
$ch=mysqli_query($con,$checkingtable);
if (empty($ch))
{
	$pp="CREATE TABLE register(Name VARCHAR(30),Password VARCHAR(10),Conpassword VARCHAR(10),Email VARCHAR(30),Cny VARCHAR(30),Phone CHAR(10))";
	$ll=mysqli_query($con,$pp);
	if (!($ll))
	{
		echo "table unable to create";
	}
}
=======
$con=mysqli_connect("localhost","root","","register_users");
if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$response = array();

>>>>>>> 48af31befd35dce394d068c8fe546fe03e70be38
// check for required fields
//if (isset($_POST['name']) && isset($_POST['password']) && isset($_POST['conpassword']) && isset($_POST['email'])) {
    
    $name = $_POST['name'];
    $pass = $_POST['password'];
    $conpass = $_POST['conpassword'];
    $email=$_POST['email'];
    $cny=$_POST['cny'];
    $phone=$_POST['phone'];
	/*$name="Snajli";
	$pass="1234";
$conpass="1234";
$email="sanjliaarwal@gmail.com";
$cny="ppy";
$phone="7206340282";*/

    // mysql inserting a new row
    $res = ("INSERT INTO register VALUES('$name', '$pass', '$conpass','$email','$cny','$phone')");
	$result=mysqli_query($con,$res);
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Registered Successfully";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
?>
