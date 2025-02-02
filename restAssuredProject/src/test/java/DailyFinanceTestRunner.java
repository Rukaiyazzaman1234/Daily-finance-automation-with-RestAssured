import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DailyFinanceTestRunner extends Setup {

    @Test(priority = 1,description = "Check if new user can register successfully")
    public void register() throws ConfigurationException {
        DailyFinanceController financeController = new DailyFinanceController(prop);
        UserModel userModel = new UserModel();
        Faker faker=new Faker();
        String firstName=faker.name().firstName();
        String lastName=faker.name().lastName();
        String email=firstName+"1@gmail.com";
        String phoneNumber="0160"+Utils.generateRandomId(1000000,9999999);

        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword("1234");
        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress("Mirpur");
        userModel.setGender("Female");
        userModel.setTermsAccepted(true);

        Response response =financeController.userRegistration(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String userId=jsonPath.get("_id");
        System.out.println(userId);
        Utils.setEnvVar("userId",userId);

        String userFirstName=jsonPath.get("firstName");
        Utils.setEnvVar("userFirstName",userFirstName);

        String userLastName=jsonPath.get("lastName");
        Utils.setEnvVar("userLastName",userLastName);

        String userEmail=jsonPath.get("email");
        Utils.setEnvVar("userEmail",userEmail);

    }
    @Test(priority = 2, description = "Check if user can not register with duplicate creds")
    public void userRegDuplicate(){
        DailyFinanceController financeController = new DailyFinanceController(prop);
        UserModel userModel = new UserModel();
        Faker faker=new Faker();
        String firstName=faker.name().firstName();
        String phoneNumber="0130"+Utils.generateRandomId(1000000,9999999);

        userModel.setFirstName(firstName);
        userModel.setLastName(prop.getProperty("userLastName"));
        userModel.setEmail(prop.getProperty("userEmail"));
        userModel.setPassword("1234");
        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress("dhaka");
        userModel.setGender("Female");
        userModel.setTermsAccepted(true);

        Response response =financeController.userRegistration(userModel);
        System.out.println(response.asString());
        JsonPath jsonPath=response.jsonPath();
        String msgActual=jsonPath.get("message");
        Assert.assertTrue(msgActual.contains("User already exists"));
    }
    @Test(priority = 3, description = "Check if admin can not login with wrong creds")
    public void adminLoginWrongCreds(){
        DailyFinanceController financeController=new DailyFinanceController(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("4321");
        Response response=financeController.adminLogin(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String msgActual=jsonPath.get("message");
        Assert.assertTrue(msgActual.contains("Invalid"));
    }
    @Test(priority = 4, description = "Check if admin can login successfuully")
    public void adminLogin() throws ConfigurationException {
        DailyFinanceController financeController=new DailyFinanceController(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin123");
        Response response=financeController.adminLogin(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String adminToken=jsonPath.get("token");
        System.out.println(adminToken);

        Utils.setEnvVar("admin_Token",adminToken);
    }

    @Test(priority = 5, description = "Login as a admin and check user list")
    public void userList(){
        DailyFinanceController financeController=new DailyFinanceController(prop);
        Response response=financeController.userList();
        System.out.println(response.asString());
    }

    @Test(priority = 6, description = "Search user with wrong id")
    public void searchUserWrongId(){
        DailyFinanceController financeController=new DailyFinanceController(prop);
        Response response=financeController.searchUser("123");
        System.out.println(response.asString());
        JsonPath jsonPath=response.jsonPath();
        String msgActual=jsonPath.get("message");
        Assert.assertTrue(msgActual.contains("User not found"));
    }
    @Test (priority = 7, description = "Search user by id")
    public void searchUser(){
        DailyFinanceController financeController=new DailyFinanceController(prop);
        Response response=financeController.searchUser(prop.getProperty("userId"));
        System.out.println(response.asString());
    }

    @Test (priority = 8, description = "Search user by id")
    public void searchUserWithWrongId(){
        DailyFinanceController financeController=new DailyFinanceController(prop);
        Response response=financeController.searchUser(prop.getProperty("WronguserId"));
        System.out.println(response.asString());
    }


    @Test (priority = 9, description = "Check if admin can edit user info")
    public void editUserInfo(){
        DailyFinanceController financeController = new DailyFinanceController(prop);
        Response response = financeController.searchUser(prop.getProperty("userId"));
        UserModel userModel=new UserModel();
        userModel.setFirstName("Rainbow");
        userModel.setLastName(prop.getProperty("userLastName"));
        userModel.setEmail(prop.getProperty("userEmail"));
        userModel.setPassword("1234");
        userModel.setPhoneNumber("0160"+Utils.generateRandomId(1000000,9999999));
        userModel.setAddress("dhaka");
        userModel.setGender("Female");
        userModel.setTermsAccepted(true);

        Response responseNew= financeController.editUserInfo(prop.getProperty("userId"), userModel);
        System.out.println(responseNew.asString());
    }


    @Test(priority = 10,description = "Check if user can login")
    public void userLogin() throws ConfigurationException {
        DailyFinanceController financeController=new DailyFinanceController(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail("rupatest@gmail.com");
        userModel.setPassword("1234");
        Response response= financeController.userLogin(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String userToken=jsonPath.get("token");
        System.out.println(userToken);

        Utils.setEnvVar("user_Token",userToken);
    }

    @Test(priority = 11,description = "Check if user can not login with wrong creds")
    public void userLoginWithWrongCreds() throws ConfigurationException {
        DailyFinanceController financeController=new DailyFinanceController(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail("rupatest@gmail.com");
        userModel.setPassword("1111");
        Response response= financeController.userLogin(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String userToken=jsonPath.get("token");
        System.out.println(userToken);

        Utils.setEnvVar("userToken",userToken);
    }

    @Test(priority = 12, description = "Login as a user and check item list")
    public void itemList(){
        DailyFinanceController financeController=new DailyFinanceController(prop);
        Response response= financeController.itemList();
        System.out.println(response.asString());
    }

    @Test(priority = 13, description = "check if user can add new a item to the list")
    public void addItem() throws ConfigurationException {
        DailyFinanceController financeController=new DailyFinanceController(prop);
        ItemModel itemModel=new ItemModel();
        itemModel.setItemName("Monitor");
        itemModel.setQuantity("1");
        itemModel.setAmount("40000");
        itemModel.setPurchaseDate("2025-01-19");
        itemModel.setMonth("January");
        itemModel.setRemarks("purchased");

        Response response=financeController.addItem(itemModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String itemId=jsonPath.get("_id");
        System.out.println(itemId);
        Utils.setEnvVar("itemId",itemId);
    }

    @Test(priority = 14, description = "Check if item with no value can not be added")
    public void addItemNoValue(){
        DailyFinanceController financeController=new DailyFinanceController(prop);
        ItemModel itemModel=new ItemModel();
        itemModel.setItemName("");
        itemModel.setQuantity("");
        itemModel.setAmount("");
        itemModel.setPurchaseDate("");
        itemModel.setMonth("");
        itemModel.setRemarks("");

        Response response=financeController.addItem(itemModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String msgActual=jsonPath.get("message");
        Assert.assertTrue(msgActual.contains("error"));

    }

    @Test(priority = 15, description = "Check if user can edit item")
    public void editItem(){
        DailyFinanceController financeController=new DailyFinanceController(prop);
        Response response = financeController.searchItem(prop.getProperty("itemId"));
        ItemModel itemModel=new ItemModel();
        itemModel.setItemName("Laptop");
        itemModel.setQuantity("1");
        itemModel.setAmount("50000");
        itemModel.setPurchaseDate("2025-01-25");
        itemModel.setMonth("January");
        itemModel.setRemarks("done");

        Response responseNew=financeController.editItem(prop.getProperty("itemId"),itemModel);
        System.out.println(responseNew.asString());
    }

    @Test(priority = 16, description = "Check if item can be deleted by user")
    public void deleteItem(){
        DailyFinanceController financeController=new DailyFinanceController(prop);
        Response response=financeController.deleteItem(prop.getProperty("itemId"));
        System.out.println(response.asString());
    }
}