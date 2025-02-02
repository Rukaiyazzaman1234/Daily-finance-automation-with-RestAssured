import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class DailyFinanceController {
    Properties prop;
    public DailyFinanceController(Properties prop){
        this.prop=prop;
        RestAssured.baseURI="https://dailyfinanceapi.roadtocareer.net";
    }

    public Response userRegistration(UserModel userModel)  {
        Response response=given().contentType("application/json")
                .accept("application/json")
                .body(userModel).when().post("/api/auth/register");

        return response;
    }

    public Response adminLogin(UserModel userModel){
        Response response=given().contentType("application/json")
                .body(userModel).when().post("/api/auth/login");

        return response;
    }

    public Response userList(){
        Response response=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("Admin_Token"))
                .when().get("/api/user/users");
        return response;
    }

    public Response searchUser(String userId){
        Response response=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("Admin_Token"))
                .when().get("/api/user/"+userId);
        return response;
    }
    public Response searchUserWithWrongId(String userId){
        Response response=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("Admin_Token"))
                .when().get("/api/user/"+userId);
        return response;
    }


    public Response editUserInfo(String userId,UserModel userModel){
        Response response=given().contentType("application/json")
                .body(userModel)
                .header("Authorization","Bearer "+prop.getProperty("Admin_Token"))
                .when().put("/api/user/"+userId);
        return response;
    }

    public Response userLogin(UserModel userModel){
        Response response=given().contentType("application/json")
                .body(userModel).when().post("/api/auth/login");
        return response;
    }

    public Response userLoginWithWrongCreds(UserModel userModel){
        Response response=given().contentType("application/json")
                .body(userModel).when().post("/api/auth/login");
        return response;
    }

    public Response itemList(){
        Response response=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("User_Token"))
                .when().get("/api/costs");
        return response;
    }

    public Response addItem(ItemModel itemModel){
        Response response=given().contentType("application/json")
                .body(itemModel)
                .header("Authorization","Bearer "+prop.getProperty("User_Token"))
                .when().post("/api/costs");
        return response;
    }

    //extra
    public Response searchItem(String itemId){
        Response response=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("User_Token"))
                .when().get("/api/costs/"+itemId);
        return response;
    }
    public Response editItem(String itemId,ItemModel itemModel){
        Response response=given().contentType("application/json")
                .body(itemModel)
                .header("Authorization","Bearer "+prop.getProperty("User_Token"))
                .when().put("/api/costs/"+itemId);
        return response;
    }

    public Response deleteItem(String itemId){
        Response response=given().contentType("application/json")
                .header("Authorization","Bearer "+prop.getProperty("User_Token"))
                .when().delete("/api/costs/"+itemId);
        return response;
    }
}