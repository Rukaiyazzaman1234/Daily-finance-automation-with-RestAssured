public class UserModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String gender;
    private boolean termsAccepted;

//encapsulation

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean getTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public UserModel(String email, String password){
        this.email=email;
        this.password=password;
    }
    public UserModel(String firstName, String lastName,String email,
                     String password,String phoneNumber,String address,
                     String gender,boolean termsAccepted){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.gender=gender;
        this.termsAccepted=termsAccepted;
    }

    public UserModel(){

    }


}
