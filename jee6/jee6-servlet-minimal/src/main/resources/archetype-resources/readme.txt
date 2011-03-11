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

 This is a starter project for Java EE 6 using CDI, JSF 2.0, JPA in a servlet 
 container that can be deployed using Maven 2.0.10 or greater with Java 5.0 or 
 greater. This application is meant to be deployed on a simple servlet container 
 or a as opposed to a full Java EE 6 Application Server. 
 
 This project is meant to be a starter application for using Java EE 6, 
 specifically JSF,CDI,JPA and the Validations library. To get a demo that can
 be used with Java EE 6 application servers, try one of the other archetypes. 
     
 How To Deploy
 =============

  The application can be deployed from the command line using the Jetty servlet
  container by entering : 
 
  mvn jetty:run
  
  This will download any necessary dependencies or plugins, and start the jetty 
  server  with the application deployed. You can see the application by browsing 
  to :
  
  http://localhost:8080/{project.artifactId}/home.jsf
    
 What's Included?
 ================
 
 A JPA model (Person) that includes validation annotations and can be entered on 
 the sample page. When the page is posted back, the person is added to the HSQL
 database and displayed on a list in the page.
    
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

 
 
