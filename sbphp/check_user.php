<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();
$con=mysqli_connect("localhost","root","","register_users");
if(mysqli_connect_errno($con))
{
	echo "Database can't be reached";
}

// connecting to db
$mail=$_POST['mail'];
$password=$_POST['password'];
//$mail="san@gmail.com";
//$password="qqqq";
// get all products from products table
$res = ("SELECT Name,Email,Password 
FROM register 
WHERE Email='$mail' AND Password='$password'") ;
$result=mysqli_query($con,$res);
if (!$result)
{
	echo "query problem"; 
}
// check for empty result
if ((mysqli_num_rows(mysqli_query($con,$res))) == 1) {
    
    
    $row=mysqli_fetch_assoc($result);
        $response["Name"] = $row["Name"];
        $response["Email"] = $row["Email"];
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["Name"]="";
$response["Email"]="";

    // echo no users JSON
    echo json_encode($response);
}
?>
