<?php
include '../db/db_connect.php';
$response = array();
 
//Check for mandatory parameters
if(isset($_GET['customer_ID'])
	&&isset($_GET['email'])
	&&isset($_GET['password'])
	&&isset($_GET['dob'])
	&&isset($_GET['address'])){
	$email = $_GET['email'];
	$password = $_GET['password'];
	$dob = $_GET['dob'];
	$address = $_GET['address'];
	$customer_ID = $_GET['customer_ID'];

	
	//Query to update a movie
	$query = "UPDATE customers SET email=?,password=?,dob=?,address=? WHERE customer_ID=?";
	//Prepare the query
	if($stmt = $con->prepare($query)){
		//Bind parameters
		$stmt->bind_param("ssisi",$email,$password,$dob,$address, $customer_ID);
		//Exceting MySQL statement
		$stmt->execute();
		//Check if data got updated
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "User successfully updated";
			
		}else{
			//When movie is not found
			$response["success"] = 0;
			$response["message"] = "User not found";
		}					
	}else{
		//Some error while updating
		$response["success"] = 0;
		$response["message"] = mysqli_error($con);
	}
 
}else{
	//Mandatory parameters are missing
	$response["success"] = 0;
	$response["message"] = "missing mandatory parameters";
}
//Displaying JSON response
echo json_encode($response);
?>