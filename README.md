# IBAN VALIDATION REST API

This REST API is made for International Bank Account Number (IBAN) validation. Validation process is made 
according to instruction from [Wikipedia](https://en.wikipedia.org/wiki/International_Bank_Account_Number#Algorithms).
This project consists only of backend part which implements IBAN VALIDATION REST API enpoint. Fronted written for this API locates at `https://github.com/jelikaer/IBAN-validation-ui.git`. These two parts are physically separated and UI part interacts with backend part through the REST API endpoint.

### Used technologies for development, building and deployment
* Java 11
* Maven 3.8.4
* Git 2.32.0

### Backend installation and running guide
1. Clone repository using Git (here will be repository address)
   `git clone https://github.com/jelikaer/IBAN-validation.git`
2. go to the cloned repository local folder with name "IBAN-validation"
3. run `mvn clean package`
4. from target folder copy IBAN-validation jar file to the server 
destination folder (remote or local);
5. execute the command 
`java -jar IBAN-validation-<file-version>.jar com.luminor.task.ibanvalidation.IbanValidationApplication`
