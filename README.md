# IBAN VALIDATION REST API

This REST API is made for International Bank Account Number (IBAN) validation. Validation process is made 
according to instruction from [Wikipedia](https://en.wikipedia.org/wiki/International_Bank_Account_Number#Algorithms).
This project consists only of backend part. Fronted written for this API locates at ...

### Used technologies for development, building and deployment
* Java 11
* Maven 3.8.4
* Git 2.32.0
* Node JS 17.5.0 (UI part)

### Backend installation and running guide
1. Clone repository using Git (here will be repository address)
   `git clone ...`
2. go to clone repository
3. run `mvn clean package`
4. from target folder copy IBAN-validation jar file to the server 
destination folder (remote or local);
5. execute the command `java -jar IBAN-validation-<file-version>.jar com.luminor.task.ibanvalidation.IbanValidationApplication`

### Project components description
This is backend part of IBAN VALIDATION REST API. UI part might be found at ...
These two parts are physically separated and are interacting through the server(?).

