
# Anypoint Template: SAP2SFDC-product-broadcast

+ [Use Case](#usecase)
+ [Run it!](#runit)  
	* [Running on premise](#runonopremise)
    * [Running on CloudHub](#runoncloudhub)
	* [Properties to be configured](#propertiestobeconfigured)
+ [Customize It!](#customizeit)
    * [config.xml](#configxml)
    * [endpoints.xml](#endpointsxml)
    * [businessLogic.xml](#businesslogicxml)
    * [errorHandling.xml](#errorhandlingxml)
+ [Testing the Anypoint Template](#testingtheanypointtemplate)

# Use Case <a name="usecase"/>


## Foreword <a name="foreword"/>
To make this Anypoint Template run, there are certain preconditions that must be considered. All of them deal with the preparations in both source (SAP) and destination (SFDC) systems, that must be made in order for all to run smoothly. 
**Failling to do so could lead to unexpected behavior of the template.**

### SAP Pre Conditions

### SFDC Pre Conditions

This template makes use of the `External ID` field offered by Salesforce. Here is a short description on how SFDC define external ID's 

+ [What is an external ID?](http://help.salesforce.com/apex/HTViewHelpDoc?id=faq_import_general_what_is_an_external.htm)

The templates uses the External ID in order to do xRef between the entities in both systems. The idea is, once an entity is created in SFDC it's decorated with an ID from the source system which will be used afteward for the template to reference it.

You will need to create a new custom field in your **Product** entity in SFDC with the following name: 

+ `sap_external_id`

For instructions on how to create a custom field in SFDC plase check this link:

+ [Create Custom Fields](http://www.salesforce.com/smallbusinesscenter/faq/customize.jsp#customfield)


# Run it! <a name="runit"/>

Simple steps to get SAP2SFDC-product-broadcast running


## Running on premise <a name="runonopremise"/>

In this section we detail the way you have to run you Anypoint Temple on you computer.

### Running on Studio <a name="runonstudio"/>
Once you have imported you Anypoint Template into Anypoint Studio you need to follow these steps to run it:

+ Locate the properties file `mule.dev.properties`, in src/main/resources
+ Complete all the properties required as per the examples in the section [Properties to be configured](#propertiestobeconfigured)
+ Once that is done, right click on you Anypoint Template project folder 
+ Hover you mouse over `"Run as"`
+ Click on  `"Mule Application"`


### Running on Mule ESB stand alone <a name="runonmuleesbstandalone"/> 
Complete all properties in one of the property files, for example in [mule.prod.properties] (../blob/master/src/main/resources/mule.prod.properties) and run your app with the corresponding environment variable to use it. To follow the example, this will be `mule.env=prod`.


## Running on CloudHub <a name="runoncloudhub"/>

While [creating your application on CloudHub](http://www.mulesoft.org/documentation/display/current/Hello+World+on+CloudHub) (Or you can do it later as a next step), you need to go to Deployment > Advanced to set all environment variables detailed in **Properties to be configured** as well as the **mule.env**. 

Once your app is all set and started, supposing you choose as domain name `SAP2SFDC-product-broadcast` to trigger the use case you just need to hit `http://SAP2SFDC-product-broadcast.cloudhub.io/synccontacts` and report will be sent to the emails configured.

### Deploying your Anypoint Template on CloudHub <a name="deployingyouranypointtemplateoncloudhub"/>
Mule Studio provides you with really easy way to deploy your Anypoint Template directly to CloudHub, for the specific steps to do so please check this [link](http://www.mulesoft.org/documentation/display/current/Deploying+Mule+Applications#DeployingMuleApplications-DeploytoCloudHub)

## Properties to be configured (With examples)<a name="propertiestobeconfigured"/>
In order to use this Anypoint Template you need to configure properties (Credentials, configurations, etc.) either in properties file or in CloudHub as Environment Variables. Detail list with examples:
...

# Customize It!<a name="customizeit"/>
This brief guide intends to give a high level idea of how this Anypoint Template is built and how you can change it according to your needs.
As mule applications are based on XML files, this page will be organised by describing all the XML that conform the Anypoint Template.
Of course more files will be found such as Test Classes and [Mule Application Files](http://www.mulesoft.org/documentation/display/current/Application+Format), but to keep it simple we will focus on the XMLs.

Here is a list of the main XML files you'll find in this application:

* [config.xml](#configxml)
* [inboundEndpoints.xml](#inboundendpointsxml)
* [businessLogic.xml](#businesslogicxml)
* [errorHandling.xml](#errorhandlingxml)


## config.xml<a name="configxml"/>
This file holds the configuration for Connectors and [Properties Place Holders](http://www.mulesoft.org/documentation/display/current/Configuring+Properties) are set in this file. 
**Even you can change the configuration here, all parameters that can be modified here are in properties file, and this is the recommended place to do it so.** 
Of course if you want to do core changes to the logic you will probably need to modify this file.

In the visual editor they can be found on the *Global Element* tab.


## indboundEndpoints.xml<a name="inbpundendpointsxml"/>
This is the file where you will found the inbound and outbound sides of your integration app.
It is intented to define the application API.
...

## businessLogic.xml<a name="businesslogicxml"/>
This file holds the functional aspect of the Anypoint Template, directed by one flow responsible of conducting the business logic.
...


## errorHandling.xml<a name="errorhandlingxml"/>
This is the right place to handle how your integration will react depending on the different exceptions. 
This file holds a [Choice Exception Strategy](http://www.mulesoft.org/documentation/display/current/Choice+Exception+Strategy) that is referenced by the main flow in the business logic.
...


## Testing the Anypoint Template <a name="testingtheanypointtemplate"/>

You will notice that the Template has been shipped with test.
These devidi them self into two categories:

+ Unit Tests
+ Integration Tests

You can run any of them by just doing right click on the class and clicking on run as Junit test.

Do bear in mind that you'll have to tell the test classes which property file to use.
For you convinience we have added a file mule.test.properties located in "src/test/resources".
In the run configurations of the test just make sure to add the following property:

+ -Dmule.env=test