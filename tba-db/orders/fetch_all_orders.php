<?php
include '../db/db_connect.php';
//Query to select movie id and movie name
$query = "SELECT email FROM customers";
$result = array();
$userArray = array();
$response = array();
//Prepare the query
	if($stmt = $con->prepare($query)){
		$stmt->execute();
	//Bind the fetched data to $movieId and $movieName
	$stmt->bind_result($email);
	//Fetch 1 row at a time					
	while($stmt->fetch()){
		//Populate the movie array
		$userArray["email"] = $email;
		//$userArray["userID"] = $userID;
		$result[]=$userArray;
		
	}
	$stmt->close();
	$response["successful"] = 1;
	$response["data"] = $result;
	
 
}else{
	//Some error while fetching data
	$response["success"] = 0;
	$response["message"] = mysqli_error($con);
		
	
}
//Display JSON response
echo json_encode($response);
 
?>