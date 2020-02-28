package jingubang.json;


import java.io.StringReader;

//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
//import javax.json.JsonValue;

public class JSONDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String jsonStr = "{"access_token":"30_vxpDfBdeK3rc5pfAFYbVlti8Gl6wVkt8qKcmnbtixAXNEhRnofik6F8yQAIrpwwcVKdwwV1U_iLsabGniATuIAxTkyha6c9mNJCouvHUaLE5JmtAiMNMAK2eZnupt4iXES554ueB5ukB8MKaVOZfABAYRC","expires_in":7200}";
	
        String personJSONData = 
                "  {" +
                "   \"name\": \"Jack\", " +
                "   \"age\" : 13, " +
                "   \"isMarried\" : false, " +
                "   \"address\": { " +
                "     \"street\": \"#1234, Main Street\", " +
                "     \"zipCode\": \"123456\" " +
                "   }, " +
                "   \"phoneNumbers\": [\"011-111-1111\", \"11-111-1111\"] " +
                " }";
             
//            JsonReader reader = Json.createReader(new StringReader(personJSONData));
//             
//            JsonObject personObject = reader.readObject();
//             
//            reader.close();
//             
//            System.out.println("Name   : " + personObject.getString("name"));
//            System.out.println("Age    : " + personObject.getInt("age"));
//            System.out.println("Married: " + personObject.getBoolean("isMarried"));
//
//            JsonObject addressObject = personObject.getJsonObject("address");
//            System.out.println("Address: ");
//            System.out.println(addressObject.getString("street"));
//            System.out.println(addressObject.getString("zipCode"));
//             
//            System.out.println("Phone  : ");
//             JsonArray phoneNumbersArray = personObject.getJsonArray("phoneNumbers");
//            for (JsonValue jsonValue : phoneNumbersArray) {
//                System.out.println(jsonValue.toString());
//            }
	}

}
