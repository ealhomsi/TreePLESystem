# Project-13 (ECSE 321 McGill University Fall 2018)
This project was done on 6 different deliverables for the course of ECSE 321 on McGill Github.

![Image of Branches](https://image.ibb.co/dvMt1c/Introductory_Slide.png)

## Introduction
The system is a Tree management system that displays trees on a map, which allows to modify/update trees. The purpose of this app is to display statistical data useful to the users who are considering a what-if senario in case of building a new construction over some specific area. It provides bio-diversity index according to a search criterea for example: Municipaltiy, Species, and Status. We have also decided to release the app to the public domain. It consists of:

1. A Java spring Application (BackEnd)

2. An Android Application (FrontEnd)

3. A front-end running Vue.js (FrontEnd)

![Image of Branches](https://image.ibb.co/gHprnH/trees.png)

## The Team
1. Al Homsi, Elias
https://www.linkedin.com/in/ealhomsi/

2. Chuong, Kevin
3. Davis, Jeremy
4. Décéus, Oscar

## Challenges Faced
* Q: Which architectural styles apply best?
* Q: How to test effectively?
* Q: How to keep user authentication secure?
* Q: How to define areas for biodiversity index?

## Project Iterations:
### Iteration 1 (Desgin)
Iteration one was the desinging phase which included things like
#### choice of architecture
For this project three main architectures were used:

1. The MVC: 
global structure which specifies the backend part and the two different front-ends. This choice was given because the two front-ends share the same controller. Also, It helps seperating the concerns into three different parts.

![Image of Branches](https://preview.ibb.co/dhErnH/mvc.png)

2. The MMVM:
This structure was used on the website front-end in order to grab the contents from the backend while keeping them in javascript temporarely for display. The model-view objects are DTO (Data transfer objects) that were used to update the view.
![Image of Branches](https://preview.ibb.co/cgGRMc/mvmm.png)

3. The Layered Architecture:
The backend was further sepearated into two different parts: 

The first being the REST-Controller which contain the API calls and handeled user requests to the backend.
,The second being the service layer which contains the business logic and operations.

![Image of Branches](https://preview.ibb.co/jsqmMc/layeredarch.png)


#### domain model
The domain model is implemented using umple designing language. The model consists of the following classes: Tree, Resident, Location, Municipaltiy, TreePLESystem, Transaction.

1. Tree: 
Is the Tree class which contains parameters for the tree such as the diameter, status, and species.

2. Municipality:
This class contained the names of the municipality in the system.

3. Resident:
This class contained the name, email and the securely stored hashed password of the user.

4. Location:
This is a helper class that containted the LatLng (Latitude and Longitutde) of a point on a map.

#### usecases
The uses cases were generated to match the requirements:

1. A resident login and adding a tree

2. A resident Register and login and modifying a tree

3. A resident downloading the apk to his/her phone from the website and then running the app

4. A resident asking for bio-index information through the specification of a polygon or a search Criteriea

5. Clicking on a treeid would show you directly the tree place.

Logic rules are also implemented to make sense for modifying a tree. For example, a cutdown tree cannot be updated to healthy. Another example, A resident cannot set a tree to diseased unless he/she introduced that tree into the system. Therefore, Login is required when modifying trees.

### Iteration 2 (Prototype)
In iteration 2 an initial prototype of the web front-end and the backend was implemented. Challenges in applying the google-maps api and integrating it into the vuejs system. google-maps-vue2 library was used to solve this issue. Also, trees were retrieved from the backend to display on the front-end. The purpose of this iteration is testing the feasiblity of the system while getting some information in depth about the technical requirements of the project.

### Iteration 3 (Testing)
Extensive ammount of tests were written for the backend using JUnit Testing in Java. Tests are conducted before pushing to the release branch on github. The testing suits were written for both the service part of the backend layer and the Controller part. A stub was created for the service part to test the Controller upon.

![Image of Branches](https://preview.ibb.co/n2LLgc/integration.png)
Integration Testing

### Iteration 4 (Release PipeLine)
The automated build plan was implemented using github and jenkins. Jenkins was setup on the server of http://ecse321-12.ece.mcgill.ca:8081/. Jenkins would automatically check the release branch of the project for new pushes. In this case jenkins would clone the changes and build the backend using the gradle script. The bash script would also execute to run both the gradle for android to build the apk and make it available on the website. The website would be built using npm run build and deployed using apache httpd server. The backend is implemented using the Tomcat server. Follow the notes on the slides for more details on the server configuration also, find attache the build scripts. Jenkins is deployed on 8081, Tomcat is deployed on 8080, and the apache is deployed on 8087.



![Image of Branches](https://preview.ibb.co/h4VxSH/branches.png)
Development Branches


![Image of Branches](https://image.ibb.co/gLpP7H/release_Pipeline.png)
Build

![Image of Branches](https://preview.ibb.co/hMTTZx/deploy.png)
Deploy

### ** Iteration 5 and 6 are the presentation and the documentation phases **


## List of Tools Used:

* Eclipse
Eclipse was used to code the Java Spring backend

* Sublime
Sublime was used as the main text editor for the web development

* Gradle
Gradle was used to build and main the releases

* Vue.js
The website front-end was coded using Vue.js

* Spring Java
J2EE java to help building the backend

* Jenkins
An automation build and deployment tool used to deploy releases

* LucidChart
Used to generate diagrams and data-models

* Ngrok
Ngrok is a port exposer that allows exposing a local port for the public internet. This allowed us to make the android app work without the restriction of using VPN to connect to McGill's network. Alternatives are localtunnel.

* AndroidStudio
Android studio was used to develop the android application.

![Image of Branches](https://preview.ibb.co/j4AWnH/tools.png)

## Unique Features

### google-maps-vue2 
source: https://www.npmjs.com/package/vue2-google-maps
This is the native library of google maps which allowed easy interation in Vue.js, The library was used heavyly in developing markers and polygon feature for tree selection. Work includes: Customized icons, customized notification windows, information bubbles and custom polygons.
To Add a tree right click on the map.

![Image of Branches](https://image.ibb.co/j46t1c/gmapspolygon.png)

### Displaying statistical data
Many different search critera could be used to filter out a group of trees and display information about most frequent trees in that group. For example, trees could be filtered based on speices, diamter, municipality and using a polygon. The data outputed in the format of percentages of the most frequent trees in that group.
![Image of Branches](https://image.ibb.co/fmOvEx/data.png)

### Secure Login
Secure standard practices in making and saving the password. Instead of sending the password in clear text, the website hash and salts the password. Only this version is checked against the Database. This ensures that the backend does not store users password.

![Image of Branches](https://image.ibb.co/iXm47H/secure_Login.png)
Secure Login

## To DO (in future iterations)
* Color trees based on species

* Deploying on a secure SSL tunnel website (https)

* Adding redundency to the system.

* Clustering Trees based on type and figuring out the distances between those clusters

* Providing more biodiversity indexes

* Testing the frontend using Mocha js
