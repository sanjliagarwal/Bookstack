<?php
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
$name=$_POST['name'];
//$name="Data Structure";
$nameofbook=strtolower($name);
//$mail="son@g.c";
// get all products from products table
$res = ("SELECT addbook.Nameofbook,addbook.Author,addbook.Edition,addbook.Category,register.Name,register.Email,register.Cny,register.Phone,addbook.id
FROM addbook,register 
WHERE addbook.Email=register.Email AND LCASE(Nameofbook) LIKE '%{$nameofbook}%'") ;
$result=mysqli_query($con,$res);
if (!$result)
{
	echo "query problem"; 
}
// check for empty result
if ((mysqli_num_rows(mysqli_query($con,$res))) >= 1) {
    $response["book"]=array();
    while($row=mysqli_fetch_array($result)){
        $book["Nameofbook"] = $row["Nameofbook"];
        $book["Author"] = $row["Author"];
		$book["Edition"]=$row["Edition"];
		$book["Category"]=$row["Category"];
		$book["Name"]=$row["Name"];
		$book["Email"]=$row["Email"];
		$book["Cny"]=$row["Cny"];
		$book["Phone"]=$row["Phone"];
		$book["Id"]=$row["id"];
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
