# MyPetStoreTesting
This repository contains Selenium TestNG tests for the Pet Store website using Page Object Model. These tests are designed to validate various functionalities of the website, such as registration, search, sorting, adding products to the cart as well as negative tests.

## Test Cases
### Basic Page Load Test (testBasic)
Validates the basic page load functionality by checking if the URL matches the expected URL of the Pet Store homepage.

### Registration and Redirect Test (testRegisteringAndRedirectToHome)
Tests the registration process by registering a new user with a unique email address and verifies if the user is redirected to the homepage after registration.

### User Information Verification Test (testCorrectInfoAfterRegister)
Verifies that the user information displayed after registration matches the expected information provided during registration.

### Sorting Test (testSort)
Tests the sorting functionality by sorting products by price in ascending and descending order and verifies if the sorting is done correctly.

### Search Test (testSearch)
Tests the search functionality by entering a search query, navigating to the search results page, and verifying if the expected product is displayed in the search results.

### Cart Functionality Test (testCart)
Tests the cart functionality by adding two products to the cart and verifies if both products are added successfully and the cart count is correct.

### Not enough information Negative Test (testNotAllFieldsFilled)
Tests the error handling by attempting to register without filling in all required fields and verifies if the expected error message is displayed.

### Not unique email Negative Test (testNotUniqueEmail)
Tests the error handling when attempting to register with an email that has already been registered.

## File Structure
All the tests are in the src/test/java/MyTests directory. The main tests are in PSTestNGlog.java file in Mytests package. The LogUtility class is created for logging test procedure in Logs directory.


![image](https://github.com/user-attachments/assets/7a3f56e4-e6ef-4162-93d1-e8584aecd7da)

## License
This project is licensed under the MIT License.


