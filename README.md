# DeveloperEvaluation

The goal of this application is, given xls files with a specific format, get the information of claims and providers and save them in the database.

It runs as a daemon checking if there are new files to process under a defined folder. After processing the files, it moves them to another folder called "processed".

## Prerequisites:

* JDK 8
* maven >= 3.0.5
* MySQL >= 5.7.22

## Installation:

* Download the project

    ```bash
    cd ~/code
    git clone  https://github.com/rustumza/DeveloperEvaluation.git
    cd DeveloperEvaluation
    git checkout master
    ```     
    
* Create a database called "developerEvaluation1" 
NOTE: you do not need to execute any database script. All the tables are created when the application is run for the first time.

* Set the following properties in the file application.properties that is under src/main/resources:
    - database parameters (url, user and password)
    - foder.path: path where the files are saved.

* Under the folder where the files are saved, create a new folder called "processed"

* compile the project with maven
    
    ```bash
    mvn clean compile
    ```    

## Open in an IDE

Import the code to your IDE as a Maven project.
It is necessary to install the Lombok plugin to avoid errors with the real-time compiler that the IDEs have.
Depends on your IDE you need to follow the steps in the Lombok [webpage](https://projectlombok.org/).

## Run the application

It has to be run as a normal java application. The main class is com.vestacare.developerevaluation1.Main

## Libraries, frameworks and tools used

* HIbernate 5.3.7: Persistence framework.
* Apache POI 4.0.0: Used to read information from xls files.
* Lombok 1.18.4: Allows to reduce the written code in the entities (avoid to developers to write getters, setters and constructors) making the code clearer and faster to write.
* Flyway 3.2.1: Is a database migration tool. I used it to avoid to use Hibernate tables creation and update because is not trustuble when the tables have information. Using this tool, all the DDL operations and database inserts to initialize values are under the developer control.
* Commons-io 2.6: It has a library (FilenameUtils) to work with files names. In this case, it was used to get the files' extension.
* Logs Library: Due to time reasons, I could not implement a Logs library.

## Database structure

```bash
CREATE TABLE `claim` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `claim_number` varchar(255) DEFAULT NULL,
  `cliente_claim` varchar(255) DEFAULT NULL,
  `patient` varchar(255) DEFAULT NULL,
  `provider_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ar15wy56lqayuqy27vbdmdc9v` (`claim_number`),
  KEY `FKj34fq3yhw0mrauw0xj8rvf14` (`provider_id`),
  CONSTRAINT `FKj34fq3yhw0mrauw0xj8rvf14` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `provider` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address1` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `facility` varchar(255) DEFAULT NULL,
  `provider_name` varchar(255) DEFAULT NULL,
  `provider_ref_num` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `tax_id` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hspqs6liacennbdydegdt5970` (`tax_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```
There are 2 tables: Provider and Claim with the required attributes (described in the exercise).
Claim table has a foreign key to Provider so the relation is Claim * - 1 Provider.
The column client_claim in Claim has a constraint to be unique. It is used to find a Claim using the business id. The same happens with tax_id in Provider table.

The table creation script is in the project under src/main/resources/db/migration. This script is used by Flayway to initialize the database.
