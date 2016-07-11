<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();
<<<<<<< HEAD
$con=mysqli_connect("localhost","root","","register_usersfinal");
=======
$con=mysqli_connect("localhost","root","","register_users");
>>>>>>> 48af31befd35dce394d068c8fe546fe03e70be38
if(mysqli_connect_errno($con))
{
	echo "Database can't be reached";
}

// connecting to db
$mail=$_POST['mail'];

//$mail="san@gmail.com";
//$password="qqqq";
// get all products from products table
$res = ("SELECT Name,Email,Phone,Cny FROM register 
WHERE Email='$mail'") ;
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
		$response["Phone"] = $row["Phone"];
		$response["Cny"]=$row["Cny"];
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["Name"]="";
$response["Email"]="";
$response["Phone"] = "";
		$reponse["Cny"]="";
    // echo no users JSON
    echo json_encode($response);
}
?>
