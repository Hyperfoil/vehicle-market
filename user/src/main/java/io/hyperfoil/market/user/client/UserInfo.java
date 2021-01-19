package io.hyperfoil.market.user.client;

public class UserInfo {
   public String username;
   public String firstName;
   public String lastName;
   public String email;
   public String phone;

   public UserInfo(String username, String firstName, String lastName, String email, String phone) {
      this.username = username;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.phone = phone;
   }
}
