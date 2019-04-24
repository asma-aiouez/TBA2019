<?php
include '../db/db_connect.php';
$response = array();
 
//Check for mandatory parameters
if(isset($_GET['email'])
	&&isset($_GET['password'])
	&&isset($_GET['dob'])
	&&isset($_GET['address'])
	&&isset($_GET['isLogged'])){
	$email = $_GET['email'];
	$password = $_GET['password'];
	$dob = $_GET['dob'];
	$address = $_GET['address'];
	$isLogged = $_GET['isLogged'];
	var_dump($email);
	//Query to insert a movie
	$query = "INSERT INTO customers( email, password, dob, address) VALUES (?,?,?,?)";
	//Prepare the query
	if($stmt = $con->prepare($query)){

		//Bind parameters
		$stmt->bind_param("ssss",$email, $password, $dob, $address);
		var_dump($email);
		//Exceting MySQL statement
		$res = $stmt->execute();
		var_dump($res);
		//Check if data got inserted
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "User Successfully Added";			
			
		}else{
			//Some error while inserting
			$response["success"] = 0;
			$response["message"] = "Error while adding user";
		}					
	}else{
		//Some error while inserting
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