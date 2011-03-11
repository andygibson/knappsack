#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
                                 ${project.artifactId}


 Created using Knappsack Maven Archetypes. For more information on the 
 archetypes go to :
  
  http://www.andygibson.net/blog/tag/knappsack/

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
 
 This project is meant to be used as a sandbox application for learning 
 Java EE, or trying out ideas or libraries. It provides a good code base for
 building tutorials on without having to start a project from scratch. The 
 section "what's included" below describes in more detail what is in the 
 application.
    
 To get more of a simple demo so you can see if your environment is set up 
 correctly, create a project from the jee6-minimal-archetype instead which 
 provides a simple smoke test for JSF, CDI and JPA. Alternatively, you can use 
 the jee6-basic-archetype that creates a project with just configuration for 
 JSF, JPA and CDI and a single template based page. 
 
 How To Deploy
 =============
 
 Once the application is deployed, it can be viewed using the following URL : 
 
 http://localhost:8080/{project.artifactId}/home.jsf
 
 This application can be deployed in any Java EE 6 container through the 
 following methods
 
 1) IDE Integration - Any Maven aware IDE (such as Netbeans or Eclipse) 
    should be able to deploy this application using methods provided by 
    the IDE
    
 2) Manually - If you run the Maven command :
 
    mvn clean package
    
    Maven will clean, build and package the application and you can 
    manually deploy the re{project.artifactId}et/${project.artifactId}.war file to your server
    
 3) You can deploy to JBoss is you have the JBOSS_HOME environment variable 
    set up using :
    
    mvn clean package jboss:hard-deploy
    
    This will clean, build and package the application, and then copy the 
    application to the jboss deploy directory for the JBoss installation 
    defined by JBOSS_HOME.
    
 What's Included?
 ================
 
 The application contains a simple book model (Author,Book, Catalog) that 
 involves one to many, many to one and many to many relationships. The 
 DataFactory class is a CDI bean that creates the default data for the 
 application in the createData method. Each time the data should be created the 
 same as it is based on a seeded random number generator. Note that we use
 the UserTransaction injected into our bean to manually handle transactions. 
 
 You don't have to worry about calling the create data method, it is done 
 automatically using the ${symbol_pound}{dataStatus} attribute which appears in the template 
 header. When JSF requests that value, the producer method will trigger the 
 creation of the data, just don't take it out of the page. Note that if you try 
 and put data aware expressions ahead of the ${symbol_pound}{dataStatus} expression, then the
 data may not be created on the first call to the page. 
 
 The DataRepository annotation class is a CDI qualifier for the default entity 
 manager in the application. The DataRepositoryProducer is a stateless EJB that 
 produces the entity manager. The DataRepositoryProducerLocal is the local EJB 
 interface.
 
 Technically, with EJB 3.1 we don't need the Local interface but CDI has a 
 problem locating EJBs without it.
   
 The application has a single page that lists the set of books in the dataset
 and can be seen by going to :
 
{project.artifactId}lhost:8080/${project.artifactId}/home.xhtml
 
 The expression ${symbol_pound}{books} is used to refer to the list of books and the producer
 for that expression is in the datafactory. 
 
 We chose not to use the import.sql method of adding data because it only works
 with Hibernate JPA providers.
          
 
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

 
 
