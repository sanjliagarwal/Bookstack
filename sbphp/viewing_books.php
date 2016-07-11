<?php
$response = array();
$con=mysqli_connect("localhost","root","","register_usersfinal");
if(mysqli_connect_errno($con))
{
	echo "Database can't be reached";
}

// connecting to db
$mail=$_POST['mail'];
//$mail="son@g.c";
// get all products from products table
$res = ("SELECT Nameofbook,Author,Edition,Category 
FROM addbook 
WHERE Email='$mail'") ;
$result=mysqli_query($con,$res);
if (!$result)
{
	echo "query problem"; 
}
// check for empty result
if ((mysqli_num_rows(mysqli_query($con,$res))) >= 1) {
    $response["book"]=array();
    while($row=mysqli_fetch_array($result)){
        $book["Name"] = $row["Nameofbook"];
        $book["Author"] = $row["Author"];
		$book["Edition"]=$row["Edition"];
		$book["Category"]=$row["Category"];
		array_push($response["book"],$book);
	}
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
