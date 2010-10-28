#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
                                 ${project.artifactId}ifactId}


 Created using servlet Knappsack Maven Archetypes. For more information on the 
 archetypes go to :
  
  http://www.andygibson.net/blog/tag/knappsack/

 To find out more details of server compatabilities goto : 

 http://www.andygibson.net/blog/projects/knappsack/deploying-knappsack-projects/


 What is it?
 ===========

 This is a demo project created from the Knappsack servlet archetype that can be 
 run from the command line in a Tomcat or Jetty container. It uses an embedded HQL 
 database  so you 
     
 How To Deploy
 =============

  The application can be deployed from the command line using the Jetty servlet
  container by entering : 
 
  mvn jetty:run
  
  Alternatively, you can use the embedded Tomcat container using the following : 
  
  mvn tomcat:run
  
  In either case, Maven will download any necessary dependencies or plugins, and 
  start the server with the application deployed. You can then see the application 
  by browsing to :
  
  http://localhos{project.artifactId}ject.artifactId}/
  
      
 What's Included?
 ================
 
 A demo bookmarking application that uses JSF, JPA, CDI and Bean validation that can 
 be run in a servlet container such as Tomcat or Jetty from the command line. The 
 demo implements user accounts with login requirements and a simple bookmarking site
 where users can add, title and tag bookmarks to share with others. 
    
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

 
 
