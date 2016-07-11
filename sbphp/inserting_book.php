<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
$con=mysqli_connect("localhost","root","","register_users");
if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$response = array();

// check for required fields
if (isset($_POST['nameofbook']) && isset($_POST['author']) && isset($_POST['email']) && isset($_POST['category'])) {
    
    $nameofbook = $_POST['nameofbook'];
    $author = $_POST['author'];
    $edition = $_POST['edition'];
	 $email=$_POST['email'];
    $category=$_POST['category'];
	/* $nameofbook = "Data Structures";
    $author = "Sahni";
    $edition = "2";
	 $email="son@g.c";
    $category="computers";*/

    // mysql inserting a new row
    $res = ("INSERT INTO addbook(Nameofbook,Author,Edition,Email,Category) VALUES('$nameofbook', '$author', '$edition','$email','$category')");
	$result=mysqli_query($con,$res);
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Book added to the database";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
}
else
{
$response["success"] = 0;
        $response["message"] = "Oops! Some importatnt fields were missing.";
        
        // echoing JSON response
        echo json_encode($response);	
}
?>
