<?php
require ("config.php");

$sql = "SELECT * FROM `fouadationinfor` ORDER BY avgtone ASC";
$result = mysqli_query($conn,$sql);
$json=array();

if($result){

    while($row =  mysqli_fetch_assoc($result)){
        $json[] = $row;
    }

    $response["success"] = 1;
    $response["massage"] = "Load data success";
    $response["contents"] = $json ;
    echo json_encode($response);

}else{

    $response["success"] = 0;
    $response["massage"] = "Load failed";
    echo json_encode($response);
}


?>