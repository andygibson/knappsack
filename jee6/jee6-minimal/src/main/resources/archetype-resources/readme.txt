#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
                                 ${artifactId}


 Created using Knappsack Maven Archetypes. For more information on the 
 archetypes go to :
  
 http://www.andygibson.net/tag/knappsack/

 To find out more details of server compatabilities goto : 

 http://www.andygibson.net/blog/projects/knappsack/deploying-knappsack-projects/

 What is it?
 ===========

 This is a starter project for Java EE 6 using CDI, JSF 2.0, JPA and can be 
 deployed using Maven 2.0.10 or greater with Java 5.0 or greater. This 
 application can be deployed on any Java EE 6 container, but currently 
 targets JBoss 6 with the datasource naming. There are instructions below  
 describing how to alter the project to target Glassfish. The currently 
 supported version of JBoss is JBoss 6 M3.

 Using it for your own projects
 ============================== 
  
 This project comes with some basic functionality that can easily be cleaned up 
 and used as the basis for your own project. Every package can be removed, 
 although you may want to keep the following files for your own project : 
 
    bean\DataRepositoryProducer.java
    bean\DataRepositoryProducerLocal.java
    qualifier\DataRepository.java
   
 These files provide you with an injectable EntityManager instance.
    
 The only files you will need to consider changing is the home.xhtml file,
 the template file in 
 
 WEB-INF${symbol_escape}templates${symbol_escape}template.xhtml
 
 and the stylesheet in :
 
 webapp${symbol_escape}resources${symbol_escape}css${symbol_escape}screen.css
 
 Alternatively, to start with a blank project, simply create a new project from 
 the jee6-basic-archetype. This project will have JSF, JPA, and CDI configured 
 and will only have the DataRepository qualifier and producer code. 
  
 How To Deploy
 =============
 
 Once the application is deployed, it can be viewed using the following URL : 
 
 http://localhost:8080/${artifactId}/home.jsf
 
 This application can be deployed in any Java EE 6 container through the 
 following methods
 
 1) IDE Integration - Any Maven aware IDE (such as Netbeans or Eclipse) 
    should be able to deploy this application using methods provided by 
    the IDE
    
 2) Manually - If you run the Maven command :
 
    mvn clean package
    
    Maven will clean, build and package the application and you can 
    manually deploy the resulting target/${artifactId}.war file to your server
    
 3) You can deploy to JBoss is you have the JBOSS_HOME environment variable 
    set up using :
    
    mvn clean package jboss:hard-deploy
    
    This will clean, build and package the application, and then copy the 
    application to the jboss deploy directory for the JBoss installation 
    defined by JBOSS_HOME.     
 
 Changes for Glassfish
 =====================

 A full list of changes to deploy under Glassfish can be found here :

 http://www.andygibson.net/blog/projects/knappsack/deploying-knappsack-projects/

 Note that you may need to update Glassfish  with the latest version of Weld
 
 In order to run the application under Glassfish, you will need change 
 the default datasource to use the default Derby database in Glassfish.
 
 In the file : 
 
 src/main/resources/META-INF/persistence.xml
 
 Change the JTA datasource from 

 <jta-data-source>java:/DefaultDS</jta-data-source>
 
to :

 <jta-data-source>jdbc/__default</jta-data-source>
 
 Also note that the default JPA provider in Glassfish doesn't execute the 
 import.sql script unlike the Hibernate provider in JBoss. You could 
 install Hibernate as the JPA Provider and uncomment the line :
 
 <provider>org.hibernate.ejb.HibernatePersistence</provider>
 
 in the file : 
 
 src/main/resources/META-INF/persistence.xml  
   
   
  Importing the project into an IDE
 =================================

 If you created the project using the Maven 2 archetype wizard in your IDE
 (Eclipse, NetBeans or IntelliJ IDEA), then there is nothing to do. You should
 already have an IDE project.

 If you created the project from the commandline using archetype:generate, then
 you need to bring the project into your IDE. If you are using NetBeans 6.8 or
 IntelliJ IDEA 9, then all you have to do is open the project as an existing
 project. Both of these IDEs recognize Maven 2 projects natively.

 To import into Eclipse, you first need to install the m2eclipse plugin. To get
 started, add the m2eclipse update site (http://m2eclipse.sonatype.org/update/)
 to Eclipse and install the m2eclipse plugin and required dependencies. Once
 that is installed, you'll be ready to import the project into Eclipse.

 Select File > Import... and select "Import... > Maven Projects" and select
 your project directory. m2eclipse should take it from there.

 Once in the IDE, you can execute the Maven commands through the IDE controls.

 
 
